package net.abraxator.moresnifferflowers.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.client.gui.slot.HardenedMouthSlot;
import net.abraxator.moresnifferflowers.init.MSFEffects;
import net.abraxator.moresnifferflowers.init.MSFItems;
import net.abraxator.moresnifferflowers.networking.toClient.SyncMouthSlotsPacket;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;
import java.util.function.Function;

public class HardenedMouthCapability {
    public static int SLOT_COUNT = 2;
    public NonNullList<ItemStack> mouthSlots;
    public int cooldown;

    public static final Codec<ItemStack> SAFE_ITEMSTACK_CODEC = ItemStack.OPTIONAL_CODEC.xmap(
            stack -> stack.is(MSFItems.PLACEHOLDER) ? ItemStack.EMPTY : stack,
            stack -> stack.isEmpty() ? MSFItems.PLACEHOLDER.toStack() : stack
    );

    public static final Codec<HardenedMouthCapability> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    NonNullList.codecOf(SAFE_ITEMSTACK_CODEC).fieldOf("slots").forGetter(cap -> cap.mouthSlots),
                    Codec.INT.fieldOf("cooldown").forGetter(cap -> cap.cooldown))
            .apply(instance, HardenedMouthCapability::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, HardenedMouthCapability> STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC.apply(ByteBufCodecs.list()).map(NonNullList::copyOf, Function.identity()), cap -> cap.mouthSlots,
            ByteBufCodecs.INT, cap -> cap.cooldown,
            HardenedMouthCapability::new
    );


    public HardenedMouthCapability(NonNullList<ItemStack> list, int cooldown) {
        this.cooldown = cooldown;

        this.mouthSlots = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
        for (int i = 0; i < SLOT_COUNT; ++i) {
            this.mouthSlots.set(i, list.get(i));
        }
    }
    
    public NonNullList<ItemStack> getMouthSlotItems() {
        return mouthSlots;
    }

    
    public void setAllItems(NonNullList<ItemStack> itemStacks) {
        for (int i = 0; i < itemStacks.size(); i++) {
            setItem(i, itemStacks.get(i));
        }
    }

    
    public void setItem(int index, ItemStack stack) {
        mouthSlots.set(index, stack);
    }

    
    public ItemStack getItem(int index) {
        return mouthSlots.get(index);
    }

    
    public void clear() {
        mouthSlots.clear();
    }


    public void sync(Player player) {
        PacketDistributor.sendToPlayer((ServerPlayer) player,new SyncMouthSlotsPacket(this));
    }

    public void onEffectEnd(Player player) {
        getMouthSlotItems().forEach(itemStack -> {
            if (HardenedMouthSlot.moveToPlayerInventory(player.inventoryMenu, itemStack)) return;
            if (itemStack.isEmpty()) return;
            player.drop(itemStack, true);
        });
        clear();
        sync(player);
    }

    public void tick(Player player) {

        if (player.level().isClientSide || !player.hasEffect(MSFEffects.HARDENED_MOUTH)) return;

        ItemStack input = this.getItem(0);
        ItemStack output = this.getItem(1);


        if (getSmeltingResult(player.level(), input).isEmpty() || (!getSmeltingResult(player.level(), input).get().is(output.getItem()) && !output.isEmpty())) cooldown = getMaxCooldown(player);

        if (cooldown > 0) {
            cooldown--;
            sync(player);
            return;
        }

        getSmeltingResult(player.level(), input).ifPresentOrElse(result -> {

            if (output.isEmpty() || (ItemStack.isSameItemSameComponents(output, result) && output.getCount() < output.getMaxStackSize())) {
                input.shrink(1);

                if (output.isEmpty()) {
                    this.setItem(1, result.copy());
                } else {
                    output.grow(1);
                    this.setItem(1, output);
                }

                this.setItem(0, input);

                cooldown = getMaxCooldown(player);
            }
        }, () -> cooldown = getMaxCooldown(player));

        sync(player);
    }

    
    public int getCooldown() {
        return this.cooldown;
    }

    
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    
    public int getMaxCooldown(Player player) {
        int amplifier = 0;
        if (player.hasEffect(MSFEffects.HARDENED_MOUTH)) amplifier = player.getEffect(MSFEffects.HARDENED_MOUTH).getAmplifier();

        return Math.max(1, 80 - amplifier * 10);
    }

    public Optional<ItemStack> getSmeltingResult(Level level, ItemStack input) {
        return level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(input), level)
                .map(recipe -> recipe.value().getResultItem(level.registryAccess()));
    }

    public boolean isEmpty(){
        return getItem(0).isEmpty() && getItem(1).isEmpty();
    }

}

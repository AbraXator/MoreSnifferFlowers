package net.abraxator.moresnifferflowers.blocks.blockentities;

import com.google.common.collect.Lists;
import com.mojang.datafixers.types.templates.TaggedChoice;
import it.unimi.dsi.fastutil.ints.IntImmutableList;
import net.abraxator.moresnifferflowers.client.gui.menu.RebrewingStandMenu;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.http.conn.MultihomePlainSocketFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class RebrewingStandBlockEntity extends BaseContainerBlockEntity {
    public static final double MAX_FUEL = 16;
    public static final int DATA_PROGRESS = 0;
    public static final int DATA_FUEL = 1;
    public static final int MAX_PROGRESS = 100;
    private NonNullList<ItemStack> inv = NonNullList.withSize(6, ItemStack.EMPTY);
    int brewProgress;
    int fuel;
    public final ContainerData containerData = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> RebrewingStandBlockEntity.this.brewProgress;
                case 1 -> RebrewingStandBlockEntity.this.fuel;
                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0:
                    RebrewingStandBlockEntity.this.brewProgress = pValue;
                    break;
                case 1:
                    RebrewingStandBlockEntity.this.fuel = pValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
    
    public RebrewingStandBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REBREWING_STAND.get(), pPos, pBlockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatableWithFallback("container.more_sniffer_flowers.rebrewing", "Rebrewing Stand");
    }

    @Override
    public boolean isEmpty() {
        return inv.stream().allMatch(Predicate.not(ItemStack::isEmpty));
    }
    
    public void tick(Level level) {
        //FUEL 0; OG-POTION 1; INGREDIENT 2; POTION 3 - 5;
        var fuelStack = inv.get(0);
        var ogPotionStack = inv.get(1);
        var ingredientStack = inv.get(2);
        ItemStack[] potionStack = {inv.get(3), inv.get(4), inv.get(5)};
        if(fuel < MAX_FUEL && fuelStack.is(ModItems.CROPRESSED_NETHERWART.get())) {
            fuel++;
            fuelStack.shrink(1);
            setChanged();
        }
        
        if(canBrew()) {
            brewProgress++;
            if(brewProgress >= MAX_PROGRESS) {
                var effects = getEffect(ogPotionStack, ingredientStack);
                List<Integer> index = Util.make(Lists.newArrayList(), integers -> integers.addAll(Arrays.asList(3, 4, 5)));
                
                for(int i : index) {
                    ItemStack itemStack = inv.get(i);
                    if(itemStack.is(ItemStack.EMPTY.getItem())) {
                        
                    } else {
                        ItemStack itemStack1 = Items.POTION.getDefaultInstance();
                        PotionUtils.setCustomEffects(itemStack1, effects);
                        itemStack1.addTagElement("rebrewedPotion", EndTag.INSTANCE);
                        inv.set(i, itemStack1);
                    }
                }

                fuel -= 4;
                brewProgress = 0;
            }
        }
    }

    private List<MobEffectInstance> getEffect(ItemStack inputPotion, ItemStack ingredient) {
        List<MobEffectInstance> ret = new ArrayList<>();
        ListTag listTag = ((ListTag) inputPotion.getOrCreateTag().get("CustomPotionEffects"));
        
        for(int i = 0; i < listTag.size(); i++) {
            var potion = listTag.getCompound(i);
            var id = potion.getString("forge:id");
            var amp = potion.getByte("Amplifier") + (ingredient.is(Items.REDSTONE) ? 2 : 1);
            var dur = potion.getInt("Duration") + (ingredient.is(Items.GLOWSTONE_DUST) ? 12000 : 6000);
            var instance = new MobEffectInstance(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(id.split(":")[1])), dur, amp);
            
            ret.add(instance);
        }
        
        
        return ret;
    }
    
    private boolean canBrew() {
        boolean ret = false;
        
        for(int i = 3; i <= 5; i++) {
            if(inv.get(i).is(Items.GLASS_BOTTLE)) {
                ret = true;
            }
        }
        
        return ret && !inv.get(1).isEmpty() && fuel >= 1;
    }
    
    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new RebrewingStandMenu(pContainerId, pInventory, this, this.containerData);
    }

    @Override
    public int getContainerSize() {
        return 6;
    }

    @Override
    public ItemStack getItem(int pSlot) {
        return pSlot >= 0 && pSlot < this.inv.size() ? this.inv.get(pSlot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return ContainerHelper.removeItem(this.inv, pSlot, pAmount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return ContainerHelper.takeItem(inv, pSlot);
    }

    @Override
    public void setItem(int pSlot, ItemStack pStack) {
        if (pSlot >= 0 && pSlot < this.inv.size()) {
            this.inv.set(pSlot, pStack);
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    @Override
    public void clearContent() {
        this.inv.clear();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        ContainerHelper.saveAllItems(pTag, inv);
        pTag.putInt("progress", brewProgress);
        pTag.putInt("fuel", fuel);
        super.saveAdditional(pTag);
    }
    
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        inv = NonNullList.withSize(6, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(pTag, inv);
        fuel = pTag.getInt("fuel");
        brewProgress = pTag.getInt("progress");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("fuel", fuel);
        tag.putInt("progress", brewProgress);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
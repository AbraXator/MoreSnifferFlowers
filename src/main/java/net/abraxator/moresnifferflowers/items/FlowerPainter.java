package net.abraxator.moresnifferflowers.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FlowerPainter extends Item {
    public FlowerPainter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if(pOther.getItem() instanceof DyeItem && pAction == ClickAction.SECONDARY) {
            if(pOther.isEmpty()) {
                removeOne(pStack).ifPresent(itemStack -> {
                    playRemoveOneSound(pPlayer);
                    pAccess.set(itemStack);
                });
            } else {
                int i = add(pStack, pOther);
                if(i > 0) {
                    this.playInsertSound(pPlayer);
                    pOther.shrink(1);
                }
            }
        }
        return true;
    }

    private int add(ItemStack pStack, ItemStack pOther) {
        return 0;
    }

    private Optional<ItemStack> removeOne(ItemStack pStack) {
        var itemStack = getDye(pStack);
        if(itemStack.isPresent()) {
            ItemStack itemStack1 = itemStack.get();
            itemStack1.shrink(1);
            if(itemStack1.isEmpty()) {
                pStack.removeTagKey("dye");
            } else {
                pStack.setTag(itemStack1.serializeNBT());
            }
            return Optional.of(new ItemStack(itemStack1.getItem(), 1));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        getDye(pStack).ifPresentOrElse(itemStack -> {
           Component name = Component.literal(pStack.getCount() + " " + pStack.getHoverName().getString()).withStyle(Style.EMPTY.withColor(TextColor.parseColor(Integer.toHexString(getColor(pStack).get()))));
           pTooltipComponents.add(name);
        }, () -> {
            pTooltipComponents.add(Component.translatable("tooltip.flower_painter.empty").withStyle(ChatFormatting.GRAY));
        });
    }

    public static Optional<ItemStack> getDye(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTagElement("dye");
        return tag == null ? Optional.empty() : Optional.of(ItemStack.of(tag));
    }

    public static Optional<Integer> getColor(ItemStack itemStack) {
        var dye = getDye(itemStack);
        return dye.map(stack -> ((DyeItem) stack.getItem()).getDyeColor().getTextColor());
    }

    private void playRemoveOneSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }
}

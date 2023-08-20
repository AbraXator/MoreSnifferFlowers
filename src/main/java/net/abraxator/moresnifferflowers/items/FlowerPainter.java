package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.blocks.blockentities.CaulorflowerBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FlowerPainter extends Item {
    public FlowerPainter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);

        if(level.getBlockEntity(blockPos) instanceof CaulorflowerBlockEntity entity && pContext.getHand() == InteractionHand.MAIN_HAND) {
            ItemStack stack = pContext.getItemInHand();

            if(!player.isShiftKeyDown()) {
                colorOne(stack, entity, level, blockPos, blockState);
            } else {
                colorColumn(stack, level, blockPos);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    private void colorOne(ItemStack stack, CaulorflowerBlockEntity entity, Level level, BlockPos blockPos, BlockState blockState) {
            FlowerPainter.getDye(stack).ifPresentOrElse(
                    itemStack -> {
                        entity.dye = itemStack;
                        itemStack.shrink(1);
                        addDye(stack, itemStack);
                    },
                    () -> {
                        entity.dye = ItemStack.EMPTY;
                    }
            );
            level.sendBlockUpdated(blockPos, blockState, blockState, 3);
        }

        private void colorColumn(ItemStack stack, Level level, BlockPos blockPos) {
        BlockPos posUp = blockPos.mutable();
        BlockPos posDown = blockPos.mutable();
        while (level.getBlockEntity(posUp) instanceof CaulorflowerBlockEntity entity1) {
            if(getDye(stack).isEmpty()) break;
            colorOne(stack, entity1, level, posUp, level.getBlockState(posUp));
            posUp = posUp.above();
        }

        while (level.getBlockEntity(posDown) instanceof CaulorflowerBlockEntity entity1) {
            if(getDye(stack).isEmpty()) break;
            colorOne(stack, entity1, level, posDown, level.getBlockState(posDown));
            posDown = posDown.below();
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if(pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if(pOther.isEmpty()) {
                remove(pStack).ifPresent(itemStack -> {
                    playRemoveOneSound(pPlayer);
                    pAccess.set(itemStack);
                });
            } else {
                int i = add(pStack, pOther);
                if(i > 0) {
                    this.playInsertSound(pPlayer);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private int add(ItemStack pStack, ItemStack pOther) {
        if (!pOther.isEmpty() && pOther.getItem() instanceof DyeItem dyeItem) {
            ItemStack newStack;
            var dyeOptional = getDye(pStack);
            int k = 0;
            int toShrink = 0;
            if(dyeOptional.isPresent()) {
                ItemStack dye = dyeOptional.get();
                if (ItemStack.isSameItem(dye, pOther)) {
                    if (dye.getCount() == 64) {
                        return 0;
                    } else {
                        int dyeInside = dye.getCount();
                        int freeSpace = 64 - dyeInside;
                        int otherCount = pOther.getCount();

                        k = pOther.getCount() + dye.getCount();
                        toShrink = k;
                        newStack = dye.copyWithCount(k);

                        if(freeSpace < otherCount) {
                            addDye(pStack, newStack.copyWithCount(dyeInside + freeSpace));
                            pOther.setCount(otherCount - freeSpace);
                            return otherCount - freeSpace;
                        }
                    }
                } else {
                    return 0;
                }
            } else {
                k = pOther.getCount();
                toShrink = k;
                newStack = pOther.copyWithCount(k);
            }
            pOther.shrink(toShrink);
            addDye(pStack, newStack);
            return k;
        } else {
            return 0;
        }
    }

    private Optional<ItemStack> remove(ItemStack pStack) {
        var itemStack = getDye(pStack);
        if(itemStack.isPresent()) {
            ItemStack itemStack1 = itemStack.get();
            addDye(pStack, ItemStack.EMPTY);
            return Optional.of(itemStack1);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        getDye(pStack).ifPresentOrElse(itemStack -> {
           Component name = Component.literal(itemStack.getCount() + " " + itemStack.getHoverName().getString()).withStyle(Style.EMPTY.withColor(TextColor.parseColor(Integer.toHexString(getColor(pStack).get()))));
           pTooltipComponents.add(name);
        }, () -> {
            pTooltipComponents.add(Component.translatable("tooltip.flower_painter.empty").withStyle(ChatFormatting.GRAY));
        });
    }

    public static void addDye(ItemStack stack, ItemStack dye) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.put("dye", dye.serializeNBT());
        stack.setTag(tag);
    }

    public static Optional<ItemStack> getDye(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        ItemStack stack = ItemStack.of(tag.getCompound("dye"));
        return stack.getItem() instanceof DyeItem ? Optional.of(stack) : Optional.empty();
    }

    public static Optional<Integer> getColor(ItemStack itemStack) {
        var dye = getDye(itemStack);
        return dye.map(stack -> ((DyeItem) stack.getItem()).getDyeColor().getFireworkColor());
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

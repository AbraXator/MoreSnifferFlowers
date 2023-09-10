package net.abraxator.moresnifferflowers.items;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.CaulorflowerBlock;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class DyespriaItem extends Item {
    public static final Map<DyeColor, Integer> COLORS = Util.make(Maps.newLinkedHashMap(), dyeColorHexFormatMap -> {
        dyeColorHexFormatMap.put(DyeColor.WHITE, 0xFFD9D9D9);
        dyeColorHexFormatMap.put(DyeColor.LIGHT_GRAY, 0xFF696969);
        dyeColorHexFormatMap.put(DyeColor.GRAY, 0xFF4C4C4C);
        dyeColorHexFormatMap.put(DyeColor.BLACK, 0xFF1E1E1E);
        dyeColorHexFormatMap.put(DyeColor.BROWN, 0xFF724727);
        dyeColorHexFormatMap.put(DyeColor.RED, 0xFFD0021B);
        dyeColorHexFormatMap.put(DyeColor.ORANGE, 0xFFFF8300);
        dyeColorHexFormatMap.put(DyeColor.YELLOW, 0xFFFFE100);
        dyeColorHexFormatMap.put(DyeColor.LIME, 0xFF41CD34);
        dyeColorHexFormatMap.put(DyeColor.GREEN, 0xFF287C23);
        dyeColorHexFormatMap.put(DyeColor.CYAN, 0xFF00AACC);
        dyeColorHexFormatMap.put(DyeColor.LIGHT_BLUE, 0xFF4F3FE0);
        dyeColorHexFormatMap.put(DyeColor.BLUE, 0xFF2E388D);
        dyeColorHexFormatMap.put(DyeColor.PURPLE, 0xFF963EC9);
        dyeColorHexFormatMap.put(DyeColor.MAGENTA, 0xFFD73EDA);
        dyeColorHexFormatMap.put(DyeColor.PINK, 0xFFF38BAA);
    });

    public DyespriaItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        if(player != null && pContext.getHand() == InteractionHand.MAIN_HAND) {
            ItemStack stack = pContext.getItemInHand();

            CompoundTag tag = stack.getOrCreateTagElement(MoreSnifferFlowers.MOD_ID);
            MoreSnifferFlowers.LOGGER.info(tag.getString("dye"));
            MoreSnifferFlowers.LOGGER.info(String.valueOf(tag.getInt("count")));

            if(!player.isShiftKeyDown()) {
                colorOne(stack, level, blockPos, blockState);
            } else {
                colorColumn(stack, level, blockPos);
            }
            level.playSound(player, blockPos, SoundEvents.DYE_USE, SoundSource.BLOCKS);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    public static void colorOne(ItemStack stack, Level level, BlockPos blockPos, BlockState blockState) {
        if(level.getBlockState(blockPos).is(ModBlocks.CAULORFLOWER.get())) {
                DyespriaItem.getDyeAndCount(stack).ifPresentOrElse(
                        itemStack -> {
                            level.setBlock(
                                    blockPos,
                                    blockState.setValue(CaulorflowerBlock.COLOR, itemStack.getFirst().getDyeColor()).setValue(CaulorflowerBlock.HAS_COLOR, true),
                                    3);
                            removeOneDye(stack);
                        },
                        () -> {
                            level.setBlock(blockPos, blockState.setValue(CaulorflowerBlock.HAS_COLOR, false), 3);
                        }
                );
                level.sendBlockUpdated(blockPos, blockState, blockState, 1);
            }
        }

        private void colorColumn(ItemStack stack, Level level, BlockPos blockPos) {
        BlockPos posUp = blockPos.mutable();
        BlockPos posDown = blockPos.mutable();
        while (level.getBlockState(posUp).is(ModBlocks.CAULORFLOWER.get())) {
            colorOne(stack, level, posUp, level.getBlockState(posUp));
            posUp = posUp.above();
        }

        while (level.getBlockState(posDown).is(ModBlocks.CAULORFLOWER.get())) {
            colorOne(stack, level, posDown, level.getBlockState(posDown));
            posDown = posDown.below();
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack dyespria, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if(pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if(pOther.isEmpty()) {
                remove(dyespria).ifPresent(itemStack -> {
                    playRemoveOneSound(pPlayer);
                    pAccess.set(itemStack);
                });
            } else {
                if(add(dyespria, pOther)) {
                    this.playInsertSound(pPlayer);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean add(ItemStack dyespria, ItemStack pOther) {
        if (!pOther.isEmpty()) {
            DyeItem dyeInside = (DyeItem) pOther.getItem();
            int amountToInsert = pOther.getCount();
            int amountInside = 0;
            var dyeOptional = getDyeAndCount(dyespria);
            if(dyeOptional.isPresent()) {
                amountInside = dyeOptional.get().getSecond();
            }
            setDyeAndCount(dyespria, dyeInside, amountToInsert + amountInside);
            pOther.setCount(0);
            return true;
        }
        return false;
    }

    private Optional<ItemStack> remove(ItemStack pStack) {
        Optional<Pair<DyeItem, Integer>> optional1 = getDyeAndCount(pStack);
        if(optional1.isPresent()) {
            int toRemove = Math.max(optional1.get().getSecond(), 64);
            setDyeAndCount(pStack, (DyeItem) ItemStack.EMPTY.getItem(), optional1.get().getSecond() - toRemove);
            return Optional.of(new ItemStack(optional1.get().getFirst(), toRemove));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        Component usage = Component.translatableWithFallback(
                "tooltip.dyespria.usage",
                "Right click with dye to insert \nRight click caulorflower to repaint \nSneak to apply to the whole column \n")
                .withStyle(ChatFormatting.GOLD);

        getDyeAndCount(pStack).ifPresentOrElse(dyeAndCount -> {
           Component name = Component.literal(
                   dyeAndCount.getFirst().getDyeColor().getName() + " - " + dyeAndCount.getSecond())
                   .withStyle(Style.EMPTY.withColor(TextColor.parseColor(Integer.toHexString(getColorForDye(dyeAndCount.getFirst().getDyeColor())))));

           pTooltipComponents.add(usage);
           pTooltipComponents.add(name);
        }, () -> {
            pTooltipComponents.add(usage);
            pTooltipComponents.add(Component.translatableWithFallback("tooltip.dyespria.empty", "Empty").withStyle(ChatFormatting.GRAY));
        });
    }

    public static void removeOneDye(ItemStack stack) {
        getDyeAndCount(stack).ifPresent(dyeItemIntegerPair -> {
            setDyeAndCount(stack, dyeItemIntegerPair.getFirst(), dyeItemIntegerPair.getSecond() - 1);
        });
    }

    public static void setDyeAndCount(ItemStack stack, DyeItem name, int count) {
        CompoundTag tag = stack.getOrCreateTagElement(MoreSnifferFlowers.MOD_ID);
        tag.putString("dye", name.toString());
        tag.putInt("count", count);
        stack.setTag(tag);

        MoreSnifferFlowers.LOGGER.info(tag.getString("dye"));
        MoreSnifferFlowers.LOGGER.info(String.valueOf(tag.getInt("count")));
    }

    public static Optional<Pair<DyeItem, Integer>> getDyeAndCount(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTagElement(MoreSnifferFlowers.MOD_ID);
        Item dyeItem =  ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("dye")));
        int count = tag.getInt("count");
        return dyeItem instanceof DyeItem ? Optional.of(new Pair<>(((DyeItem) dyeItem), count)) : Optional.empty();
    }

    public static int getColorForDye(DyeColor dyeColor) {
        return COLORS.getOrDefault(dyeColor, -1);
    }

    public static int colorForDyespria(ItemStack stack) {
        if(getDyeAndCount(stack).isPresent())
            return getColorForDye(getDyeAndCount(stack).get().getFirst().getDyeColor());
        else
            return -1;
    }

    private void playRemoveOneSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }
}

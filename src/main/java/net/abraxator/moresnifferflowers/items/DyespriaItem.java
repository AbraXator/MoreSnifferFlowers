package net.abraxator.moresnifferflowers.items;

import com.google.common.collect.Maps;
import net.abraxator.moresnifferflowers.blocks.CaulorflowerBlock;
import net.abraxator.moresnifferflowers.init.ModAdvancementCritters;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);

        if (pContext.getHand() != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if(blockState.is(ModBlocks.CAULORFLOWER.get()) && player instanceof ServerPlayer serverPlayer) {
            ItemStack stack = pContext.getItemInHand();
            if (!player.isShiftKeyDown()) {
                colorOne(stack, level, blockPos, blockState);
            } else {
                colorColumn(stack, level, blockPos);
            }
            level.playSound(player, blockPos, SoundEvents.DYE_USE, SoundSource.BLOCKS);
            ModAdvancementCritters.USED_DYESPRIA.get().trigger(serverPlayer);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public static void colorOne(ItemStack stack, Level level, BlockPos blockPos, BlockState blockState) {
        Dye dye = getDye(stack);

        if(!dye.isEmpty()) {
            level.setBlock(blockPos, blockState.setValue(CaulorflowerBlock.COLOR, dye.color).setValue(CaulorflowerBlock.HAS_COLOR, true), 3);
            setDye(stack, stackFromDye(new Dye(dye.color, dye.amount - 1)));
        } else {
            level.setBlock(blockPos, blockState.setValue(CaulorflowerBlock.HAS_COLOR, false), 3);
        }

        level.sendBlockUpdated(blockPos, blockState, blockState, 1);
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
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if(pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if(pOther.isEmpty()) {
                pAccess.set(remove(pStack));
                playRemoveOneSound(pPlayer);
            } else {
                if(add(pStack, pOther)) {
                    this.playInsertSound(pPlayer);
                }
            }
            return true;
        }
        return false;
    }

    private boolean add(ItemStack pStack, ItemStack pOther) {
        Dye dye = getDye(pStack);
        if(!pOther.isEmpty()) {
            if(!dye.isEmpty()) {
                int amountInside = dye.amount;
                int freeSpace = 64 - amountInside;
                if(freeSpace <= 0) return false;
                else {
                    dye = new Dye(dye.color, freeSpace);
                    setDye(pStack, stackFromDye(dye));
                    pOther.shrink(freeSpace);
                    return true;
                }
            } else {
                setDye(pStack, pOther);
                pOther.setCount(0);
                return true;
            }
        }
        return false;
    }

    private ItemStack remove(ItemStack pStack) {
        Dye dye = getDye(pStack);
        if(!dye.isEmpty()) {
            setDye(pStack, DyeColor.WHITE, 0);
            return stackFromDye(dye);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        Dye dye = getDye(pStack);
        Component usage = Component.translatableWithFallback("tooltip.dyespria.usage", "Right click with dye to insert \nRight click caulorflower to repaint \nSneak to apply to the whole column \n").withStyle(ChatFormatting.GOLD);

        if(!dye.isEmpty()) {
            Component name = Component.literal(dye.amount + " - " + dye.color.getName()).withStyle(Style.EMPTY.withColor(TextColor.parseColor(Integer.toHexString(dye.color.getTextColor())).get().orThrow()));

            pTooltipComponents.add(usage);
            pTooltipComponents.add(name);
        } else {
            pTooltipComponents.add(usage);
            pTooltipComponents.add(Component.translatableWithFallback("tooltip.dyespria.empty", "Empty").withStyle(ChatFormatting.GRAY));
        }
    }

    public static void setDye(ItemStack stack, ItemStack dye) {
        setDye(stack, ((DyeItem) dye.getItem()).getDyeColor(), dye.getCount());
    }

    public static void setDye(ItemStack stack, DyeColor dyeColor, int amount) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("color", dyeColor.getId());
        tag.putInt("amount", amount);
        stack.setTag(tag);
    }

    public static Dye getDye(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        int colorId = tag.getInt("color");
        int amount = tag.getInt("amount");

        return new Dye(DyeColor.byId(colorId), amount);
    }

    public static ItemStack stackFromDye(Dye dye) {
        return new ItemStack(DyeItem.byColor(dye.color), dye.amount);
    }

    public static int colorForDye(DyeColor dyeColor) {
        return COLORS.getOrDefault(dyeColor, -1);
    }

    private void playRemoveOneSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    public record Dye(DyeColor color, int amount) {
        public boolean isEmpty() {
            return Dye.this.amount <= 0;
        }
    }
}

package net.abraxator.moresnifferflowers.items;

import com.google.common.collect.Maps;
import net.abraxator.moresnifferflowers.blockentities.DyespriaPlantBlockEntity;
import net.abraxator.moresnifferflowers.colors.Colorable;
import net.abraxator.moresnifferflowers.colors.Dye;
import net.abraxator.moresnifferflowers.init.ModAdvancementCritters;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class DyespriaItem extends Item implements Colorable {
    public DyespriaItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        ItemStack stack = pContext.getItemInHand();

        if (pContext.getHand() != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        if(blockState.is(ModBlocks.CAULORFLOWER.get()) && player instanceof ServerPlayer serverPlayer && level instanceof ServerLevel serverLevel) {
            if (!player.isShiftKeyDown()) {
                colorOne(stack, serverLevel, blockPos, blockState);
            } else {
                colorColumn(stack, serverLevel, blockPos);
            }
            level.playSound(player, blockPos, SoundEvents.DYE_USE, SoundSource.BLOCKS);
            ModAdvancementCritters.USED_DYESPRIA.trigger(serverPlayer);
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            level.setBlockAndUpdate(blockPos.above(), ModBlocks.DYESPRIA_PLANT.get().defaultBlockState().setValue(ModStateProperties.AGE_3, 3));
            stack.setCount(-1);
            if(level.getBlockEntity(blockPos.above()) instanceof DyespriaPlantBlockEntity entity) {
                entity.dye = Dye.getDyeFromStack(stack);
                entity.setChanged();
            }
        }
        return InteractionResult.PASS;
    }

    public void colorOne(ItemStack stack, ServerLevel level, BlockPos blockPos, BlockState blockState) {
        Dye dye = Dye.getDyeFromStack(stack);
        RandomSource randomSource = level.random;

        if(blockState.getValue(ModStateProperties.COLOR).equals(dye.color()) || dye.isEmpty()) {
            return;
        }

        level.setBlockAndUpdate(blockPos, blockState.setValue(ModStateProperties.COLOR, dye.color()));
        ItemStack itemStack = Dye.stackFromDye(new Dye(dye.color(), dye.amount() - 1));
        Dye.setDyeToStack(stack, itemStack, itemStack.getCount());
        particles(randomSource, level, dye, blockPos);
    }

    private void colorColumn(ItemStack stack, ServerLevel level, BlockPos blockPos) {
        BlockPos posUp = blockPos.above().mutable();
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
                ItemStack itemStack = add(pStack, Dye.getDyeFromStack(pStack), pOther);
                pAccess.set(itemStack);
                if(itemStack.isEmpty()) {
                    this.playInsertSound(pPlayer);
                }
            }
            return true;
        }
        return false;
    }

    private void particles(RandomSource randomSource, ServerLevel level, Dye dye, BlockPos blockPos) {
        for(int i = 0; i <= randomSource.nextIntBetweenInclusive(5, 10); i++) {
            level.sendParticles(
                    new DustParticleOptions(dye.isEmpty() ? Vec3.fromRGB24(14013909).toVector3f() : Vec3.fromRGB24(Dye.colorForDye(this, dye.color())).toVector3f(), 1.0F),
                    blockPos.getX() + randomSource.nextDouble(),
                    blockPos.getY() + randomSource.nextDouble(),
                    blockPos.getZ() + randomSource.nextDouble(),
                    1, 0, 0, 0, 0.3D);
        }
    }

    @Override
    public void onAddDye(@Nullable ItemStack destinationStack, ItemStack dye, int amount) {
        Dye.setDyeToStack(destinationStack, dye, amount);
    }

    private ItemStack remove(ItemStack pStack) {
        Dye dye = Dye.getDyeFromStack(pStack);
        if(!dye.isEmpty()) {
            Dye.setDyeColorToStack(pStack, DyeColor.WHITE, 0);
            return Dye.stackFromDye(dye);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        Dye dye = Dye.getDyeFromStack(pStack);
        Component usage = Component.translatableWithFallback("tooltip.dyespria.usage", "Right click with dye to insert \nRight click caulorflower to repaint \nSneak to apply to the whole column \n").withStyle(ChatFormatting.GOLD);

        if(!dye.isEmpty()) {
            Component name = Component
                    .literal(dye.amount() + " - " + WordUtils.capitalizeFully(dye.color()
                            .getName()
                            .toLowerCase()
                            .replaceAll("[^a-z_]", "")
                            .replaceAll("_", " ")))
                    .withStyle(Style.EMPTY
                            .withColor(Dye.colorForDye(this, dye.color())));

            pTooltipComponents.add(usage);
            pTooltipComponents.add(name);
        } else {
            pTooltipComponents.add(usage);
            pTooltipComponents.add(Component.translatableWithFallback("tooltip.dyespria.empty", "Empty").withStyle(ChatFormatting.GRAY));
        }
    }

    private void playRemoveOneSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    @Override
    public Map<DyeColor, Integer> colorValues() {
        return Util.make(Maps.newLinkedHashMap(), dyeColorHexFormatMap -> {
            dyeColorHexFormatMap.put(DyeColor.WHITE, 0xFFFFFFFF);
            dyeColorHexFormatMap.put(DyeColor.LIGHT_GRAY, 0xFF9d979b);
            dyeColorHexFormatMap.put(DyeColor.GRAY, 0xFF474f52);
            dyeColorHexFormatMap.put(DyeColor.BLACK, 0xFF1d1d21);
            dyeColorHexFormatMap.put(DyeColor.BROWN, 0xFF835432);
            dyeColorHexFormatMap.put(DyeColor.RED, 0xFFb5432e);
            dyeColorHexFormatMap.put(DyeColor.ORANGE, 0xFFf89635);
            dyeColorHexFormatMap.put(DyeColor.YELLOW, 0xFFffee53);
            dyeColorHexFormatMap.put(DyeColor.LIME, 0xFF80c71f);
            dyeColorHexFormatMap.put(DyeColor.GREEN, 0xFF5e7c16);
            dyeColorHexFormatMap.put(DyeColor.CYAN, 0xFF00AACC);
            dyeColorHexFormatMap.put(DyeColor.LIGHT_BLUE, 0xFF70d9e4);
            dyeColorHexFormatMap.put(DyeColor.BLUE, 0xFF4753ac);
            dyeColorHexFormatMap.put(DyeColor.PURPLE, 0xFFb15fc2);
            dyeColorHexFormatMap.put(DyeColor.MAGENTA, 0xFFd276b9);
            dyeColorHexFormatMap.put(DyeColor.PINK, 0xFFf8b0c4);
        });
    }
}

package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blockentities.DyespriaPlantBlockEntity;
import net.abraxator.moresnifferflowers.colors.Dye;
import net.abraxator.moresnifferflowers.init.ModAdvancementCritters;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class DyespriaPlantBlock extends BushBlock implements ModCropBlock, ModEntityBlock {
    public DyespriaPlantBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(getAgeProperty(), 0)
                .setValue(ModStateProperties.COLOR, DyeColor.WHITE));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(getAgeProperty()).add(ModStateProperties.COLOR);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if(pPlacer instanceof ServerPlayer serverPlayer) {
            ModAdvancementCritters.PLACED_DYESPRIA_PLANT.trigger(serverPlayer);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pLevel.isClientSide ) {
            return InteractionResult.FAIL;
        }
        
        if (isMaxAge(pState) && pLevel.getBlockEntity(pPos) instanceof DyespriaPlantBlockEntity entity && pHand.equals(InteractionHand.MAIN_HAND)) {
            var item = pPlayer.getItemInHand(pHand).copy();
            if (item.getItem() instanceof DyeItem) {
                pPlayer.getItemInHand(pHand).setCount(-1);
                pPlayer.addItem(entity.add(null, entity.dye, item));
                
                return InteractionResult.sidedSuccess(pLevel.isClientSide());
            } else if (!entity.dye.isEmpty()) {
                pPlayer.addItem(Dye.stackFromDye(entity.removeDye()));
                
                return InteractionResult.sidedSuccess(pLevel.isClientSide());
            }
        }
            
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if(!pState.is(pNewState.getBlock()) && pLevel.getBlockEntity(pPos) instanceof DyespriaPlantBlockEntity entity && isMaxAge(pState)) {
            var dyespria = ModItems.DYESPRIA.get().getDefaultInstance();
            Dye.setDyeColorToStack(dyespria, entity.dye.color(), entity.dye.amount());
            
            Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), dyespria);   
        }
        
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public boolean mayPlaceOn(BlockState pState) {
        return pState.is(BlockTags.DIRT);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return ModStateProperties.AGE_3;
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return isMaxAge(pState);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        makeGrowOnTick(this, pState, pLevel, pPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return !isMaxAge(pState);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        makeGrowOnBonemeal(pLevel, pPos, pState);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DyespriaPlantBlockEntity(pPos, pState);
    }
}

package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blocks.blockentities.ModBlockEntity;
import net.abraxator.moresnifferflowers.blocks.blockentities.RebrewingStandBlockEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class BaseRebrewingStandBlock extends Block implements ModEntityBlock {
    public static final BooleanProperty[] HAS_BOTTLE = new BooleanProperty[]{BlockStateProperties.HAS_BOTTLE_0, BlockStateProperties.HAS_BOTTLE_1, BlockStateProperties.HAS_BOTTLE_2};
    
    public BaseRebrewingStandBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if(pState.is(ModBlocks.REBREWING_STAND_BOTTOM.get())) super.playerWillDestroy(pLevel, pPos.above(), pState, pPlayer);
        if(pState.is(ModBlocks.REBREWING_STAND_TOP.get())) super.playerWillDestroy(pLevel, pPos.below(), pState, pPlayer);
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockPos blockPos = pPos;
            if(pLevel.getBlockState(pPos).is(ModBlocks.REBREWING_STAND_BOTTOM.get())) {
                blockPos = blockPos.above();
            }
            
            BlockEntity entity = pLevel.getBlockEntity(blockPos);
            if (entity instanceof RebrewingStandBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer) pPlayer), ((RebrewingStandBlockEntity) entity), blockPos);
            }

            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockPos = pContext.getClickedPos();
        BlockPos blockPos1 = blockPos.above();
        Level level = pContext.getLevel();

        return level.getBlockState(blockPos1).canBeReplaced(pContext) ? this.defaultBlockState() : null;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if(!pLevel.isClientSide) {
            BlockPos blockPos = pPos.above();
            pLevel.setBlockAndUpdate(blockPos, ModBlocks.REBREWING_STAND_TOP.get().defaultBlockState());
            pState.updateNeighbourShapes(pLevel, blockPos, 3);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BrewingStandBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide) return null;
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if (pBlockEntity instanceof RebrewingStandBlockEntity) {
                ((RebrewingStandBlockEntity) pBlockEntity).tick(pLevel); 
            }
        };
    }
}

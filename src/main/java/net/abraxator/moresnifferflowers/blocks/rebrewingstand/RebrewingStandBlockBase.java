package net.abraxator.moresnifferflowers.blocks.rebrewingstand;

import net.abraxator.moresnifferflowers.blockentities.RebrewingStandBlockEntity;
import net.abraxator.moresnifferflowers.blocks.ModEntityDoubleTallBlock;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class RebrewingStandBlockBase extends ModEntityDoubleTallBlock {
    public static final BooleanProperty[] HAS_BOTTLE = new BooleanProperty[]{BlockStateProperties.HAS_BOTTLE_0, BlockStateProperties.HAS_BOTTLE_1, BlockStateProperties.HAS_BOTTLE_2};
    public static final VoxelShape BASE = Block.box(0, 0, 0, 16, 1, 16);
    public static VoxelShape ROD_UPPER = Block.box(6.5, 0, 6.5, 9.5, 14, 9.5);
    public static VoxelShape ROD_LOWER = Block.box(6.5, 0, 6.5, 9.5, 16, 9.5);
    
    public RebrewingStandBlockBase(Properties pProperties) {
        super(pProperties);
        if(!(this instanceof RebrewingStandBlockTop)) {
            defaultBlockState().setValue(HAS_BOTTLE[0], false).setValue(HAS_BOTTLE[1], false).setValue(HAS_BOTTLE[2], false);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HAS_BOTTLE);
    }
    
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.or(BASE, ROD_LOWER);
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

    @Override
    public Block getLowerBlock() {
        return ModBlocks.REBREWING_STAND_BOTTOM.get();
    }

    @Override
    public Block getUpperBlock() {
        return ModBlocks.REBREWING_STAND_TOP.get();
    }
}

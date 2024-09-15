package net.abraxator.moresnifferflowers.blocks;

import net.abraxator.moresnifferflowers.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.init.ModParticles;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.ticks.ScheduledTick;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiantCropBlock extends Block implements ModEntityBlock {
    public GiantCropBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(ModStateProperties.CENTER, false));
    }

    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 1.0F;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if(pLevel.getBlockEntity(pPos) instanceof GiantCropBlockEntity entity) {
            if(entity.state == 1) {
                entity.canGrow = true;
            } else if (entity.state == 2) {
                var blockPos = entity.pos2.mutable().move(1, 0, 1).immutable();
                List<ItemStack> drops = new ArrayList<>();

                if(pLevel.getBlockState(blockPos).is(this)) {
                    BlockPos.betweenClosed(entity.pos1, entity.pos2).forEach(blockPos1 -> {
                        if(pLevel.getBlockState(blockPos1).is(this)) {
                            LootParams.Builder params = new LootParams.Builder(pLevel).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withParameter(LootContextParams.ORIGIN, blockPos1.getCenter());

                            drops.addAll(pLevel.getBlockState(blockPos1).getDrops(params));
                            pLevel.destroyBlock(blockPos1, false);
                        }
                    });
                }


                BoblingSackBlock.spawnSack(pLevel, blockPos, drops);
            }
        }
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        if(pState.getValue(ModStateProperties.CENTER)) {
            pLevel.getBlockTicks().schedule(new ScheduledTick<>(this, pPos, pLevel.getGameTime() + 7, pLevel.nextSubTickCount()));
            if(pLevel.getBlockEntity(pPos) instanceof GiantCropBlockEntity entity && entity.state == 0) {
                entity.state = 1;
            }
        
            if(pLevel instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ModParticles.GIANT_CROP.get(), pPos.getCenter().x, pPos.getCenter().y, pPos.getCenter().z, 1, 0, 0, 0, 0);
            }
        }
    }
 
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ModStateProperties.CENTER);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GiantCropBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return tickerHelper(pLevel);
    }
}

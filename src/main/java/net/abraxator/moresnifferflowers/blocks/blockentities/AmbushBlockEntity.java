package net.abraxator.moresnifferflowers.blocks.blockentities;

import net.abraxator.moresnifferflowers.blocks.AmbushBlock;
import net.abraxator.moresnifferflowers.init.ModBlockEntities;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.antlr.v4.runtime.tree.xpath.XPathLexer;

public class AmbushBlockEntity extends BlockEntity {
    public float growProgress;
    public boolean hasAmber;

    public AmbushBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.AMBUSH.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, AmbushBlockEntity entity) {
        if(canGrow(pState, entity.growProgress, entity.hasAmber)) {
            entity.growProgress += 0.01;
            if(entity.growProgress == 1) {
                entity.onGrow(pPos, pState, pLevel);
            }
        }
    }

    public void onGrow(BlockPos blockPos, BlockState state, Level level) {
        this.hasAmber = true;
        level.setBlock(blockPos, ModBlocks.AMBER.get().defaultBlockState(), 3);
    }

    public void resetProgress(BlockPos blockPos, BlockState state, Level level) {
        if(this.hasAmber) {
            this.growProgress = 0;
            level.setBlock(blockPos, state.setValue(AmbushBlock.AGE, 7), 3);
        }
    }

    public static boolean canGrow(BlockState state, float growProgress, boolean hasAmber) {
        return state.getValue(AmbushBlock.AGE) == 7 && growProgress != 1 && !hasAmber;
    }
}
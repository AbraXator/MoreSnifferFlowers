package net.abraxator.moresnifferflowers.entities.goals;

import net.abraxator.moresnifferflowers.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.blocks.BoblingSackBlock;
import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.entities.BoblingEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.items.JarOfBonmeelItem;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.ticks.ScheduledTick;
import org.apache.commons.io.output.ThresholdingOutputStream;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class BoblingGiantCropGoal extends Goal {
    private final BoblingEntity bobling;
    private final int searchArea;
    private final double speed;
    private Vec3 wantedPos;

    public BoblingGiantCropGoal(BoblingEntity bobling, int searchArea, double speed) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.bobling = bobling;
        this.searchArea = searchArea;
        this.speed = speed;
    }

    @Override
    public boolean canUse() {
        var level = this.bobling.level();
        var entityAABB = new AABB(BlockPos.containing(bobling.position())).inflate(searchArea);
        if(level.getBlockStates(entityAABB).anyMatch(blockState -> blockState.is(Blocks.FARMLAND))) {
            var pos = BlockPos.withinManhattanStream(this.bobling.blockPosition(), searchArea, searchArea, searchArea)
                    .filter(blockPos -> {
                        var aabb = AABB.ofSize(blockPos.getCenter(), 2, 0, 2);
                        return level.getBlockStates(aabb).allMatch(blockState -> blockState.is(Blocks.FARMLAND));
                    })
                    .findFirst();
            
            if(pos.isPresent()) {
                this.wantedPos = pos.get().getCenter();
                this.bobling.getNavigation().moveTo(this.wantedPos.x, this.wantedPos.z, this.wantedPos.y, this.speed);

                return true;
            }
        }

        return false;
    }
    
    @Override
    public void tick() {
        if(AABB.ofSize(this.wantedPos, 2.0D, 2.0D, 2.0D).contains(this.bobling.position())) {
            this.wantedPos = this.wantedPos.add(0, 1, 0);
            var giantBlock = Util.getRandom(List.of(ModBlocks.GIANT_CARROT, ModBlocks.GIANT_BEETROOT, ModBlocks.GIANT_NETHERWART, ModBlocks.GIANT_POTATO, ModBlocks.GIANT_WHEAT), this.bobling.getRandom());
            var aabb = AABB.ofSize(wantedPos.add(0, 1, 0), 2, 2, 2);
            var level = this.bobling.level();
            if(level.getBlockStates(aabb).allMatch(blockState -> blockState.is(Blocks.AIR))) {
                BlockPos.betweenClosedStream(aabb).forEach(pos -> {
                    level.setBlockAndUpdate(pos, giantBlock.get().defaultBlockState().setValue(GiantCropBlock.MODEL_POSITION, JarOfBonmeelItem.evaulateModelPos(pos, BlockPos.containing(this.wantedPos))));
                    if(level.getBlockEntity(pos) instanceof GiantCropBlockEntity entity) {
                        entity.pos1 = BlockPos.containing(this.wantedPos).mutable().move(1, 2, 1);
                        entity.pos2 = BlockPos.containing(this.wantedPos).mutable().move(-1, 0, -1);
                    } 
                    
                    var tick = new ScheduledTick<>(level.getBlockState(pos).getBlock(), pos, level.getGameTime() + 50, level.nextSubTickCount());
                    level.getBlockTicks().schedule(tick);
                    this.bobling.remove(Entity.RemovalReason.DISCARDED);
                });
            }
            this.wantedPos = this.wantedPos.add(0, -1, 0);
        }
    }
}

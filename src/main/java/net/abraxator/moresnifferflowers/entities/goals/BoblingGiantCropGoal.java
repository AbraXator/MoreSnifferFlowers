package net.abraxator.moresnifferflowers.entities.goals;

import com.sun.jna.platform.win32.OaIdl;
import net.abraxator.moresnifferflowers.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.blocks.vivicus.VivicusRotatedPillarBlock;
import net.abraxator.moresnifferflowers.entities.BoblingEntity;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.abraxator.moresnifferflowers.items.JarOfBonmeelItem;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.checkerframework.common.returnsreceiver.qual.This;
import org.openjdk.nashorn.internal.objects.NativeWeakMap;

import javax.swing.plaf.ListUI;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        if(level.getBlockStates(entityAABB).anyMatch(blockState -> blockState.is(Blocks.FARMLAND)))
        var blocks = BlockPos.betweenClosedStream(entityAABB)
                .filter(blockPos -> level.getBlockState(blockPos).is(Blocks.FARMLAND))
                .map(BlockPos::immutable)
                .collect(Collectors.toCollection(ArrayList::new));

        for (BlockPos blockPos : blocks) {
            var aabb = AABB.ofSize(blockPos.getCenter(), 2, 0, 2);
            var states = level.getBlockStates(aabb);
            if(states.allMatch(blockState -> blockState.is(Blocks.FARMLAND))) {
                this.wantedPos = blockPos.getCenter();
                return true;
            }
        }

        return false;
    }
        

    @Override
    public void start() {
        this.bobling.getNavigation().moveTo(this.wantedPos.x, this.wantedPos.z, this.wantedPos.y, this.speed);
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
                });
            }
            this.wantedPos = this.wantedPos.add(0, -1, 0);
        }
    }
}

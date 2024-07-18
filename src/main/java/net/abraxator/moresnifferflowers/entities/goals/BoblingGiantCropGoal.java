package net.abraxator.moresnifferflowers.entities.goals;

import net.abraxator.moresnifferflowers.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.entities.BoblingEntity;
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
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.swing.plaf.ListUI;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class BoblingGiantCropGoal extends Goal {
    private final BoblingEntity bobling;
    private final int searchArea;

    public BoblingGiantCropGoal(BoblingEntity bobling, int searchArea) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.bobling = bobling;
        this.searchArea = searchArea;
    }

    @Override
    public boolean canUse() {
        var level = this.bobling.level();
        var blocks = BlockPos.betweenClosedStream(new AABB(BlockPos.containing(bobling.position())).inflate(searchArea)).filter(blockPos -> level.getBlockState(blockPos).is(Blocks.FARMLAND)).toList();
        for(BlockPos blockPos : blocks) {
            var aabb = new AABB(blockPos).inflate(1, 0, 1);
            if(level.getBlockStates(aabb).allMatch(blockState -> blockState.is(Blocks.FARMLAND))) {
                this.bobling.setWantedPos(Optional.of(blockPos));
                return true;
            }
        }
        
        return false;
    }

    @Override
    public void start() {
        this.bobling.moveTo(this.bobling.getWantedPos().getCenter());
    }

    @Override
    public void tick() {
        if(AABB.ofSize(this.bobling.getWantedPos().getCenter(), 2.0D, 2.0D, 2.0D).contains(this.bobling.position())) {
            String list = BuiltInRegistries.BLOCK.getTag(ModTags.ModBlockTags.GIANT_CROPS).map(holders -> holders.)
            var giantBlock = Util.getRandom(BuiltInRegistries.BLOCK.key()..stream().toList(), this.bobling.getRandom());
            var aabb = AABB.ofSize(this.bobling.getWantedPos().getCenter(), 1, 2, 1);
            var level = this.bobling.level();
            if(BlockPos.betweenClosedStream(aabb).allMatch(blockPos -> level.getBlockState(blockPos).is(Blocks.AIR))) {
                BlockPos.betweenClosedStream(aabb).forEach(pos -> {
                    level.setBlockAndUpdate(pos, giantBlock..defaultBlockState().setValue(GiantCropBlock.MODEL_POSITION, JarOfBonmeelItem.evaulateModelPos(pos, clickedPos)));
                    if(level.getBlockEntity(pos) instanceof GiantCropBlockEntity entity) {
                        entity.pos1 = clickedPos.mutable().move(1, 2, 1);
                        entity.pos2 = clickedPos.mutable().move(-1, 0, -1);
                    } 
                });
            }
        }
    }
}

package net.abraxator.moresnifferflowers.entities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.block.Blocks;

public class BoblingAi {
    protected static Brain<?> makeBrain(Brain<Bobling> brain){
        initRunAwayActivity(brain);
        initIdleActivity(brain);
        initCoreActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.AVOID);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<Bobling> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(new AnimalPanic(2.0F), new LookAtTargetSink(45, 90), new MoveToTargetSink()));
    }

    private static void initIdleActivity(Brain<Bobling> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(Pair.of(10, new BoblingSitting(UniformInt.of(150, 250), 30.0F, 0.0F, 0.0F, 20))));
    }

    private static void initRunAwayActivity(Brain<Bobling> brain) {
        brain.addActivity(Activity.AVOID, ImmutableList.of(Pair.of(10, SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, 1.5F, 20, false)), Pair.of(3, SetEntityLookTargetSometimes.create(8.0F, UniformInt.of(30, 60)))));
    }

    public static void initMemories(Bobling bobling, RandomSource random) {

    }

    public static void updateActivity(Bobling bobling) {
        bobling.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.AVOID, Activity.CORE, Activity.IDLE));
    }


    public static class BoblingSitting extends RandomLookAround {
        private final int minimalPoseTicks;
        private Direction edgeDir;

        public BoblingSitting(IntProvider pInterval, float pMaxYaw, float pMinPitch, float pMaxPitch, int minimalPoseSeconds) {
            super(pInterval, pMaxYaw, pMinPitch, pMaxPitch);
            this.minimalPoseTicks = minimalPoseSeconds * 20;
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel pLevel, Mob pOwner) {
            return isOnEdge(pLevel, BlockPos.containing(pOwner.position()));
        }

        private boolean isOnEdge(ServerLevel level, BlockPos blockPos) {
            for(Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
                BlockPos blockPos1 = blockPos.relative(direction);
                if(level.getBlockState(blockPos1).is(Blocks.AIR)) {
                    this.edgeDir = direction;
                    return true;
                }
            }

            return false;
        }

        @Override
        protected void start(ServerLevel pLevel, Mob pEntity, long pGameTime) {
            super.start(pLevel, pEntity, pGameTime);
            if(pEntity instanceof Bobling bobling) {
                bobling.setYRot(edgeDir.toYRot());
                if(bobling.isBoblingSitting()) {
                    bobling.standUp();
                } else if(!bobling.isPanicking()) {
                    bobling.sitDown();
                }
            }
        }
    }
}

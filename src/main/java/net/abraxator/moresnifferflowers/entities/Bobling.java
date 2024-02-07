package net.abraxator.moresnifferflowers.entities;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import net.abraxator.moresnifferflowers.init.ModEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Bobling extends Animal {
    //public static final ImmutableList<SensorType<? extends Sensor<? super Bobling>>> SENSOR_TYPES = ImmutableList.
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULE_TYPES = ImmutableList.of(MemoryModuleType.AVOID_TARGET);
    public static final EntityDataAccessor<Long> LAST_POSE_CHANGE_TICK = SynchedEntityData.defineId(Bobling.class, EntityDataSerializers.LONG);
    public final AnimationState sitDownAnimationState = new AnimationState();
    public final AnimationState sitPoseAnimationState = new AnimationState();
    public final AnimationState standUpAnimationState = new AnimationState();
    private static final EntityDimensions SITTING_DIMENSION = EntityDimensions.scalable(ModEntityTypes.BOBLING.get().getWidth(), ModEntityTypes.BOBLING.get().getHeight() - 0.6F);

    public Bobling(EntityType<? extends Bobling> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new BoblingMoveControl();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putLong("LastPoseTick", this.entityData.get(LAST_POSE_CHANGE_TICK));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        long i = pCompound.getLong("LastPoseTick");
        if(i < 0L) {
            this.setPose(Pose.SITTING);
        }

        this.resetLastPoseChangeTick(i);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.8F).add(Attributes.FOLLOW_RANGE, 10.0F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LAST_POSE_CHANGE_TICK, 0L);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        this.resetLastPoseChangeTickToFullStand(pLevel.getLevel().getGameTime());
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected Brain.Provider<Bobling> brainProvider() {
        return Brain.provider(MEMORY_MODULE_TYPES, ImmutableList.of());
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return pPose == Pose.SITTING ? SITTING_DIMENSION.scale(this.getScale()) : super.getDimensions(pPose);
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("BoblingBrain");
        Brain<?> brain = this.getBrain();
        ((Brain<Bobling>) brain).tick(((ServerLevel) this.level()), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("BoblingActivityUpdate");
        this.level().getProfiler().pop();
        super.customServerAiStep();
    }

    @Override
    public void tick() {
        super.tick();

        if(this.refuseToMove()) {
            clampHeadRotationToBody(this, 30);
        }

        if(this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if(this.isBoblingVisuallySitting()) {
            this.standUpAnimationState.stop();
            if(this.isVisuallySittingDown()) {
                this.sitDownAnimationState.startIfStopped(this.tickCount);
                this.sitPoseAnimationState.stop();
            } else {
                this.sitDownAnimationState.stop();
                this.sitPoseAnimationState.startIfStopped(this.tickCount);
            }
        } else {
            this.sitDownAnimationState.stop();;
            this.sitPoseAnimationState.stop();
            this.standUpAnimationState.animateWhen(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.tickCount);
        }
    }

    public void updateWalkAnimation(float f) {
        float f2;
        if(this.getPose() == Pose.STANDING) {
            f2 = Math.min(f * 6.0f, 1.0F);
        } else {
            f2 = 0.0F;
        }

        this.walkAnimation.update(f2, 0.2F);
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if(this.refuseToMove() && this.onGround()) {
            Vec3 vec3 = new Vec3(0.0D, 1.0D, 0.0D);
            this.setDeltaMovement(this.getDeltaMovement().multiply(vec3));
            pTravelVector = pTravelVector.multiply(vec3);
        }

        super.travel(pTravelVector);
    }

    public boolean refuseToMove() {
        return this.isBoblingSitting() || this.isInPoseTransition();
    }

    @Override
    public boolean canBreed() {
        return false;
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    public boolean isPanicking() {
        return this.getBrain().checkMemory(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_PRESENT);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    public boolean isBoblingSitting() {
        return this.entityData.get(LAST_POSE_CHANGE_TICK) < 0L;
    }

    public boolean isBoblingVisuallySitting() {
        return this.getPoseTime() < 0L != this.isBoblingSitting();
    }

    public boolean isInPoseTransition() {
        long i = this.getPoseTime();
        return i < (long)(this.isBoblingSitting() ? 40 : 52);
    }

    public long getPoseTime() {
        return (long) (this.level().getGameTime() - Mth.abs(this.entityData.get(LAST_POSE_CHANGE_TICK)));
    }

    private boolean isVisuallySittingDown() {
        return this.isBoblingSitting() && this.getPoseTime() < 40L && this.getPoseTime() >= 0L;
    }

    public void sitDown() {
        if (!this.isBoblingSitting()) {
            this.playSound(SoundEvents.CAMEL_SIT, 1.0F, 1.0F);
            this.setPose(Pose.SITTING);
            this.resetLastPoseChangeTick(-this.level().getGameTime());
        }
    }

    public void standUp() {
        if (this.isBoblingSitting()) {
            this.playSound(SoundEvents.CAMEL_STAND, 1.0F, 1.0F);
            this.setPose(Pose.STANDING);
            this.resetLastPoseChangeTick(this.level().getGameTime());
        }
    }

    public void standUpInstantly() {
        this.setPose(Pose.STANDING);
        this.resetLastPoseChangeTickToFullStand(this.level().getGameTime());
    }

    @VisibleForTesting
    public void resetLastPoseChangeTick(long pLastPoseChangeTick) {
        this.entityData.set(LAST_POSE_CHANGE_TICK, pLastPoseChangeTick);
    }

    private void resetLastPoseChangeTickToFullStand(long p_265447_) {
        this.resetLastPoseChangeTick(Math.max(0L, p_265447_ - 52L - 1L));
    }

    @Override
    protected void actuallyHurt(DamageSource pDamageSource, float pDamageAmount) {
        this.standUpInstantly();
        super.actuallyHurt(pDamageSource, pDamageAmount);
    }

    private void clampHeadRotationToBody(Entity p_265624_, float p_265541_) {
        float f = p_265624_.getYHeadRot();
        float f1 = Mth.wrapDegrees(this.yBodyRot - f);
        float f2 = Mth.clamp(Mth.wrapDegrees(this.yBodyRot - f), -p_265541_, p_265541_);
        float f3 = f + f1 - f2;
        p_265624_.setYHeadRot(f3);
    }

    class BoblingMoveControl extends MoveControl {
        public BoblingMoveControl() {
            super(Bobling.this);
        }

        @Override
        public void tick() {
            if(this.operation == Operation.MOVE_TO && Bobling.this.isBoblingSitting() && !Bobling.this.isInPoseTransition()) {
                Bobling.this.standUp();
            }

            super.tick();
        }
    }
}

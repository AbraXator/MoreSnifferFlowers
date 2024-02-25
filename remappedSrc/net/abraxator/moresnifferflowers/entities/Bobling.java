package net.abraxator.moresnifferflowers.entities;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import net.abraxator.moresnifferflowers.init.ModEntityTypes;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.entity.*;
import org.jetbrains.annotations.Nullable;

public class Bobling extends AnimalEntity {
    //public static final ImmutableList<SensorType<? extends Sensor<? super Bobling>>> SENSOR_TYPES = ImmutableList.
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULE_TYPES = ImmutableList.of(MemoryModuleType.AVOID_TARGET);
    public static final TrackedData<Long> LAST_POSE_CHANGE_TICK = DataTracker.registerData(Bobling.class, TrackedDataHandlerRegistry.LONG);
    public final AnimationState sitDownAnimationState = new AnimationState();
    public final AnimationState sitPoseAnimationState = new AnimationState();
    public final AnimationState standUpAnimationState = new AnimationState();
    private static final EntityDimensions SITTING_DIMENSION = EntityDimensions.changing(ModEntityTypes.BOBLING.get().getWidth(), ModEntityTypes.BOBLING.get().getHeight() - 0.6F);

    public Bobling(EntityType<? extends Bobling> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new BoblingMoveControl();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound pCompound) {
        super.writeCustomDataToNbt(pCompound);
        pCompound.putLong("LastPoseTick", this.dataTracker.get(LAST_POSE_CHANGE_TICK));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound pCompound) {
        super.readCustomDataFromNbt(pCompound);
        long i = pCompound.getLong("LastPoseTick");
        if(i < 0L) {
            this.setPose(EntityPose.SITTING);
        }

        this.resetLastPoseChangeTick(i);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return createLivingAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.8F).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10.0F);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(LAST_POSE_CHANGE_TICK, 0L);
    }

    @Override
    public EntityData initialize(ServerWorldAccess pLevel, LocalDifficulty pDifficulty, SpawnReason pReason, @Nullable EntityData pSpawnData, @Nullable NbtCompound pDataTag) {
        this.resetLastPoseChangeTickToFullStand(pLevel.toServerWorld().getTime());
        return super.initialize(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    protected Brain.Profile<Bobling> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULE_TYPES, ImmutableList.of());
    }

    @Override
    protected void initGoals() {
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pPose) {
        return pPose == EntityPose.SITTING ? SITTING_DIMENSION.scaled(this.getScaleFactor()) : super.getDimensions(pPose);
    }

    @Override
    protected void mobTick() {
        this.method_48926().getProfiler().push("BoblingBrain");
        Brain<?> brain = this.getBrain();
        ((Brain<Bobling>) brain).tick(((ServerWorld) this.method_48926()), this);
        this.method_48926().getProfiler().pop();
        this.method_48926().getProfiler().push("BoblingActivityUpdate");
        this.method_48926().getProfiler().pop();
        super.mobTick();
    }

    @Override
    public void tick() {
        super.tick();

        if(this.refuseToMove()) {
            clampHeadRotationToBody(this, 30);
        }

        if(this.method_48926().isClient()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if(this.isBoblingVisuallySitting()) {
            this.standUpAnimationState.stop();
            if(this.isVisuallySittingDown()) {
                this.sitDownAnimationState.startIfNotRunning(this.age);
                this.sitPoseAnimationState.stop();
            } else {
                this.sitDownAnimationState.stop();
                this.sitPoseAnimationState.startIfNotRunning(this.age);
            }
        } else {
            this.sitDownAnimationState.stop();;
            this.sitPoseAnimationState.stop();
            this.standUpAnimationState.setRunning(this.isInPoseTransition() && this.getPoseTime() >= 0L, this.age);
        }
    }

    public void updateLimbs(float f) {
        float f2;
        if(this.getPose() == EntityPose.STANDING) {
            f2 = Math.min(f * 6.0f, 1.0F);
        } else {
            f2 = 0.0F;
        }

        this.limbAnimator.updateLimbs(f2, 0.2F);
    }

    @Override
    public void travel(Vec3d pTravelVector) {
        if(this.refuseToMove() && this.isOnGround()) {
            Vec3d vec3 = new Vec3d(0.0D, 1.0D, 0.0D);
            this.setVelocity(this.getVelocity().multiply(vec3));
            pTravelVector = pTravelVector.multiply(vec3);
        }

        super.travel(pTravelVector);
    }

    public boolean refuseToMove() {
        return this.isBoblingSitting() || this.isInPoseTransition();
    }

    @Override
    public boolean isReadyToBreed() {
        return false;
    }

    @Override
    public boolean canSprintAsVehicle() {
        return true;
    }

    public boolean isPanicking() {
        return this.getBrain().isMemoryInState(MemoryModuleType.IS_PANICKING, MemoryModuleState.VALUE_PRESENT);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld pLevel, PassiveEntity pOtherParent) {
        return null;
    }

    public boolean isBoblingSitting() {
        return this.dataTracker.get(LAST_POSE_CHANGE_TICK) < 0L;
    }

    public boolean isBoblingVisuallySitting() {
        return this.getPoseTime() < 0L != this.isBoblingSitting();
    }

    public boolean isInPoseTransition() {
        long i = this.getPoseTime();
        return i < (long)(this.isBoblingSitting() ? 40 : 52);
    }

    public long getPoseTime() {
        return (long) (this.method_48926().getTime() - MathHelper.abs(this.dataTracker.get(LAST_POSE_CHANGE_TICK)));
    }

    private boolean isVisuallySittingDown() {
        return this.isBoblingSitting() && this.getPoseTime() < 40L && this.getPoseTime() >= 0L;
    }

    public void sitDown() {
        if (!this.isBoblingSitting()) {
            this.playSound(SoundEvents.ENTITY_CAMEL_SIT, 1.0F, 1.0F);
            this.setPose(EntityPose.SITTING);
            this.resetLastPoseChangeTick(-this.method_48926().getTime());
        }
    }

    public void standUp() {
        if (this.isBoblingSitting()) {
            this.playSound(SoundEvents.ENTITY_CAMEL_STAND, 1.0F, 1.0F);
            this.setPose(EntityPose.STANDING);
            this.resetLastPoseChangeTick(this.method_48926().getTime());
        }
    }

    public void standUpInstantly() {
        this.setPose(EntityPose.STANDING);
        this.resetLastPoseChangeTickToFullStand(this.method_48926().getTime());
    }

    @VisibleForTesting
    public void resetLastPoseChangeTick(long pLastPoseChangeTick) {
        this.dataTracker.set(LAST_POSE_CHANGE_TICK, pLastPoseChangeTick);
    }

    private void resetLastPoseChangeTickToFullStand(long p_265447_) {
        this.resetLastPoseChangeTick(Math.max(0L, p_265447_ - 52L - 1L));
    }

    @Override
    protected void applyDamage(DamageSource pDamageSource, float pDamageAmount) {
        this.standUpInstantly();
        super.applyDamage(pDamageSource, pDamageAmount);
    }

    private void clampHeadRotationToBody(Entity p_265624_, float p_265541_) {
        float f = p_265624_.getHeadYaw();
        float f1 = MathHelper.wrapDegrees(this.bodyYaw - f);
        float f2 = MathHelper.clamp(MathHelper.wrapDegrees(this.bodyYaw - f), -p_265541_, p_265541_);
        float f3 = f + f1 - f2;
        p_265624_.setHeadYaw(f3);
    }

    class BoblingMoveControl extends MoveControl {
        public BoblingMoveControl() {
            super(Bobling.this);
        }

        @Override
        public void tick() {
            if(this.state == State.MOVE_TO && Bobling.this.isBoblingSitting() && !Bobling.this.isInPoseTransition()) {
                Bobling.this.standUp();
            }

            super.tick();
        }
    }
}

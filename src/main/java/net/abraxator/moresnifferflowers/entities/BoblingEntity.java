package net.abraxator.moresnifferflowers.entities;

import net.abraxator.moresnifferflowers.entities.goals.BoblingAttackPlayerGoal;
import net.abraxator.moresnifferflowers.entities.goals.BoblingAvoidPlayerGoal;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Optional;

public class BoblingEntity extends PathfinderMob {
    private static final EntityDataAccessor<Boolean> DATA_RUNNING = SynchedEntityData.defineId(BoblingEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<BlockPos>> DATA_WANTED_POS = SynchedEntityData.defineId(BoblingEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Boolean> DATA_PLANTING = SynchedEntityData.defineId(BoblingEntity.class, EntityDataSerializers.BOOLEAN);
    private BoblingAttackPlayerGoal attackPlayerGoal;
    private BoblingAvoidPlayerGoal<Player> avoidPlayerGoal;
    public AnimationState plantingAnimationState = new AnimationState();
    private int plantingProgress = 0;
    private final int MAX_PLANTING_PROGRESS = 26;
    
    public BoblingEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    
    public boolean isRunning() {
        return this.entityData.get(DATA_RUNNING);   
    }
    
    public void setRunning(boolean running) {
        this.entityData.set(DATA_RUNNING, running);
        this.updateGoals();
    }

    public BlockPos getWantedPos() {
        return this.entityData.get(DATA_WANTED_POS).orElse(null);
    }
    
    public void setWantedPos(Optional<BlockPos> wantedPos) {
        this.entityData.set(DATA_WANTED_POS, wantedPos);
    }

    public boolean isPlanting() {
        return this.entityData.get(DATA_PLANTING);
    }

    public void setPlanting(boolean plan) {
        this.entityData.set(DATA_PLANTING, plan);
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("running", this.isRunning());
        pCompound.putBoolean("planting", this.isPlanting());
        if(getWantedPos() != null) {
            pCompound.put("wanted_pos", NbtUtils.writeBlockPos(getWantedPos()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setRunning(pCompound.getBoolean("running"));
        this.setPlanting(pCompound.getBoolean("planting"));
        this.setWantedPos(NbtUtils.readBlockPos(pCompound, "wanted_pos"));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_RUNNING, false);
        pBuilder.define(DATA_WANTED_POS, Optional.empty());
        pBuilder.define(DATA_PLANTING, false);
    }

    @Override
    protected void registerGoals() {
        if(this.attackPlayerGoal == null) {
            this.attackPlayerGoal = new BoblingAttackPlayerGoal(this, 1.5F, false);
        }
        
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, this.attackPlayerGoal);
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    protected void actuallyHurt(DamageSource pDamageSource, float pDamageAmount) {
        super.actuallyHurt(pDamageSource, pDamageAmount);
        if(!this.isRunning()) {
            this.setRunning(true);
        }
    }

    @Override
    public void tick() {
        super.tick();
        
        if(this.isPlanting()) {
            this.plantingProgress++;    
            if(plantingProgress >= MAX_PLANTING_PROGRESS) {
                var blockPos = BlockPos.containing(this.position()).relative(this.getDirection());
                this.level().setBlockAndUpdate(blockPos, ModBlocks.CORRUPTED_SAPLING.get().defaultBlockState());
                this.discard();
            }
        }
    }

    public void updateGoals() {
        if(this.avoidPlayerGoal == null) {
            this.avoidPlayerGoal = new BoblingAvoidPlayerGoal<>(this, Player.class, 16.0F, 1.3F, 1.8F);
        }
        
        if(this.isRunning()) {
            if(this.attackPlayerGoal != null) this.goalSelector.removeGoal(this.attackPlayerGoal);
            
            this.goalSelector.addGoal(1, this.avoidPlayerGoal);
        }
    }
    
    @Override
    public void aiStep() {
        super.aiStep();
        
        if (this.isAlive() && !this.isPlanting() && this.getWantedPos() != null&& this.isRunning() && AABB.ofSize(getWantedPos().getCenter(), 2.0D, 2.0D, 2.0D).contains(this.position())) {
            this.setYRot(this.getDirection().toYRot());
            this.plantingAnimationState.start(this.tickCount);
            this.setPlanting(true);
            this.removeFreeWill();
        }
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.25F).add(Attributes.ATTACK_DAMAGE, 3.0);
    }
}

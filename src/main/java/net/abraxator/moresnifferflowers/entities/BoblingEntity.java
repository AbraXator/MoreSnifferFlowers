package net.abraxator.moresnifferflowers.entities;

import io.netty.buffer.ByteBuf;
import net.abraxator.moresnifferflowers.entities.goals.BoblingAttackPlayerGoal;
import net.abraxator.moresnifferflowers.entities.goals.BoblingAvoidPlayerGoal;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModEntityDataSerializers;
import net.abraxator.moresnifferflowers.init.ModEntityTypes;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.naming.Name;
import java.util.Optional;
import java.util.function.IntFunction;

public class BoblingEntity extends PathfinderMob {
    private static final EntityDataAccessor<Type> DATA_BOBLING_TYPE = SynchedEntityData.defineId(BoblingEntity.class, ModEntityDataSerializers.BOBLING_TYPE.get());
    private static final EntityDataAccessor<Boolean> DATA_RUNNING = SynchedEntityData.defineId(BoblingEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<BlockPos>> DATA_WANTED_POS = SynchedEntityData.defineId(BoblingEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Boolean> DATA_PLANTING = SynchedEntityData.defineId(BoblingEntity.class, EntityDataSerializers.BOOLEAN);
    private BoblingAttackPlayerGoal attackPlayerGoal;
    private BoblingAvoidPlayerGoal<Player> avoidPlayerGoal;
    public AnimationState plantingAnimationState = new AnimationState();
    private int plantingProgress = 0;
    private static final int MAX_PLANTING_PROGRESS = 26;

    public BoblingEntity(EntityType<? extends PathfinderMob> entityType, Level level, Type type) {
        super(entityType, level);
        setBoblingType(type);
    }

    public BoblingEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        this(pEntityType, pLevel, Type.CORRUPTED);
    }

    public BoblingEntity(Level level, Type type) {
        this(ModEntityTypes.BOBLING.get(), level, type);
    }

    public Type getBoblingType() {
        return this.entityData.get(DATA_BOBLING_TYPE);
    }

    public void setBoblingType(Type type) {
        this.entityData.set(DATA_BOBLING_TYPE, type);
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
        pCompound.putInt("bobling_type", getBoblingType().id());
        pCompound.putBoolean("running", this.isRunning());
        pCompound.putBoolean("planting", this.isPlanting());
        if (getWantedPos() != null) {
            pCompound.put("wanted_pos", NbtUtils.writeBlockPos(getWantedPos()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setBoblingType(Type.BY_ID.apply(pCompound.getInt("bobling_type")));
        this.setRunning(pCompound.getBoolean("running"));
        this.setPlanting(pCompound.getBoolean("planting"));
        this.setWantedPos(NbtUtils.readBlockPos(pCompound, "wanted_pos"));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_BOBLING_TYPE, Type.CORRUPTED);
        pBuilder.define(DATA_RUNNING, false);
        pBuilder.define(DATA_WANTED_POS, Optional.empty());
        pBuilder.define(DATA_PLANTING, false);
    }

    @Override
    protected void registerGoals() {
        if (this.attackPlayerGoal == null) {
            this.attackPlayerGoal = new BoblingAttackPlayerGoal(this, 1.5F, false);
        }

        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, this.attackPlayerGoal);
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.8F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    protected void actuallyHurt(DamageSource pDamageSource, float pDamageAmount) {
        super.actuallyHurt(pDamageSource, pDamageAmount);
        if (!this.isRunning()) {
            this.setRunning(true);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isPlanting()) {
            this.plantingProgress++;
            if (plantingProgress >= MAX_PLANTING_PROGRESS) {
                var blockPos = BlockPos.containing(this.position()).relative(this.getDirection());
                this.level().setBlockAndUpdate(blockPos, ModBlocks.CORRUPTED_SAPLING.get().defaultBlockState());
                this.discard();
            }
        }
    }

    public void updateGoals() {
        if (this.avoidPlayerGoal == null) {
            this.avoidPlayerGoal = new BoblingAvoidPlayerGoal<>(this, Player.class, 16.0F, 1.3F, 1.8F);
        }

        if (this.isRunning()) {
            if (this.attackPlayerGoal != null) this.goalSelector.removeGoal(this.attackPlayerGoal);

            this.goalSelector.addGoal(1, this.avoidPlayerGoal);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.isAlive() && !this.isPlanting() && this.getWantedPos() != null && this.isRunning() && AABB.ofSize(getWantedPos().getCenter(), 2.0D, 2.0D, 2.0D).contains(this.position())) {
            this.setYRot(this.getDirection().toYRot());
            this.plantingAnimationState.start(this.tickCount);
            this.setPlanting(true);
            this.removeFreeWill();
        }
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (itemStack.is(ModItems.VIVICUS_ANTIDOTE)) {
            this.setBoblingType(Type.CURED);
            itemStack.shrink(1);

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.25F).add(Attributes.ATTACK_DAMAGE, 3.0);
    }

    public static enum Type implements StringRepresentable {
        CORRUPTED(0, "corrupted"), CURED(1, "cured");

        public static final IntFunction<Type> BY_ID = ByIdMap.continuous(Type::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, Type> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Type::id);
        private final int id;
        private final String name;

        Type(int pId, String name) {
            this.id = pId;
            this.name = name;
        }

        public int id() {
            return this.id;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}

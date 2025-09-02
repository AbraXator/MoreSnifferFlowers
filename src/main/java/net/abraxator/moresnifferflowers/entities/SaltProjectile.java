package net.abraxator.moresnifferflowers.entities;

import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModEntityTypes;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

public class SaltProjectile extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Boolean> CORRUPTED = SynchedEntityData.defineId(SaltProjectile.class, EntityDataSerializers.BOOLEAN);

    public SaltProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public SaltProjectile(Level level, LivingEntity pShooter) {
        super(ModEntityTypes.SALT_PROJECTILE.get(), pShooter, level);
    }

    public SaltProjectile(Level level) {
        super(ModEntityTypes.SALT_PROJECTILE.get(), level);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        var pos = result.getBlockPos();
        var posRelative = result.getBlockPos().relative(result.getDirection());

        var state = this.level().getBlockState(pos);
        var stateRelative = this.level().getBlockState(posRelative);

        if (level().isClientSide) return;
        if (!isCorrupted()){
            if (placeBlockSalt(pos, state)) {
                discard();
            } else if (placeBlockSalt(posRelative, stateRelative)) {
                discard();
            }
        } else {
            if (placeBlockSour(pos, 0)) {
                discard();
            } else if (placeBlockSour(posRelative, 0)) {
                discard();
            }
        }

    }

    public boolean placeBlockSalt(BlockPos pos, BlockState state) {
        Level level = this.level();
        if (state.canBeReplaced()){
            if (!ModBlocks.SALTY_CLUMP.get().defaultBlockState().canSurvive(level, pos)){
                return true;
            }
            level.setBlock(pos, ModBlocks.SALTY_CLUMP.get().defaultBlockState().setValue(ModStateProperties.AMOUNT_4, 1), 2);
            return true;
        }
        if (state.is(ModBlocks.SALTY_CLUMP.get())){
           int amount = state.getValue(ModStateProperties.AMOUNT_4);
           if (amount < 4){
               level.setBlock(pos, state.setValue(ModStateProperties.AMOUNT_4, amount+1), 3);
           } else
               level.setBlock(pos, ModBlocks.DRIPSALT.get().defaultBlockState().setValue(BlockStateProperties.VERTICAL_DIRECTION, Direction.UP), 3);
           return true;
        }
        return false;
    }

    public boolean placeBlockSour(BlockPos pos, int loop) {
        Level level = this.level();
        BlockState newState = level.getBlockState(pos);

        if (loop > 2) return false;

        if (newState.canBeReplaced()) {
            if (!ModBlocks.SOUR_PUDDLE.get().defaultBlockState().canSurvive(level, pos)){
                return true;
            }
            level.setBlock(pos, ModBlocks.SOUR_PUDDLE.get().defaultBlockState(), 3);
            return true;
        }
        if (newState.is(ModBlocks.SOUR_PUDDLE.get())){
            return placeBlockSour(aroundPos(pos, random.nextInt(7)), loop+1);
        }
        return false;
    }

    public static BlockPos aroundPos(BlockPos pos, int direction ) {
        return switch (direction) {
            case 0 -> pos.north().west();
            case 1 -> pos.north();
            case 2 -> pos.north().east();
            case 3 -> pos.east();
            case 4 -> pos.south().east();
            case 5 -> pos.south();
            case 6 -> pos.south().west();
            case 7 -> pos.west();
            default -> pos;
        };
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SALTY_SPICE.get();
    }


    public boolean isCorrupted() {
        return this.entityData.get(CORRUPTED);
    }

    public void setCorrupted(boolean corrupted) {
        this.entityData.set(CORRUPTED, corrupted);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("corrupted", this.isCorrupted());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setCorrupted(tag.getBoolean("corrupted"));

    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(CORRUPTED, false);

    }

}

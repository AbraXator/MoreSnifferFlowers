package net.abraxator.moresnifferflowers.entities;

import net.abraxator.moresnifferflowers.init.ModEntityTypes;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DragonflyProjectile extends ThrowableItemProjectile {
    public DragonflyProjectile(EntityType<? extends DragonflyProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public DragonflyProjectile(Level pLevel, Player player) {
        super(ModEntityTypes.DRAGONFLY.get(), player, pLevel);
        this.setOwner(player);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.DRAGONFLY.get();
    }

    @Override
    public void tick() {
        super.tick();
        var vec3 = this.getDeltaMovement();
        
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * 180.0F / (float)Math.PI));
            this.setXRot((float)(Mth.atan2(vec3.y, d0) * 180.0F / (float)Math.PI));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        double d5 = vec3.x;
        double d6 = vec3.y;
        double d1 = vec3.z;
        double d4 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(d5, d1) * 180.0F / (float)Math.PI));
        this.setXRot((float)(Mth.atan2(d6, d4) * 180.0F / (float)Math.PI));
        this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
        this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
    }

    @Override
    protected void onHit(HitResult pResult) {
        if(level() instanceof ServerLevel serverLevel) {
            var particle = new ItemParticleOption(ParticleTypes.ITEM, ModItems.DRAGONFLY.get().getDefaultInstance());
            serverLevel.sendParticles(particle, getX(), getY(), getZ(), 10, Mth.nextDouble(random, 0, 0.3), Mth.nextDouble(random, 0, 0.3), Mth.nextDouble(random, 0, 0.3), 0);
        }
        super.onHit(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if(pResult.getEntity() instanceof LivingEntity entity) {
            entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 140, 2));
            entity.hurt(this.damageSources().thrown(this, this.getOwner()), 0.3F);
        }
        
        discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        discard();
    }
}

package net.abraxator.moresnifferflowers.entities;

import net.abraxator.moresnifferflowers.init.ModEntityTypes;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class DragonflyProjectile extends ThrowableItemProjectile {
    public DragonflyProjectile(EntityType<? extends DragonflyProjectile> entityType, Level pLevel) {
        super(entityType, pLevel);
    }

    public DragonflyProjectile(Level pLevel, Player player) {
        super(ModEntityTypes.DRAGONFLY.get(), (LivingEntity) player, pLevel);
        this.setOwner(player);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.DRAGONFLY.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if(pResult.getEntity() instanceof LivingEntity entity) {
            entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 140, 2));
        }
        
        discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        discard();
    }
}

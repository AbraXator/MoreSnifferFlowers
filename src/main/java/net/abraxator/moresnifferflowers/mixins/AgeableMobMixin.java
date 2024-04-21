package net.abraxator.moresnifferflowers.mixins;

import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PassiveEntity.class)
public class AgeableMobMixin extends PathAwareEntity {
    protected AgeableMobMixin(EntityType<? extends PathAwareEntity> pEntityType, World pWorld) {
        super(pEntityType, pWorld);
    }

    @ModifyVariable(method = "tickMovement", at = @At(value = "STORE"), ordinal = 0)
    public int moresniffeflowers$tickMovement(int x) {
        if(this.getType() == EntityType.SNIFFER) {
            return x + 1;
        } else return x;
    } 
}

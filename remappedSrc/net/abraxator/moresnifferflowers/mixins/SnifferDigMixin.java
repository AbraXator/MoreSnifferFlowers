package net.abraxator.moresnifferflowers.mixins;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SnifferEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnifferEntity.class)
public abstract class SnifferDigMixin extends AnimalEntity {
    protected SnifferDigMixin(EntityType<? extends AnimalEntity> pEntityType, World pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "Lnet/minecraft/world/entity/animal/sniffer/Sniffer;onDiggingComplete(Z)Lnet/minecraft/world/entity/animal/sniffer/Sniffer;", at = @At("TAIL"), require = 1)
    public void spawnBlock(boolean flag, CallbackInfoReturnable<SnifferEntity> info){
        /*ServerLevel serverLevel = ((ServerLevel) level());
        LootTable loottable = serverLevel.getServer().getLootData().getLootTable(BuiltInLootTables.SNIFFER_DIGGING);
        LootParams lootparams = (new LootParams.Builder(serverLevel)).withParameter(LootContextParams.ORIGIN, this.position().add(this.getForward().scale(2.25D))).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.GIFT);
        MoreSnifferFlowers.LOGGER.info("MIXIN FIRED");
        level().setBlock(BlockPos.containing(this.position().add(this.getForward().scale(2.25D))), ModBlocks.BOBLING_HEAD.get().defaultBlockState(), 3);
        if(this.level().getRandom().nextFloat() < 1 / loottable.getRandomItems(lootparams).size()) {
        }*/
    }
}

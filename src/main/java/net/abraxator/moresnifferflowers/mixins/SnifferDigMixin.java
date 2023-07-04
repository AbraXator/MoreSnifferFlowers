package net.abraxator.moresnifferflowers.mixins;

import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sniffer.class)
public abstract class SnifferDigMixin extends Animal {
    protected SnifferDigMixin(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "Lnet/minecraft/world/entity/animal/sniffer/Sniffer;onDiggingComplete(Z)Lnet/minecraft/world/entity/animal/sniffer/Sniffer;", at = @At("TAIL"), cancellable = false, require = 1, remap = false)
    public void spawnBlock(boolean flag, CallbackInfoReturnable<Sniffer> info){
        ServerLevel serverLevel = ((ServerLevel) level());
        LootTable loottable = serverLevel.getServer().getLootData().getLootTable(BuiltInLootTables.SNIFFER_DIGGING);
        LootParams lootparams = (new LootParams.Builder(serverLevel)).withParameter(LootContextParams.ORIGIN, this.position().add(this.getForward().scale(2.25D))).withParameter(LootContextParams.THIS_ENTITY, this).create(LootContextParamSets.GIFT);
        level().setBlock(BlockPos.containing(this.position().add(this.getForward().scale(2.25D))), ModBlocks.BOBLING_HEAD.get().defaultBlockState(), 3);
        if(this.level().getRandom().nextFloat() < 1 / loottable.getRandomItems(lootparams).size()) {
        }
    }
}

package net.abraxator.moresnifferflowers.worldgen.configurations;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.worldgen.configurations.tree.CorruptedTrunkPlacer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNKS = 
            DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, MoreSnifferFlowers.MOD_ID);
    
    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<CorruptedTrunkPlacer>> CORRUPTED_TRUNK_PLACER = TRUNKS.register("corrupted_trunk_placer", () -> new TrunkPlacerType<>(CorruptedTrunkPlacer.CODEC));
}

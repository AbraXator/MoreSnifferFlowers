package net.abraxator.moresnifferflowers.datagen;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.MSFTrims;
import net.abraxator.moresnifferflowers.init.MSFBannerPatterns;
import net.abraxator.moresnifferflowers.init.MSFWorldGen;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, MSFWorldGen.ConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, MSFWorldGen.PlacedFeatures::bootstrap)
            .add(Registries.TRIM_MATERIAL, MSFTrims.Materials::bootstrap)
            .add(Registries.BANNER_PATTERN, MSFBannerPatterns::bootstrap)
            .add(Registries.TRIM_PATTERN, MSFTrims.Patterns::bootstrap);

    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, BUILDER, Set.of("minecraft", MoreSnifferFlowers.MOD_ID));
    }
}

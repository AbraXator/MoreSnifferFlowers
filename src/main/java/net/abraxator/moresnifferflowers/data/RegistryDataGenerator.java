package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModDamageTypes;
import net.abraxator.moresnifferflowers.init.ModTrimMaterials;
import net.abraxator.moresnifferflowers.init.ModTrimPatterns;
import net.minecraft.data.DataOutput;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {
    public static final RegistryBuilder BUILDER = new RegistryBuilder()
            .addRegistry(RegistryKeys.TRIM_MATERIAL, ModTrimMaterials::bootstrap)
            .addRegistry(RegistryKeys.TRIM_PATTERN, ModTrimPatterns::bootstrap)
            .addRegistry(RegistryKeys.DAMAGE_TYPE, ModDamageTypes::bootstrap);

    public RegistryDataGenerator(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> provider) {
        super(output, provider, BUILDER, Set.of("minecraft", MoreSnifferFlowers.MOD_ID));
    }
}

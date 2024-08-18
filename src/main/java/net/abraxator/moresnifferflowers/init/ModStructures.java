package net.abraxator.moresnifferflowers.init;

import com.mojang.serialization.MapCodec;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.structures.SnowSnifferTemple;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModStructures {
    public static final DeferredRegister<StructureType<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(Registries.STRUCTURE_TYPE, MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<StructureType<?>, StructureType<SnowSnifferTemple>> SNOW_SNIFFER_TEMPLE = DEFERRED_REGISTRY_STRUCTURE.register("snow_sniffer_temple", () -> explicitStructureTypeTyping(SnowSnifferTemple.CODEC));

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(MapCodec<T> structureCodec) {
        return () -> structureCodec;
    }
}


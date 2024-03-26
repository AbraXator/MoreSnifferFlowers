package net.abraxator.moresnifferflowers.init;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;

public class ModPools {
    public static final ResourceKey<StructureTemplatePool> DESERT_SNIFFER_TEMPLE = Pools.createKey("desert_sniffer_temple");
    
    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureProcessorList> processorListHolderGetter = context.lookup(Registries.PROCESSOR_LIST);

        context.register(DESERT_SNIFFER_TEMPLE, new StructureTemplatePool(
                processorListHolderGetter,
                Pair.of(List.of(StructurePoolElement.legacy("desert_sniffer_temple")), 1), StructureTemplatePool.Projection.RIGID));
    }
}

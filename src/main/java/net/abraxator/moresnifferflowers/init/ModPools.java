package net.abraxator.moresnifferflowers.init;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class ModPools {
    public static final ResourceKey<StructureTemplatePool> DESERT_SNIFFER_TEMPLE = Pools.createKey("desert_sniffer_temple");
    
    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        HolderGetter<StructureProcessorList> processorListHolderGetter = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> snifferTempleProcessorListHolder = processorListHolderGetter.getOrThrow(ModProcessorLists.SNIFFER_TEMPLE);
        HolderGetter<StructureTemplatePool> poolHolderGetter = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> emptyPoolHolder = poolHolderGetter.getOrThrow(Pools.EMPTY);

        context.register(DESERT_SNIFFER_TEMPLE, new StructureTemplatePool(
                emptyPoolHolder,
                ImmutableList.of(Pair.of(StructurePoolElement.single("desert_sniffer_temple", snifferTempleProcessorListHolder), 1)), 
                StructureTemplatePool.Projection.RIGID));
    }
}

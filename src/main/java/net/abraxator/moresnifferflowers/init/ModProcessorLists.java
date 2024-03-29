package net.abraxator.moresnifferflowers.init;

import com.google.common.collect.ImmutableList;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;

public class ModProcessorLists {
    public static final ResourceKey<StructureProcessorList> SNIFFER_TEMPLE = createKey("sniffer_temple");

    private static ResourceKey<StructureProcessorList> createKey(String pName) {
        return ResourceKey.create(Registries.PROCESSOR_LIST, MoreSnifferFlowers.loc(pName));
    }

    private static void register(BootstapContext<StructureProcessorList> pContext, ResourceKey<StructureProcessorList> pKey, List<StructureProcessor> pProcessors) {
        pContext.register(pKey, new StructureProcessorList(pProcessors));
    }
    
    public static void bootstrap(BootstapContext<StructureProcessorList> context) {
        register(context, 
                SNIFFER_TEMPLE,
                ImmutableList.of(new BlockRotProcessor(0.05F)));
    }
}

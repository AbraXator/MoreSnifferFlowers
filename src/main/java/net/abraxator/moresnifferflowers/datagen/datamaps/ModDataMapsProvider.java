package net.abraxator.moresnifferflowers.datagen.datamaps;

import com.mojang.datafixers.util.Pair;
import net.abraxator.moresnifferflowers.init.MSFBlocks;
import net.abraxator.moresnifferflowers.init.MSFDataMaps;
import net.abraxator.moresnifferflowers.init.MSFItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModDataMapsProvider extends DataMapProvider {
    public ModDataMapsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        var compostables = this.builder(NeoForgeDataMaps.COMPOSTABLES);
        compostables.add(MSFItems.DAWNBERRY_VINE_SEEDS, new Compostable(0.3F), false);
        compostables.add(MSFItems.DAWNBERRY, new Compostable(0.3F), false);
        compostables.add(MSFItems.AMBUSH_SEEDS, new Compostable(0.3F), false);
        compostables.add(MSFItems.CAULORFLOWER_SEEDS, new Compostable(0.4F), false);
        compostables.add(MSFItems.DYESPRIA_SEEDS, new Compostable(0.4F), false);
        compostables.add(MSFItems.BONMEELIA_SEEDS, new Compostable(0.5F), false);
        compostables.add(MSFItems.CROPRESSED_BEETROOT, new Compostable(1.0F), false);
        compostables.add(MSFItems.CROPRESSED_NETHERWART, new Compostable(1.0F), false);
        compostables.add(MSFItems.CROPRESSED_WHEAT, new Compostable(1.0F), false);
        compostables.add(MSFItems.CROPRESSED_POTATO, new Compostable(1.0F), false);
        compostables.add(MSFItems.CROPRESSED_CARROT, new Compostable(1.0F), false);
        compostables.add(MSFBlocks.CORRUPTED_SAPLING.getId(), new Compostable(1.0F), false);
        compostables.add(MSFBlocks.VIVICUS_SAPLING.getId(), new Compostable(1.0F), false);
        compostables.add(MSFBlocks.CORRUPTED_LEAVES.getId(), new Compostable(1.0F), false);
        compostables.add(MSFBlocks.VIVICUS_LEAVES.getId(), new Compostable(1.0F), false);
        
        var corruptables = this.builder(MSFDataMaps.CORRUPTABLE);
        corruptables.add(holder(Blocks.GRASS_BLOCK), new Corruptable( List.of(
                Pair.of(MSFBlocks.CORRUPTED_GRASS_BLOCK.get(), 15),
                Pair.of(Blocks.COARSE_DIRT, 85)
                )), false);
        corruptables.add(holder(Blocks.DIRT), new Corruptable(Blocks.COARSE_DIRT), false);
        corruptables.add(holder(Blocks.STONE), new Corruptable(Blocks.NETHERRACK), false);
        corruptables.add(holder(Blocks.DEEPSLATE), new Corruptable(Blocks.BLACKSTONE), false);
        corruptables.add(BlockTags.LOGS, new Corruptable(MSFBlocks.DECAYED_LOG.get()), false);
        corruptables.remove(MSFBlocks.CORRUPTED_LOG);
        corruptables.remove(MSFBlocks.DECAYED_LOG);
        corruptables.remove(MSFBlocks.CORRUPTED_WOOD);
        corruptables.remove(MSFBlocks.STRIPPED_CORRUPTED_LOG);
        corruptables.remove(MSFBlocks.STRIPPED_CORRUPTED_WOOD);
        corruptables.add(BlockTags.LEAVES, new Corruptable(Blocks.AIR), false);
        corruptables.remove(MSFBlocks.CORRUPTED_LEAVES);
        corruptables.remove(MSFBlocks.CORRUPTED_LEAVES_BUSH);
    }

    public static @NotNull ResourceLocation holder(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}

package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.worldgen.features.ModConfiguredFeatures;
import net.abraxator.moresnifferflowers.worldgen.features.tree.VivicusTreeGrower;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.neoforged.fml.common.Mod;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower CORRUPTED_TREE = new TreeGrower("corrupted_tree",
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.CORRUPTED_TREE),
            Optional.empty());

    public static final VivicusTreeGrower VIVICUS_TREE = new VivicusTreeGrower("vivicus_tree",
            ModConfiguredFeatures.CURED_VIVICUS_TREE,
            ModConfiguredFeatures.CORRUPTED_VIVICUS_TREE);
}

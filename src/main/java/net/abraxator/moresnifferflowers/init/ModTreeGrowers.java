package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.worldgen.features.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower CORRUPTED_TREE = new TreeGrower("corrupted_tree",
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.CORRUPTED_TREE),
            Optional.empty());

    public static final TreeGrower VIVICUS_TREE = new TreeGrower("vivicus_tree",
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.CORRUPTED_TREE),
            Optional.empty());
}

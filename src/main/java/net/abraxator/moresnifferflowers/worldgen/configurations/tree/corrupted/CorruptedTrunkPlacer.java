package net.abraxator.moresnifferflowers.worldgen.configurations.tree.corrupted;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.worldgen.configurations.ModTrunkPlacerTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class CorruptedTrunkPlacer extends ForkingTrunkPlacer {
    public static final MapCodec<CorruptedTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            p_70161_ -> trunkPlacerParts(p_70161_).apply(p_70161_, CorruptedTrunkPlacer::new)
    );
    
    public CorruptedTrunkPlacer(int p_70148_, int p_70149_, int p_70150_) {
        super(p_70148_, p_70149_, p_70150_);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.CORRUPTED_TRUNK_PLACER.get();
    }

    @Override
    protected boolean validTreePos(LevelSimulatedReader pLevel, BlockPos pPos) {
        return true;
    }
}
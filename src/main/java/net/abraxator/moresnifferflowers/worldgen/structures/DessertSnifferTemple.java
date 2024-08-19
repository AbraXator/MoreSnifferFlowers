package net.abraxator.moresnifferflowers.worldgen.structures;

import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public class DessertSnifferTemple extends Structure {
    protected DessertSnifferTemple(StructureSettings pSettings) {
        super(pSettings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext pContext) {
        return Optional.empty();
    }

    @Override
    public StructureType<?> type() {
        return null;
    }
}

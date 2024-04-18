package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.items.DyespriaItem;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import static net.abraxator.moresnifferflowers.MoreSnifferFlowers.loc;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class ModBlockModelGenerator extends BlockStateProvider {
    public ModBlockModelGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MoreSnifferFlowers.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        VariantBlockStateBuilder rebrewingStandVariantBuilder = getVariantBuilder(ModBlocks.REBREWING_STAND_BOTTOM.get());
        boolean[] bottleConditions = {false, true};
        for (boolean hasBottle0 : bottleConditions) {
            for (boolean hasBottle1 : bottleConditions) {
                for (boolean hasBottle2 : bottleConditions) {
                    String modelCode = (hasBottle0 ? "1" : "0") + (hasBottle1 ? "1" : "0") + (hasBottle2 ? "1" : "0");
                    ModelFile modelFile = rebrewingStandModel(modelCode);
                    rebrewingStandVariantBuilder.partialState()
                            .with(HAS_BOTTLE_0, hasBottle0)
                            .with(HAS_BOTTLE_1, hasBottle1)
                            .with(HAS_BOTTLE_2, hasBottle2)
                            .addModels(new ConfiguredModel(modelFile));
                }
            }
        }
    }
    
    private ModelFile rebrewingStandModel(String index) {
        return models().getExistingFile(loc("block/rebrewing_stand_" + index));
    }
}

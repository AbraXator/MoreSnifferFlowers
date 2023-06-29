package net.abraxator.ceruleanvines.data;

import net.abraxator.ceruleanvines.CeruleanVines;
import net.abraxator.ceruleanvines.blocks.CeruleanVineBlock;
import net.abraxator.ceruleanvines.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateDatagen extends BlockStateProvider {
    final ExistingFileHelper existingFileHelper;

    public BlockStateDatagen(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
        this.existingFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        MultiPartBlockStateBuilder builder = getMultipartBuilder(ModBlocks.CERULEAN_VINE.get());
        PipeBlock.PROPERTY_BY_DIRECTION.entrySet().forEach(e -> {
            Direction dir = e.getKey();
            CeruleanVineBlock.AGE.getAllValues().forEach(integerValue -> {
                int age = integerValue.value();
                builder.part().modelFile(getModelFile(age)).addModel().condition(e.getValue(), true).end();
            });
        });
    }

    private ModelFile.ExistingModelFile getModelFile(int age){
        return new ModelFile.ExistingModelFile(new ResourceLocation(CeruleanVines.MOD_ID,
                "block/cerulean_vine_stage_" + (age + 1)), existingFileHelper);
    }
}

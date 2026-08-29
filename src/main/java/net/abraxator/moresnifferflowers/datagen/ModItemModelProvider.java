package net.abraxator.moresnifferflowers.datagen;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.MSFBlocks;
import net.abraxator.moresnifferflowers.init.MSFItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MoreSnifferFlowers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(MSFItems.BOBLING_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        basicItem(MSFItems.CORRUPTED_SIGN.get());
        basicItem(MSFItems.CORRUPTED_HANGING_SIGN.get());
        basicItem(MSFItems.VIVICUS_SIGN.get());
        basicItem(MSFItems.VIVICUS_HANGING_SIGN.get());
        basicItem(MSFItems.CORRUPTED_BOAT.get());
        basicItem(MSFItems.CORRUPTED_CHEST_BOAT.get());
        basicItem(MSFItems.VIVICUS_BOAT.get());
        basicItem(MSFItems.VIVICUS_CHEST_BOAT.get());
        basicItem(MSFItems.PATTERNFLOWER_SEEDS.get());

        basicItem(MSFItems.BLOCK_PATTERN_PIPES.get());
        basicItem(MSFItems.BLOCK_PATTERN_BRICKS.get());
        basicItem(MSFItems.BLOCK_PATTERN_FOCUS.get());
        basicItem(MSFItems.BLOCK_PATTERN_BUBBLES.get());
        basicItem(MSFItems.BLOCK_PATTERN_CLOUDS.get());
        basicItem(MSFItems.BLOCK_PATTERN_DEEPSLATE.get());
        basicItem(MSFItems.BLOCK_PATTERN_DIAMOND.get());
        basicItem(MSFItems.BLOCK_PATTERN_EYE.get());
        basicItem(MSFItems.BLOCK_PATTERN_HEARTS.get());
        basicItem(MSFItems.BLOCK_PATTERN_HONEYCOMB.get());
        basicItem(MSFItems.BLOCK_PATTERN_PAWS.get());
        basicItem(MSFItems.BLOCK_PATTERN_PRISMARINE.get());
        basicItem(MSFItems.BLOCK_PATTERN_SPROUTS.get());
        basicItem(MSFItems.BLOCK_PATTERN_STARS.get());
        basicItem(MSFItems.BLOCK_PATTERN_COVER.get());
        basicItem(MSFItems.BLOCK_PATTERN_FLOWERS.get());

        basicItem(MSFItems.SALTY_SPICE.get());
        basicItem(MSFItems.SOUR_SPICE.get());
        basicItem(MSFItems.FIERY_SPICE.get());
        basicItem(MSFItems.SWEET_SPICE.get());

        basicItem(MSFBlocks.GIANT_ONION.get().asItem());
        basicItem(MSFBlocks.GIANT_TOMATO.get().asItem());
        basicItem(MSFBlocks.GIANT_CABBAGE.get().asItem());
        basicItem(MSFBlocks.GIANT_RICE.get().asItem());

        basicItem(MSFItems.DEBUG_FLOWER.get());

        basicItem(MSFBlocks.TORCHFLAME.get().asItem());
        basicItem(MSFItems.PLACEHOLDER.get());

        basicItem(MSFItems.MUSIC_DISC_BOBLING.get());
        basicItem(MSFItems.DISC_FRAGMENT_BOBLING.get());


        /*for(int i = 1; i <= ModItemProperties.COPRESSOR_ANIMATION_FRAMES; i++) {
            withExistingParent(ModItems.CROPRESSOR.getId().getPath() + "_animation_" + i, MoreSnifferFlowers.loc("item/cropressor_animation_" + i / ModItemProperties.FRAME_TIME));
            
            modelBuilder.override(i)
                    .predicate(MoreSnifferFlowers.loc("item/cropressor_animation_" + i / ModItemProperties.FRAME_TIME), (float) ((double) 1 / i))
                    .model(new ModelFile.ExistingModelFile(MoreSnifferFlowers.loc("item/cropressor_animation_" + i / ModItemProperties.FRAME_TIME), existingFileHelper));
        }*/
    }
}

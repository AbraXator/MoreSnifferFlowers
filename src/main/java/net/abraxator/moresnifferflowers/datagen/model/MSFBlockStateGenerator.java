package net.abraxator.moresnifferflowers.datagen.model;

import com.google.common.collect.ImmutableMap;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.BonmeeliaBlock;
import net.abraxator.moresnifferflowers.blocks.ModEntityDoubleTallBlock;
import net.abraxator.moresnifferflowers.blocks.ModLayeredCauldronBlock;
import net.abraxator.moresnifferflowers.init.MSFBlocks;
import net.abraxator.moresnifferflowers.init.MSFStateProperties;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.nikdo53.tinymultiblocklib.block.IMultiBlock;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.abraxator.moresnifferflowers.init.MSFBlocks.*;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class MSFBlockStateGenerator extends BlockStateProvider {
    final ExistingFileHelper existingFileHelper;
    public MSFBlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MoreSnifferFlowers.MOD_ID, exFileHelper);
        this.existingFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        //prevents duplicates
        Set<Block> processedParentBlocks = new HashSet<>();

        MSFBlockFamilies.getAllFamilies().forEach(family -> {
            if (family == MSFBlockFamilies.VIVICUS) return;
            ResourceLocation baseId = MoreSnifferFlowers.loc("block/" + BuiltInRegistries.BLOCK.getKey(family.getBaseBlock()).getPath());
            if (!processedParentBlocks.contains(family.getBaseBlock())) {
                this.simpleBlock(family.getBaseBlock());
                processedParentBlocks.add(family.getBaseBlock());
            }

            family.getVariants().forEach((variant, block) -> {
                if (variant == BlockFamily.Variant.WALL_SIGN || processedParentBlocks.contains(block)) return;
                ResourceLocation blockId = MoreSnifferFlowers.loc("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath());
                TriConsumer<BlockFamily, Block, ResourceLocation> consumer = FAMILLY_MAP.get(variant);
                if (consumer == null) {
                    this.simpleBlock(block);
                } else {
                    consumer.accept(family, block, CUSTOM_TEXTURE_VARIANTS.contains(variant) ? blockId : baseId);
                }
                processedParentBlocks.add(block);
            });
        });

        simpleBlock(MSFBlocks.POTTED_DYESPRIA.get(), models().withExistingParent(MSFBlocks.POTTED_DYESPRIA.getId().getPath(), "block/flower_pot_cross").renderType("cutout").texture("plant", blockTexture(MSFBlocks.DYESPRIA_PLANT.get())));
        simpleBlock(MSFBlocks.POTTED_CORRUPTED_SAPLING.get(), models().withExistingParent(MSFBlocks.POTTED_CORRUPTED_SAPLING.getId().getPath(), "block/flower_pot_cross").renderType("cutout").texture("plant", blockTexture(MSFBlocks.CORRUPTED_SAPLING.get())));
        simpleBlock(MSFBlocks.POTTED_VIVICUS_SAPLING.get(), models().withExistingParent(MSFBlocks.POTTED_VIVICUS_SAPLING.getId().getPath(), "block/flower_pot_cross").renderType("cutout").texture("plant", blockTexture(MSFBlocks.VIVICUS_SAPLING.get())));

        multipleVariantsForStates((state, block) -> {
            int age = state.getValue(BonmeeliaBlock.AGE);
            if (age < 3) return modelFile(block, "_stage" + age);
            if (state.getValue(BonmeeliaBlock.HAS_BOTTLE)) return modelFile(block, "_bottle_" + (age - 3));
            if (state.getValue(BonmeeliaBlock.SHOW_HINT)) return modelFile(block, "_outline");
            return modelFile(block, "_empty");
        }, MSFBlocks.BONMEELIA, MSFBlocks.BONWILTIA);

        multipleVariantsForStates((state, block) -> {
            int age = state.getValue(MSFStateProperties.AGE_2);
            if (!IMultiBlock.isCenter(state)) return modelFile(block, "_stage2");
            return modelFile(block, "_stage" + age);
        }, MSFBlocks.BONDRIPIA, MSFBlocks.ACIDRIPIA);


        multipleVariantsForStates((state, block) -> {
            int age = state.getValue(MSFStateProperties.AGE_8);
            if (age < 4){
                return farmlandCrossModel(block, "_stage_" + 0);
            }
            return farmlandCrossModel(block, "_stage_" + age);
            }, MSFBlocks.AMBUSH_TOP, MSFBlocks.GARBUSH_TOP);

        multipleVariantsForStates((state, block) -> {
            int age = state.getValue(MSFStateProperties.AGE_8);
            if (age > 3 && age != 7){
                return farmlandCrossModel(block, "_stage_" + 3);
            }
            return farmlandCrossModel(block, "_stage_" + age);
        }, MSFBlocks.AMBUSH_BOTTOM, MSFBlocks.GARBUSH_BOTTOM);

        variantForStates(MSFBlocks.CORRUPTED_SLUDGE, state -> modelFile(state::getBlock, "_stage_" + (1 + 3 - state.getValue(MSFStateProperties.USES_4))));

        variantForStates(REBREWING_STAND_BOTTOM, state -> {
            String modelCode = (state.getValue(HAS_BOTTLE_0) ? "1" : "0") + (state.getValue(HAS_BOTTLE_1) ? "1" : "0") + (state.getValue(HAS_BOTTLE_2) ? "1" : "0");
            return rebrewingStandModel(modelCode);
        });

        multipleVariantsForStates((state, block) -> modelFile(block, "_level" + state.getValue(ModLayeredCauldronBlock.LEVEL)),
                MSFBlocks.ACID_FILLED_CAULDRON, MSFBlocks.BONMEEL_FILLED_CAULDRON);


        particleOnly(MSFBlocks.BEROOT_CAULDRON);
        empty(MSFBlocks.GIANT_BEETROOT, MSFBlocks.GIANT_CABBAGE, MSFBlocks.GIANT_CARROT,
                MSFBlocks.GIANT_POTATO, MSFBlocks.GIANT_RICE, MSFBlocks.GIANT_TOMATO,
                MSFBlocks.GIANT_ONION, MSFBlocks.GIANT_NETHERWART, MSFBlocks.GIANT_WHEAT
        );

        simpleBlock(CORRUPTED_LEAVES.get());
        simpleState(CORRUPTED_WART, CORRUPTED_LEAVES_BUSH, TORCHFLAME, REBREWING_STAND_TOP);
        simpleBlock(CORRUPTED_GRASS.get(), models().cross(name(CORRUPTED_GRASS), blockTexture(CORRUPTED_GRASS.get())).renderType("cutout"));

        hangingSignBlock((CeilingHangingSignBlock) CORRUPTED_HANGING_SIGN.get(), (WallHangingSignBlock) CORRUPTED_WALL_HANGING_SIGN.get(), key(CORRUPTED_PLANKS.get()).withPrefix("block/"));
        hangingSignBlock((CeilingHangingSignBlock) VIVICUS_HANGING_SIGN.get(), (WallHangingSignBlock) VIVICUS_WALL_HANGING_SIGN.get(), key(VIVICUS_PLANKS.get()).withPrefix("block/"));
        signBlock((StandingSignBlock) VIVICUS_SIGN.get(), (WallSignBlock) VIVICUS_WALL_SIGN.get(), key(VIVICUS_PLANKS.get()).withPrefix("block/") );

    }

    public BlockModelBuilder particleOnlyModel(String name, ResourceLocation texture) {
        return models().getBuilder(name).texture("particle", texture);
    }

    public void particleOnly(Supplier<Block> block) {
        simpleBlock(block.get(), particleOnlyModel(name(block.get()), blockTexture(block.get())));
    }

    public void particleOnly(Supplier<Block> block, ResourceLocation texture) {
        simpleBlock(block.get(), particleOnlyModel(name(block.get()), texture));
    }

    @SafeVarargs
    public final void empty(Supplier<Block>... blocks) {
        for (Supplier<Block> block : blocks) {
            simpleBlock(block.get(), models().getBuilder(name(block.get())));
        }
    }

    public ModelFile farmlandCrossModel(Supplier<Block> block, String... suffix){
        ResourceLocation texture = blockTexture(block.get());
        for (String s : suffix) {
            texture = texture.withSuffix(s);
        }
        return models().getBuilder(name(block)).parent(models().getExistingFile(MoreSnifferFlowers.loc("farmland_cross"))).texture("cross", texture).renderType("cutout");
    }

    @SafeVarargs
    public final void multipleVariantsForStates(BiFunction<BlockState, Supplier<Block>, ModelFile> modelFunction, Supplier<Block> block, Supplier<Block>... blocks) {
        getVariantBuilder(block.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(modelFunction.apply(state, block)).build());
        for (Supplier<Block> blockSupplier : blocks) {
            getVariantBuilder(blockSupplier.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(modelFunction.apply(state, blockSupplier)).build());
        }
    }

    public void variantForStates(Supplier<Block> block, Function<BlockState, ModelFile> modelFunction) {
        getVariantBuilder(block.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(modelFunction.apply(state)).build());
    }

    public void variantForConfiguredStates(Supplier<Block> block, Function<BlockState, ConfiguredModel[]> modelFunction) {
        getVariantBuilder(block.get()).forAllStates(modelFunction);
    }

    @SafeVarargs
    public final void simpleState(Supplier<Block>... blocks){
        for (Supplier<Block> block : blocks) {
            simpleBlock(block.get(), modelFile(block));
        }
    }

    private ModelFile.@NotNull ExistingModelFile modelFile(Supplier<Block> block) {
        return models().getExistingFile(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block.get())));
    }

    private ModelFile.@NotNull ExistingModelFile modelFile(Supplier<Block> block, String suffix) {
        return models().getExistingFile(BuiltInRegistries.BLOCK.getKey(block.get()).withSuffix(suffix));
    }
    
    private ModelFile rebrewingStandModel(String index) {
        return models().getExistingFile(MoreSnifferFlowers.loc("block/rebrewing_stand_" + index));
    }
    
    private ResourceLocation prefix(String path) {
        return MoreSnifferFlowers.loc("textures/" + path);
    }
    
    protected ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    protected String name(Block block) {
        return key(block).getPath();
    }

    protected String name(Supplier<Block> block) {
        return key(block.get()).getPath();
    }


    final Set<BlockFamily.Variant> CUSTOM_TEXTURE_VARIANTS = Set.of(BlockFamily.Variant.DOOR, BlockFamily.Variant.CHISELED,BlockFamily.Variant.CRACKED, BlockFamily.Variant.TRAPDOOR);

    final Map<BlockFamily.Variant, TriConsumer<BlockFamily, Block, ResourceLocation>> FAMILLY_MAP = ImmutableMap.<BlockFamily.Variant, TriConsumer<BlockFamily, Block, ResourceLocation>>builder()
            .put(BlockFamily.Variant.BUTTON, (f,b, r) -> buttonBlock((ButtonBlock) b, r))
            .put(BlockFamily.Variant.DOOR, (f,b, r) -> doorBlock((DoorBlock) b, r.withSuffix("_bottom"), r.withSuffix("_top")))
            .put(BlockFamily.Variant.CHISELED, (f,b, r) -> cubeAll(b))
            .put(BlockFamily.Variant.CRACKED, (f,b, r) -> cubeAll(b))
            .put(BlockFamily.Variant.FENCE,  (f,b, r) -> fenceBlock((FenceBlock) b, r))
            .put(BlockFamily.Variant.FENCE_GATE, (f,b, r) -> fenceGateBlock((FenceGateBlock) b, r))
            .put(BlockFamily.Variant.SIGN, (f,b, r) -> signBlock((StandingSignBlock) f.get(BlockFamily.Variant.SIGN), (WallSignBlock) f.get(BlockFamily.Variant.WALL_SIGN), r))
            .put(BlockFamily.Variant.SLAB, (f,b, r) -> slabBlock((SlabBlock) b, r, r))
            .put(BlockFamily.Variant.STAIRS, (f,b, r) -> stairsBlock((StairBlock) b, r))
            .put(BlockFamily.Variant.PRESSURE_PLATE, (f,b, r) -> pressurePlateBlock((PressurePlateBlock) b, r))
            .put(BlockFamily.Variant.TRAPDOOR,  (f,b, r) -> trapdoorBlock((TrapDoorBlock) b, r, true))
            .put(BlockFamily.Variant.WALL, (f,b, r) -> wallBlock((WallBlock) b, r))
            .build();

}

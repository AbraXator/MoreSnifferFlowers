package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.blockentities.AmbushBlockEntity;
import net.abraxator.moresnifferflowers.blocks.blockentities.BonmeeliaBlockEntity;
import net.abraxator.moresnifferflowers.blocks.blockentities.CropressorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {

    public static final BlockEntityType<AmbushBlockEntity> AMBUSH_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MoreSnifferFlowers.MOD_ID, "ambush_be"),
            FabricBlockEntityTypeBuilder.create(AmbushBlockEntity::new, ModBlocks.AMBUSH).build());

//    public static final BlockEntityType<GiantCropBlockEntity> GIANT_CROP_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MoreSnifferFlowers.MOD_ID, "giant_crop_be"),
//            FabricBlockEntityTypeBuilder.create(GiantCropBlockEntity::new, ModBlocks.GIANT_BEETROOT, ModBlocks.GIANT_CARROT, ModBlocks.GIANT_NETHERWART, ModBlocks.GIANT_WHEAT, ModBlocks.GIANT_POTATO).build(null));

    public static final BlockEntityType<BonmeeliaBlockEntity> BONMEELIA_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MoreSnifferFlowers.MOD_ID, "bonmeelia_be"),
            FabricBlockEntityTypeBuilder.create(BonmeeliaBlockEntity::new, ModBlocks.BONMEELIA).build());

    public static final BlockEntityType<CropressorBlockEntity> CROPRESSOR_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MoreSnifferFlowers.MOD_ID, "cropressor_be"),
            FabricBlockEntityTypeBuilder.create(CropressorBlockEntity::new, ModBlocks.CROPRESSOR).build());

    public static void registerBlockEntities() {
        MoreSnifferFlowers.LOGGER.info("Registering BlockEntities for" + MoreSnifferFlowers.MOD_ID);

    }
}

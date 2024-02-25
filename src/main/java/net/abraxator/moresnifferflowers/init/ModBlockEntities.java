package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.blockentities.AmbushBlockEntity;
import net.abraxator.moresnifferflowers.blocks.blockentities.BonmeeliaBlockEntity;
import net.abraxator.moresnifferflowers.blocks.blockentities.CropressorBlockEntity;
import net.abraxator.moresnifferflowers.blocks.blockentities.GiantCropBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MoreSnifferFlowers.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AmbushBlockEntity>> AMBUSH = BLOCK_ENTITIES.register("ambush", () -> BlockEntityType.Builder.of(AmbushBlockEntity::new, ModBlocks.AMBUSH.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GiantCropBlockEntity>> GIANT_CROP = BLOCK_ENTITIES.register("giant_crop", () -> BlockEntityType.Builder.of(GiantCropBlockEntity::new, ModBlocks.GIANT_CARROT.get(), ModBlocks.GIANT_POTATO.get(), ModBlocks.GIANT_NETHERWART.get(), ModBlocks.GIANT_BEETROOT.get(), ModBlocks.GIANT_WHEAT.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BonmeeliaBlockEntity>> BONMEELIA = BLOCK_ENTITIES.register("bonmeelia", () -> BlockEntityType.Builder.of(BonmeeliaBlockEntity::new, ModBlocks.BONMEELIA.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CropressorBlockEntity>> CROPRESSOR = BLOCK_ENTITIES.register("cropressor", () -> BlockEntityType.Builder.of(CropressorBlockEntity::new, ModBlocks.CROPRESSOR.get()).build(null));
}

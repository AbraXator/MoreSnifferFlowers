package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.blockentities.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MoreSnifferFlowers.MOD_ID);

    public static final RegistryObject<BlockEntityType<AmbushBlockEntity>> AMBUSH = BLOCK_ENTITIES.register("ambush", () -> BlockEntityType.Builder.of(AmbushBlockEntity::new, ModBlocks.AMBUSH.get()).build(null));
    public static final RegistryObject<BlockEntityType<GiantCropBlockEntity>> GIANT_CROP = BLOCK_ENTITIES.register("giant_crop", () -> BlockEntityType.Builder.of(GiantCropBlockEntity::new, ModBlocks.GIANT_CARROT.get(), ModBlocks.GIANT_POTATO.get(), ModBlocks.GIANT_NETHERWART.get(), ModBlocks.GIANT_BEETROOT.get(), ModBlocks.GIANT_WHEAT.get()).build(null));
    public static final RegistryObject<BlockEntityType<BonmeeliaBlockEntity>> BONMEELIA = BLOCK_ENTITIES.register("bonmeelia", () -> BlockEntityType.Builder.of(BonmeeliaBlockEntity::new, ModBlocks.BONMEELIA.get()).build(null));
    public static final RegistryObject<BlockEntityType<CropressorBlockEntity>> CROPRESSOR = BLOCK_ENTITIES.register("cropressor", () -> BlockEntityType.Builder.of(CropressorBlockEntity::new, ModBlocks.CROPRESSOR_OUT.get()).build(null));
    public static final RegistryObject<BlockEntityType<RebrewingStandBlock>> REBREWING_STAND = BLOCK_ENTITIES.register("rebrewing_stand", () -> BlockEntityType.Builder.of(RebrewingStandBlock::new, ModBlocks.REBREWING_STAND_TOP.get()).build(null));
}

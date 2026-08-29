package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blockentities.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.function.Supplier;

public interface MSFBlockEntities {
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MoreSnifferFlowers.MOD_ID);

    DeferredHolder<BlockEntityType<?>, BlockEntityType<GiantCropBlockEntity>> GIANT_CROP = BLOCK_ENTITIES.register("giant_crop", () ->
            BlockEntityType.Builder.of(GiantCropBlockEntity::new, MSFBlocks.GIANT_CARROT.get(), MSFBlocks.GIANT_POTATO.get(),
            MSFBlocks.GIANT_NETHERWART.get(), MSFBlocks.GIANT_BEETROOT.get(), MSFBlocks.GIANT_WHEAT.get(), MSFBlocks.GIANT_ONION.get(),
                    MSFBlocks.GIANT_TOMATO.get(), MSFBlocks.GIANT_CABBAGE.get(), MSFBlocks.GIANT_RICE.get()
            ).build(null));

    DeferredHolder<BlockEntityType<?>, BlockEntityType<XbushBlockEntity>> XBUSH = BLOCK_ENTITIES.register("xbush", () -> BlockEntityType.Builder.of(XbushBlockEntity::new, MSFBlocks.AMBUSH_TOP.get(), MSFBlocks.GARBUSH_TOP.get()).build(null));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<CropressorBlockEntity>> CROPRESSOR = BLOCK_ENTITIES.register("cropressor", () -> BlockEntityType.Builder.of(CropressorBlockEntity::new, MSFBlocks.CROPRESSOR_OUT.get()).build(null));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<RebrewingStandBlockEntity>> REBREWING_STAND = BLOCK_ENTITIES.register("rebrewing_stand", () -> BlockEntityType.Builder.of(RebrewingStandBlockEntity::new, MSFBlocks.REBREWING_STAND_TOP.get()).build(null));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<DyespriaPlantBlockEntity>> DYESPRIA_PLANT = BLOCK_ENTITIES.register("dyespria_plant", () -> BlockEntityType.Builder.of(DyespriaPlantBlockEntity::new, MSFBlocks.DYESPRIA_PLANT.get()).build(null));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<CorruptedSludgeBlockEntity>> CORRUPTED_SLUDGE = BLOCK_ENTITIES.register("corrupted_sludge", () -> BlockEntityType.Builder.of(CorruptedSludgeBlockEntity::new, MSFBlocks.CORRUPTED_SLUDGE.get()).build(null));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<BondripiaBlockEntity>> BONDRIPIA = BLOCK_ENTITIES.register("bondripia", () -> BlockEntityType.Builder.of(BondripiaBlockEntity::new, MSFBlocks.BONDRIPIA.get(), MSFBlocks.ACIDRIPIA.get()).build(null));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<SaltemoneBlockEntity>> SALTEMONE = BLOCK_ENTITIES.register("saltemone", () -> BlockEntityType.Builder.of(SaltemoneBlockEntity::new, MSFBlocks.SALTEMONE.get(), MSFBlocks.SOURLEMONE.get()).build(null));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<TorchflowerBlockEntity>> TORCHFLOWER = BLOCK_ENTITIES.register("torchflower", () -> BlockEntityType.Builder.of(TorchflowerBlockEntity::new, MSFBlocks.TORCHFLOWER_AFLAME.get()).build(null));

    DeferredHolder<BlockEntityType<?>, BlockEntityType<ModCauldronBlockEntity>> MOD_CAULDRON = BLOCK_ENTITIES.register("mod_cauldron", () -> BlockEntityType.Builder.of(ModCauldronBlockEntity::new, MSFBlocks.ACID_FILLED_CAULDRON.get(), MSFBlocks.BONMEEL_FILLED_CAULDRON.get()).build(null));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<ModSignBlockEntity>> MOD_SIGN = BLOCK_ENTITIES.register("mod_sign", () -> BlockEntityType.Builder.of(ModSignBlockEntity::new, MSFBlocks.CORRUPTED_SIGN.get(), MSFBlocks.CORRUPTED_WALL_SIGN.get()).build(null));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<VivicusSignBlockEntity>> VIVICUS_SIGN = BLOCK_ENTITIES.register("vivicus_sign", () -> BlockEntityType.Builder.of(VivicusSignBlockEntity::new, MSFBlocks.VIVICUS_SIGN.get(), MSFBlocks.VIVICUS_WALL_SIGN.get()).build(null));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<ModHangingSignBlockEntity>> MOD_HANGING_SIGN = BLOCK_ENTITIES.register("mod_hanging_sign", () -> BlockEntityType.Builder.of(ModHangingSignBlockEntity::new, MSFBlocks.CORRUPTED_HANGING_SIGN.get(), MSFBlocks.CORRUPTED_WALL_HANGING_SIGN.get()).build(null));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<VivicusHangingSignBlockEntity>> VIVICUS_HANGING_SIGN = BLOCK_ENTITIES.register("vivicus_hanging_sign", () -> BlockEntityType.Builder.of(VivicusHangingSignBlockEntity::new, MSFBlocks.VIVICUS_HANGING_SIGN.get(), MSFBlocks.VIVICUS_WALL_HANGING_SIGN.get()).build(null));
    DeferredHolder<BlockEntityType<?>, BlockEntityType<BerootCauldronBlockEntity>> BEROOT_CAULDRON = BLOCK_ENTITIES.register("beroot_cauldron", () -> BlockEntityType.Builder.of(BerootCauldronBlockEntity::new, MSFBlocks.BEROOT_CAULDRON.get()).build(null));

    @SafeVarargs
    static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<T> supplier, Supplier<Block>... blocks) {
        return BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of(supplier, Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new)).build(null));
    }
}

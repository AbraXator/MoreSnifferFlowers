package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.AmbushBlock;
import net.abraxator.moresnifferflowers.blocks.blockentities.AmbushBlockEntity;
import net.abraxator.moresnifferflowers.blocks.blockentities.OgingoBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MoreSnifferFlowers.MOD_ID);

    public static final RegistryObject<BlockEntityType<AmbushBlockEntity>> AMBUSH = BLOCK_ENTITIES.register("ambush", () -> BlockEntityType.Builder.of(AmbushBlockEntity::new, ModBlocks.AMBUSH.get()).build(null));
    //public static final RegistryObject<BlockEntityType<OgingoBlockEntity>> OGINGO = BLOCK_ENTITIES.register("ogingo", () -> BlockEntityType.Builder.of(OgingoBlockEntity::new, ModBlocks.OGINGO_VINE.get(), ModBlocks.OGINGO_VINE_PLANT.get()).build(null));
}

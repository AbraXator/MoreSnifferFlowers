package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MORESNIFFERFLOWERS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(MoreSnifferFlowers.MOD_ID, "moresnifferflowers"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.moresnifferflowers"))
                    .icon(() -> new ItemStack(ModItems.DYESPRIA)).entries((displayContext, entries) -> {
                        entries.add(ModItems.DAWNBERRY_VINE_SEEDS);
                        entries.add(ModItems.DAWNBERRY);
                        entries.add(ModItems.AMBUSH_SEEDS);
                        entries.add(ModBlocks.AMBER);
                        entries.add(ModItems.AMBER_SHARD);
                        entries.add(ModItems.AROMA_ARMOR_TRIM_SMITHING_TEMPLATE);
//                        entries.add(ModItems.AMBUSH_BANNER_PATTERN);
                        entries.add(ModItems.DRAGONFLY);
//                        entries.add(ModItems.DYESPRIA_SEEDS);
                        entries.add(ModItems.DYESPRIA);
                        entries.add(ModBlocks.CAULORFLOWER);
                        entries.add(ModItems.BONMEELIA_SEEDS);
                        entries.add(ModItems.JAR_OF_BONMEEL);
                        entries.add(ModItems.BELT_PIECE);
                        entries.add(ModItems.ENGINE_PIECE);
                        entries.add(ModItems.TUBE_PIECE);
                        entries.add(ModItems.SCRAP_PIECE);
                        entries.add(ModItems.PRESS_PIECE);
//                        entries.add(ModItems.CROPRESSOR);
                        entries.add(ModItems.CROPRESSED_CARROT);
                        entries.add(ModItems.CAROTENE_ARMOR_TRIM_SMITHING_TEMPLATE);
                        entries.add(ModItems.CROPRESSED_POTATO);
                        entries.add(ModItems.TATER_ARMOR_TRIM_SMITHING_TEMPLATE);
                        entries.add(ModItems.CROPRESSED_WHEAT);
                        entries.add(ModItems.GRAIN_ARMOR_TRIM_SMITHING_TEMPLATE);
                        entries.add(ModItems.CROPRESSED_BEETROOT);
                        entries.add(ModItems.BEAT_ARMOR_TRIM_SMITHING_TEMPLATE);
                        entries.add(ModItems.CROPRESSED_NETHERWART);
                        entries.add(ModItems.NETHER_WART_ARMOR_TRIM_SMITHING_TEMPLATE);
                        entries.add(ModItems.EXTRACTION_BOTTLE);
//                        entries.add(ModItems.REBREWING_STAND);
                        entries.add(ModItems.BROKEN_REBREWING_STAND);
                    }).build());
    public static void registerItemGroups(){
        MoreSnifferFlowers.LOGGER.info("Registering ItemGroups for" + MoreSnifferFlowers.MOD_ID);
    }
}
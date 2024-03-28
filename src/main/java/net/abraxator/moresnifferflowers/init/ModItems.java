package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.items.DyespriaItem;
import net.abraxator.moresnifferflowers.items.JarOfBonmeelItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, MoreSnifferFlowers.MOD_ID);

    public static final RegistryObject<Item> DAWNBERRY_VINE_SEEDS = ITEMS.register("dawnberry_vine_seeds", () -> new ItemNameBlockItem(ModBlocks.DAWNBERRY_VINE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DAWNBERRY = ITEMS.register("dawnberry", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.6F).fast().build())));
    public static final RegistryObject<Item> AMBUSH_SEEDS = ITEMS.register("ambush_seeds", () -> new ItemNameBlockItem(ModBlocks.AMBUSH.get(), new Item.Properties()));
    public static final RegistryObject<Item> AMBUSH_BANNER_PATTERN = ITEMS.register("ambush_banner_pattern", () -> new BannerPatternItem(ModTags.ModBannerPatternTags.AMBUSH_BANNER_PATTERN, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> AMBER_SHARD = ITEMS.register("amber_shard", () -> new Item(new Item.Properties()) {
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
            pTooltipComponents.add(Component.translatable("tooltip.amber_shard.usage"));
        }
    });
    public static final RegistryObject<Item> AROMA_ARMOR_TRIM_SMITHING_TABLE = ITEMS.register("aroma_armor_trim_smithing_table", () -> SmithingTemplateItem.createArmorTrimTemplate(ModTrimPatterns.AROMA));
    public static final RegistryObject<Item> DRAGONFLY = ITEMS.register("dragonfly", () -> new Item(new Item.Properties().food(ModFoods.DRAGONFLY)));
    public static final RegistryObject<Item> DYESPRIA = ITEMS.register("dyespria", () -> new DyespriaItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BONMEELIA_SEEDS = ITEMS.register("bonmeelia_seeds", () -> new ItemNameBlockItem(ModBlocks.BONMEELIA.get(), new Item.Properties()));
    public static final RegistryObject<Item> JAR_OF_BONMEEL = ITEMS.register("jar_of_bonmeel", () -> new JarOfBonmeelItem(new Item.Properties()));
    public static final RegistryObject<Item> CROPRESSED_POTATO = ITEMS.register("cropressed_potato", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CROPRESSED_CARROT = ITEMS.register("cropressed_carrot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CROPRESSED_BEETROOT = ITEMS.register("cropressed_beetroot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CROPRESSED_NETHERWART = ITEMS.register("cropressed_nether_wart", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CROPRESSED_WHEAT = ITEMS.register("cropressed_wheat", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CROPRESSOR = ITEMS.register("cropressor", () -> new ItemNameBlockItem(ModBlocks.CROPRESSOR_OUT.get(), new Item.Properties()));

    public static final RegistryObject<Item> CROPRESSOR_TUBE = ITEMS.register("cropressor_tube", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CROPRESSOR_BELT = ITEMS.register("cropressor_belt", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CROPRESSOR_SCRAP = ITEMS.register("cropressor_scrap", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CROPRESSOR_ENGINE = ITEMS.register("cropressor_engine", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EXTRACTION_BOTTLE = ITEMS.register("extraction_bottle", () -> new Item(new Item.Properties()));

}

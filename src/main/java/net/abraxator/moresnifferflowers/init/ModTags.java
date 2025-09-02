package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;

public class ModTags {
    public static class ModItemTags {
        public static final TagKey<Item> AROMA_TRIM_TEMPLATE_INGREDIENT = create(Registries.ITEM ,"aroma_trim_template_ingredient");
        public static final TagKey<Item> CROP_SMITHING_TEMPLATES = create(Registries.ITEM ,"crop_smithing_templates");
        public static final TagKey<Item> CROPRESSABLE = create(Registries.ITEM ,"cropressable");
        public static final TagKey<Item> CROPRESSOR_PIECES = create(Registries.ITEM ,"cropressor_pieces");
        public static final TagKey<Item> CROPRESSED_CROPS = create(Registries.ITEM ,"cropressed_crops");
        public static final TagKey<Item> REBREWED_POTIONS = create(Registries.ITEM ,"rebrewed_potions");
        public static final TagKey<Item> VIVICUS_LOGS = create(Registries.ITEM ,"vivicus_logs");
        public static final TagKey<Item> CORRUPTED_LOGS = create(Registries.ITEM ,"corrupted_logs");
        public static final TagKey<Item> BLOCK_PATTERNS = create(Registries.ITEM ,"block_patterns");
        public static final TagKey<Item> COLORABLE = create(Registries.ITEM ,"colorable");

    }

    public static class ModBlockTags {
        public static final TagKey<Block> BONMEELABLE = create(Registries.BLOCK, "bonmeelable");
        public static final TagKey<Block> GIANT_CROP_REPLACEABLE = create(Registries.BLOCK, "giant_crop_replaceable");
        public static final TagKey<Block> GIANT_CROPS = create(Registries.BLOCK, "giant_crops");
        public static final TagKey<Block> VIVICUS_BLOCKS = create(Registries.BLOCK, "vivicus_blocks");
        public static final TagKey<Block> CORRUPTED_BLOCKS = create(Registries.BLOCK, "corrupted_blocks");
        public static final TagKey<Block> CORRUPTED_SLUDGE = create(Registries.BLOCK, "corrupted_sludge");
        public static final TagKey<Block> VIVICUS_TREE_REPLACABLE = create(Registries.BLOCK, "vivicus_tree_replacable");
        public static final TagKey<Block> CORRUPTION_TRANSFORMABLES = create(Registries.BLOCK, "corruption_transformables");
        public static final TagKey<Block> DYED = create(Registries.BLOCK, "dyed");
        public static final TagKey<Block> NO_CORRUPTED_SLIME_COLLISION = create(Registries.BLOCK, "no_corrupted_slime_collision");
        public static final TagKey<Block> UNCORRUPTABLE = create(Registries.BLOCK, "uncorruptable");
        public static final TagKey<Block> STICKABLE = create(Registries.BLOCK, "stickable");
        public static final TagKey<Block> CORRUPTION_SHIELDING = create(Registries.BLOCK, "corruption_shielding");
        public static final TagKey<Block> NO_SHADING = create(Registries.BLOCK, "no_shading");

    }

    public static class ModBannerPatternTags {
        public static final TagKey<BannerPattern> AMBUSH_BANNER_PATTERN = create(Registries.BANNER_PATTERN, "pattern_item/ambush");
        public static final TagKey<BannerPattern> EVIL_BANNER_PATTERN = create(Registries.BANNER_PATTERN, "pattern_item/evil");

    }
    
    public static class ModBiomeTags {
        public static final TagKey<Biome> HAS_SWAMP_SNIFFER_TEMPLE = create(Registries.BIOME, "has_swamp_sniffer_temple");
    }

    private static <T extends Object> TagKey<T> create(ResourceKey<Registry<T>> registry, String name){
        return TagKey.create(registry, MoreSnifferFlowers.loc(name));
    }
}
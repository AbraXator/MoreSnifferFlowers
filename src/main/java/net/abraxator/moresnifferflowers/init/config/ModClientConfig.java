package net.abraxator.moresnifferflowers.init.config;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ModClientConfig {
    public static final ModConfigSpec CLIENT_CONFIG;
    public static final ModConfigSpec.IntValue HARDENED_MOUTH_X;
    public static final ModConfigSpec.IntValue HARDENED_MOUTH_Y;
    public static final ModConfigSpec.IntValue BLOCK_PATTERN_RENDER_DISTANCE;
    public static final ModConfigSpec.BooleanValue BLOCK_PATTERN_SMOOTH_LIGHTING;
    public static final ModConfigSpec.BooleanValue BLOCK_PATTERN_TRANSPARENCY;
    public static final ModConfigSpec.BooleanValue DISABLE_MULTIBLOCK_PREVIEWS;




    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

       // builder.comment("A lot of these require a game restart seemingly at random. Restart your game before reporting it as a bug!");
       // builder.translation("moresnifferflowers.configuration.restart_required");

        builder.push("hardened_mouth_effect");

        HARDENED_MOUTH_X = builder
                .comment("Move extra slots from the Hardened mouth effect left to right")
                .translation("moresnifferflowers.configuration.hardened_mouth_x")
                .defineInRange("Hardened Mouth X", -25, -5000, 5000);
        HARDENED_MOUTH_Y = builder
                .comment("Move extra slots from the Hardened mouth effect up and down")
                .translation("moresnifferflowers.configuration.hardened_mouth_y")
                .defineInRange("Hardened Mouth Y", -80, -5000, 5000);

        builder.pop();

        builder.push("block_patterns");
        BLOCK_PATTERN_RENDER_DISTANCE = builder
                .comment("Input in chunks. Negative values use a division of your current render distance instead")
                .translation("moresnifferflowers.configuration.block_pattern_render_distance")
                .defineInRange("Block Pattern Render Distance", -2, -5, 32);

        BLOCK_PATTERN_SMOOTH_LIGHTING = builder
                .comment("Enables smooth lighting for block patterns")
                .translation("moresnifferflowers.configuration.block_pattern_smooth_lighting")
                .define("Block Pattern Smooth Lighting", true);

        BLOCK_PATTERN_TRANSPARENCY = builder
                .comment("Enables transparency for block patters (only noticeable with resource packs)")
                .translation("moresnifferflowers.configuration.block_pattern_transparency")
                .define("Block Pattern Transparency", false);

        builder.pop();

        builder.push("multiblocks");

        DISABLE_MULTIBLOCK_PREVIEWS = builder
                .comment("Disables ghost previews when trying to place a multiblock")
                .translation("moresnifferflowers.configuration.disable_multiblock_previews")
                .define("Disable Multiblock Previews", false);

        builder.pop();
        CLIENT_CONFIG = builder.build();

    }

    public static int getBlockPatternRenderDistance() {
        if (!ModClientConfig.CLIENT_CONFIG.isLoaded()) return 0;
        Minecraft minecraft = Minecraft.getInstance();
        int renderDistancePlayer = minecraft.options.getEffectiveRenderDistance();
        int configuredRenderDistance = ModClientConfig.BLOCK_PATTERN_RENDER_DISTANCE.get();
        return configuredRenderDistance < 0 ? renderDistancePlayer / Math.abs(configuredRenderDistance) : configuredRenderDistance;
    }

    private static boolean validateHardenedMouthY(Object obj) {
        return obj instanceof Integer integer && (integer <= -5 || integer >= 32);
    }

}

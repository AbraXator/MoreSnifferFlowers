package net.abraxator.moresnifferflowers.mixins;


import com.google.common.collect.ImmutableList;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SpriteResourceLoader.class)
public abstract class SpriteResourceLoaderMixin {

    @Inject(method = "load",
            at = @At("RETURN"))
    private static void moresnifferflowers$load(ResourceManager resourceManager, ResourceLocation location, CallbackInfoReturnable<SpriteResourceLoader> cir) {
        if (location.getPath().equals("armor_trims")) {
            SpriteResourceLoader ret = cir.getReturnValue();
            for (SpriteSource source : ((SpriteResourceLoaderMixin) (Object) ret).getSources()) {
                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
                    ResourceLocation trimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/aroma");
                    ResourceLocation leggingsTrimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/aroma").withSuffix("_leggings");
                    permutations.setTextures(ImmutableList.<ResourceLocation>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
                }
                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
                    ResourceLocation trimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/tater");
                    ResourceLocation leggingsTrimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/tater").withSuffix("_leggings");
                    permutations.setTextures(ImmutableList.<ResourceLocation>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
                }
                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
                    ResourceLocation trimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/nether_wart");
                    ResourceLocation leggingsTrimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/nether_wart").withSuffix("_leggings");
                    permutations.setTextures(ImmutableList.<ResourceLocation>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
                }
                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
                    ResourceLocation trimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/carotene");
                    ResourceLocation leggingsTrimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/carotene").withSuffix("_leggings");
                    permutations.setTextures(ImmutableList.<ResourceLocation>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
                }
                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
                    ResourceLocation trimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/grain");
                    ResourceLocation leggingsTrimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/grain").withSuffix("_leggings");
                    permutations.setTextures(ImmutableList.<ResourceLocation>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
                }
                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
                    ResourceLocation trimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/beat");
                    ResourceLocation leggingsTrimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/beat").withSuffix("_leggings");
                    permutations.setTextures(ImmutableList.<ResourceLocation>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
                }
                    //Já věřim že tohle jde udělat líp
            }
        }
    }

    @Accessor("sources")
    abstract List<SpriteSource> getSources();

    @Mixin(PalettedPermutations.class)
    private interface PalettedPermutationsAccessor {

        @Accessor
        List<ResourceLocation> getTextures();

        @Accessor("textures")
        @Mutable
        void setTextures(List<ResourceLocation> value);

        @Accessor
        ResourceLocation getPaletteKey();
    }
}

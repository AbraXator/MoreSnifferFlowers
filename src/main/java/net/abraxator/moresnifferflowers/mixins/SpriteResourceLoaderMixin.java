package net.abraxator.moresnifferflowers.mixins;

import com.google.common.collect.ImmutableList;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceList;
import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SpriteSourceList.class)
public abstract class SpriteResourceLoaderMixin {

    @Shadow @Final private List<SpriteSource> sources;

    @Inject(method = "load*",
            at = @At("RETURN"))
    private static void moresnifferflowers$load(ResourceManager resourceManager, ResourceLocation location, CallbackInfoReturnable<SpriteSourceList> cir) {
        if (location.getPath().equals("armor_trims")) {
            SpriteSourceList ret = cir.getReturnValue();
            for (SpriteSource source : ((SpriteResourceLoaderMixin) ((Object) ret)).getSources()) {
                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
                    ResourceLocation trimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/aroma");
                    ResourceLocation leggingsTrimLocation = new ResourceLocation(MoreSnifferFlowers.MOD_ID, "trims/models/armor/aroma").withSuffix("_leggings");
                    permutations.setTextures(ImmutableList.<ResourceLocation>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
                }
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

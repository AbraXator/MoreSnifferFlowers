//package net.abraxator.moresnifferflowers.mixins;
//
//
//import com.google.common.collect.ImmutableList;
//import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
//import net.minecraft.client.texture.SpriteOpener;
//import net.minecraft.client.texture.atlas.AtlasSource;
//import net.minecraft.client.texture.atlas.PalettedPermutationsAtlasSource;
//import net.minecraft.resource.metadata.ResourceMetadataReader;
//import net.minecraft.util.Identifier;
//import net.minecraft.resource.ResourceManager;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Mutable;
//import org.spongepowered.asm.mixin.gen.Accessor;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import java.util.Collection;
//import java.util.List;
//
//@Mixin(SpriteOpener.class)
//public abstract class SpriteResourceLoaderMixin {
//
//    @Inject(method = "create",
//            at = @At("RETURN"))
//    private static void moresnifferflowers$create(Collection<ResourceMetadataReader<?>> metadatas, CallbackInfoReturnable<SpriteOpener> cir) {
//        if (location.getPath().equals("armor_trims")) {
//            SpriteOpener ret = cir.getReturnValue();
//            for (AtlasSource source : ((SpriteResourceLoaderMixin) (Object) ret).getSources()) {
//                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
//                    Identifier trimLocation = new Identifier(MoreSnifferFlowers.MOD_ID, "trims/models/armor/aroma");
//                    Identifier leggingsTrimLocation = new Identifier(MoreSnifferFlowers.MOD_ID, "trims/models/armor/aroma").withSuffixedPath("_leggings");
//                    permutations.setTextures(ImmutableList.<Identifier>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
//                }
//                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
//                    Identifier trimLocation = new Identifier(MoreSnifferFlowers.MOD_ID, "trims/models/armor/tater");
//                    Identifier leggingsTrimLocation = new Identifier(MoreSnifferFlowers.MOD_ID, "trims/models/armor/tater").withSuffixedPath("_leggings");
//                    permutations.setTextures(ImmutableList.<Identifier>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
//                }
//                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
//                    Identifier trimLocation = new Identifier(MoreSnifferFlowers.MOD_ID, "trims/models/armor/nether_wart");
//                    Identifier leggingsTrimLocation = new Identifier(MoreSnifferFlowers.MOD_ID, "trims/models/armor/nether_wart").withSuffixedPath("_leggings");
//                    permutations.setTextures(ImmutableList.<Identifier>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
//                }
//                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
//                    Identifier trimLocation = new Identifier(MoreSnifferFlowers.MOD_ID, "trims/models/armor/carotene");
//                    Identifier leggingsTrimLocation = new Identifier(MoreSnifferFlowers.MOD_ID, "trims/models/armor/carotene").withSuffixedPath("_leggings");
//                    permutations.setTextures(ImmutableList.<Identifier>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
//                }
//                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
//                    Identifier trimLocation = new Identifier(MoreSnifferFlowers.MOD_ID, "trims/models/armor/grain");
//                    Identifier leggingsTrimLocation = new Identifier(MoreSnifferFlowers.MOD_ID, "trims/models/armor/grain").withSuffixedPath("_leggings");
//                    permutations.setTextures(ImmutableList.<Identifier>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
//                }
//                if (source instanceof PalettedPermutationsAccessor permutations && permutations.getPaletteKey().getPath().equals("trims/color_palettes/trim_palette")) {
//                    Identifier trimLocation = new Identifier(MoreSnifferFlowers.MOD_ID, "trims/models/armor/beat");
//                    Identifier leggingsTrimLocation = new Identifier(MoreSnifferFlowers.MOD_ID, "trims/models/armor/beat").withSuffixedPath("_leggings");
//                    permutations.setTextures(ImmutableList.<Identifier>builder().addAll(permutations.getTextures()).add(trimLocation, leggingsTrimLocation).build());
//                }
//                    //Já věřim že tohle jde udělat líp
//            }
//        }
//    }
//
//    @Accessor("sources")
//    abstract List<AtlasSource> getSources();
//
//    @Mixin(PalettedPermutationsAtlasSource.class)
//    private interface PalettedPermutationsAccessor {
//
//        @Accessor
//        List<Identifier> getTextures();
//
//        @Accessor("textures")
//        @Mutable
//        void setTextures(List<Identifier> value);
//
//        @Accessor
//        Identifier getPaletteKey();
//    }
//}

package net.abraxator.moresnifferflowers.datagen.tag;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModEffectTagsProvider extends TagsProvider<MobEffect> {
    public ModEffectTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.MOB_EFFECT, pLookupProvider, MoreSnifferFlowers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
       // this.tag(ModTags.ModEffectTags.EXTRACTION_BLACKLIST).add(getLoc(MobEffects.MOVEMENT_SPEED));
    }

    public static ResourceKey<MobEffect> getLoc(Holder<MobEffect> effect) {
      return BuiltInRegistries.MOB_EFFECT.getResourceKey(effect.value()).get();
    }
}

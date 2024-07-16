package net.abraxator.moresnifferflowers.compat.jei.rebrewing;

import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record JeiRebrewingRecipe(ItemStack extractedPotion, ItemStack rebrewedPotion, ItemStack ingredient) {
    public static List<JeiRebrewingRecipe> createRecipes() {
        List<JeiRebrewingRecipe> ret = new ArrayList<>();
        List<ItemStack> ingredients = List.of(
                Items.REDSTONE.getDefaultInstance(),
                Items.GLOWSTONE_DUST.getDefaultInstance(),
                Items.GUNPOWDER.getDefaultInstance(),
                Items.DRAGON_BREATH.getDefaultInstance()
        );

        for (ItemStack item : ingredients) {
            BuiltInRegistries.MOB_EFFECT.stream().forEach(effect -> {
                int duration = item.is(Items.REDSTONE) ? 12000 : 6000;
                int amplifier = item.is(Items.GLOWSTONE_DUST) ? 2 : 1;

                List<MobEffectInstance> extractedEffect = List.of(new MobEffectInstance(Holder.direct(effect), 1200, 0));
                List<MobEffectInstance> rebrewedEffect = List.of(new MobEffectInstance(Holder.direct(effect), 1200 + duration, amplifier));
                PotionContents extractedPotionContents = new PotionContents(Optional.of(Potions.WATER), Optional.of(PotionContents.getColor(extractedEffect)), extractedEffect);
                PotionContents rebrewedPotionContents = new PotionContents(Optional.of(Potions.WATER), Optional.of(PotionContents.getColor(rebrewedEffect)), rebrewedEffect);
                
                ItemStack extractedPotion = ModItems.EXTRACTED_BOTTLE.get().getDefaultInstance();
                var rebrewedPotion = item.is(Items.GUNPOWDER) ? ModItems.REBREWED_SPLASH_POTION.get().getDefaultInstance() :
                        item.is(Items.DRAGON_BREATH) ? ModItems.REBREWED_LINGERING_POTION.get().getDefaultInstance() :
                                ModItems.REBREWED_POTION.get().getDefaultInstance();
                
                extractedPotion.set(DataComponents.POTION_CONTENTS, extractedPotionContents);
                rebrewedPotion.set(DataComponents.POTION_CONTENTS, rebrewedPotionContents);

                ret.add(new JeiRebrewingRecipe(extractedPotion, rebrewedPotion, item));
            });
        }
        return ret;
    }
}

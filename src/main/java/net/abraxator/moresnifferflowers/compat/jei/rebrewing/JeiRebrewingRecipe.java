package net.abraxator.moresnifferflowers.compat.jei.rebrewing;

import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.commands.execution.tasks.BuildContexts;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.util.ArrayList;
import java.util.List;

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
                int duration = item.is(Items.GLOWSTONE_DUST) ? 12000 : 6000;
                int amplifier = item.is(Items.REDSTONE) ? 2 : 1;

                MobEffectInstance extractedEffect = new MobEffectInstance(effect, 1200, 0);
                MobEffectInstance rebrewedEffect = new MobEffectInstance(effect, 1200 + duration, amplifier);

                ItemStack extractedPotion = ModItems.EXTRACTED_BOTTLE.get().getDefaultInstance();
                ItemStack rebrewedPotion = item.is(Items.GUNPOWDER) ? ModItems.REBREWED_SPLASH_POTION.toStack() : 
                        item.is(Items.DRAGON_BREATH) ? ModItems.REBREWED_LINGERING_POTION.toStack() : 
                                ModItems.REBREWED_POTION.toStack();

                PotionUtils.setCustomEffects(extractedPotion, List.of(extractedEffect));
                PotionUtils.setCustomEffects(rebrewedPotion, List.of(rebrewedEffect));

                ret.add(new JeiRebrewingRecipe(extractedPotion, rebrewedPotion, item));
            });
        }
        return ret;
    }
}

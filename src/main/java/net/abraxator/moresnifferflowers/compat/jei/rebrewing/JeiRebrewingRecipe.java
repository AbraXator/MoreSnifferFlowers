package net.abraxator.moresnifferflowers.compat.jei.rebrewing;

import net.abraxator.moresnifferflowers.blockentities.RebrewingStandBlockEntity;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

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
                int duration = item.is(Items.REDSTONE) ? 12000 : 6000;
                int amplifier = item.is(Items.GLOWSTONE_DUST) ? 2 : 1;

                MobEffectInstance extractedEffect = new MobEffectInstance(Holder.direct(effect), 1200, 0);
                MobEffectInstance rebrewedEffect = new MobEffectInstance(Holder.direct(effect), 1200 + duration, amplifier);

                ItemStack extractedPotion = ModItems.EXTRACTED_BOTTLE.get().getDefaultInstance();
                var rebrewedPotion = item.is(Items.GUNPOWDER) ? ModItems.REBREWED_SPLASH_POTION.get().getDefaultInstance() :
                        item.is(Items.DRAGON_BREATH) ? ModItems.REBREWED_LINGERING_POTION.get().getDefaultInstance() :
                                ModItems.REBREWED_POTION.get().getDefaultInstance();

                RebrewingStandBlockEntity.addPotionListToStack(List.of(extractedEffect), extractedPotion);
                RebrewingStandBlockEntity.addPotionListToStack(List.of(rebrewedEffect), rebrewedPotion);

                ret.add(new JeiRebrewingRecipe(extractedPotion, rebrewedPotion, item));
            });
        }
        return ret;
    }
}

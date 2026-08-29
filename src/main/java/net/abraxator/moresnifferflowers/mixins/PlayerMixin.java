package net.abraxator.moresnifferflowers.mixins;

import net.abraxator.moresnifferflowers.init.MSFEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow protected FoodData foodData;

    @Inject(method = "eat", at = @At(value = "HEAD"))
    public void blandEffectInject(Level level, ItemStack food, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir){
        Player player = (Player)(Object)this;

        if (player.hasEffect(MSFEffects.BLAND)) {
            int amplifier = Objects.requireNonNull(player.getEffect(MSFEffects.BLAND)).getAmplifier() + 1;
            float division =  1f + amplifier / 2f;
            float hungerChance = 0.2f + amplifier / 10f;

            if (!level.isClientSide &&  level.random.nextFloat() < hungerChance){
                player.addEffect( new MobEffectInstance(MobEffects.HUNGER, Math.round(10 * division) * 20, amplifier - 1));
            }

             //Original logic from FoodData cuz fabric SUCKS
            FoodProperties foodproperties = food.getFoodProperties(player);
            int foodValue = Math.round(foodproperties.nutrition() / division);
            float satValue = foodproperties.saturation() / division;
            foodData.eat( - (foodproperties.nutrition() - foodValue), -(foodproperties.saturation() - satValue));

        }
    }
}

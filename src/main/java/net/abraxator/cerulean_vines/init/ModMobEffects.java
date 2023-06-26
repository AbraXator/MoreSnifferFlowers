package net.abraxator.cerulean_vines.init;

import net.abraxator.cerulean_vines.CeruleanVines;
import net.abraxator.cerulean_vines.effects.CeruleanlyVinedEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(
            ForgeRegistries.MOB_EFFECTS, CeruleanVines.MOD_ID);

    //public static final RegistryObject<MobEffect> CERULEANLY_VINED = EFFECTS.register("ceruleanly_vined", () -> new CeruleanlyVinedEffect(MobEffectCategory.NEUTRAL, 10017472));
}

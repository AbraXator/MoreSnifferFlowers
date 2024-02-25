package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> VEGGIES = create("veggies");

    public static RegistryKey<DamageType> create(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, MoreSnifferFlowers.loc(name));
    }

    public static DamageSource getDamageSource(World level, RegistryKey<DamageType> type) {
        return new DamageSource(level.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(type));
    }

    public static void bootstrap(Registerable<DamageType> context) {
        context.register(VEGGIES, new DamageType(MoreSnifferFlowers.MOD_ID + "veggies", 0.0f));
    }
}

package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashSet;
import java.util.Set;

public class ModBuiltintLoottables {
    public static final Set<ResourceKey<LootTable>> RESOURCES = new HashSet<>();
    
    private ResourceKey<LootTable> register(String name) {
        var ret = ResourceKey.create(Registries.LOOT_TABLE, MoreSnifferFlowers.loc(name));    
        RESOURCES.add(ret);
        return ret;
    }
    
    public static Set<ResourceKey<LootTable>> all() {
        return RESOURCES;
    }
}

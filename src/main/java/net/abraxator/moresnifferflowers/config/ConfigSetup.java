package net.abraxator.moresnifferflowers.config;

import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigSetup {
    private static final ForgeConfigSpec CLIENT_SPEC;
    static final ModClientConfig CLIENT_CONFIG;
    
    static {
        final Pair<ModClientConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(ModClientConfig::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC = pair.getRight());
        CLIENT_CONFIG = pair.getLeft();
    }
}

package net.abraxator.moresnifferflowers.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModClientConfig {
    final ForgeConfigSpec.BooleanValue customStyleGUI;
    
    ModClientConfig(ForgeConfigSpec.Builder builder) {
        customStyleGUI = builder
                .comment("Enable custom style GUIs")
                .define("custom_style_gui", true);
    }
}

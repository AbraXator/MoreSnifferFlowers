package net.abraxator.moresnifferflowers.client.model;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayerLocations {
    //ENTITY
    public static final EntityModelLayer BOBLING = new EntityModelLayer(new Identifier(MoreSnifferFlowers.MOD_ID, "boblingmodel"), "main");

    //BLOCK
    public static final EntityModelLayer GIANT_CARROT = new EntityModelLayer(new Identifier(MoreSnifferFlowers.MOD_ID, "giant_carrot"), "main");
    public static final EntityModelLayer GIANT_POTATO = new EntityModelLayer(new Identifier(MoreSnifferFlowers.MOD_ID, "giant_potato"), "main");
    public static final EntityModelLayer GIANT_NETHERWART = new EntityModelLayer(new Identifier(MoreSnifferFlowers.MOD_ID, "giant_netherwart"), "main");
    public static final EntityModelLayer GIANT_BEETROOT = new EntityModelLayer(new Identifier(MoreSnifferFlowers.MOD_ID, "giant_beetroot"), "main");
    public static final EntityModelLayer GIANT_WHEAT = new EntityModelLayer(new Identifier(MoreSnifferFlowers.MOD_ID, "giant_wheat"), "main");
    public static final EntityModelLayer CROPRESSOR = new EntityModelLayer(new Identifier(MoreSnifferFlowers.MOD_ID, "cropressor"), "main");
}

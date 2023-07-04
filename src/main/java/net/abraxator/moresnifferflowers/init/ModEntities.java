package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MoreSnifferFlowers.MOD_ID);

    //public static final RegistryObject<EntityType<CeruleanVinePatch>> CERULEAN_VINE_PATCH = buildNoEgg("cerulean_vine_patch", makeCastedBuilder(CeruleanVinePatch.class, CeruleanVinePatch::new, 0.25F, 0.25F, 4, 10), false);

    private static <E extends Entity> RegistryObject<EntityType<E>> buildNoEgg(String name, EntityType.Builder<E> builder, boolean fireproof) {
        if (fireproof) builder.fireImmune();
        ResourceLocation id = new ResourceLocation(MoreSnifferFlowers.MOD_ID, name);
        return ENTITIES.register(id.getPath(), () -> builder.build(id.toString()));
    }

    private static <E extends Entity> EntityType.Builder<E> makeCastedBuilder(@SuppressWarnings("unused") Class<E> cast, EntityType.EntityFactory<E> factory, float width, float height, int range, int interval) {
        return makeBuilder(factory, MobCategory.MISC, width, height, range, interval);
    }

    private static <E extends Entity> EntityType.Builder<E> makeBuilder(EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, int range, int interval) {
        return EntityType.Builder.of(factory, classification).
                sized(width, height).
                setTrackingRange(range).
                setUpdateInterval(interval).
                setShouldReceiveVelocityUpdates(true);
    }
}

package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.entities.Bobling;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, MoreSnifferFlowers.MOD_ID);

    //public static final RegistryObject<EntityType<CeruleanVinePatch>> CERULEAN_VINE_PATCH = buildNoEgg("cerulean_vine_patch", makeCastedBuilder(CeruleanVinePatch.class, CeruleanVinePatch::new, 0.25F, 0.25F, 4, 10), false);
    //public static final RegistryObject<EntityType<Bobling>> BOBLING = make(MoreSnifferFlowers.loc("bobling"), Bobling::new, MobCategory.CREATURE, 0.3F, 0.7F, 0x835E38, 0x85B931);
    public static final DeferredHolder<EntityType<?>, EntityType<Bobling>> BOBLING = buildNoEgg(MoreSnifferFlowers.loc("bobling"), makeBuilder(Bobling::new, SpawnGroup.CREATURE, 0.3F, 0.7F, 80, 3), false);

    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(Identifier id, EntityType.EntityFactory<E> factory, SpawnGroup classification, float width, float height, int primary, int secondary) {
        return make(id, factory, classification, width, height, false, primary, secondary);
    }

    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> make(Identifier id, EntityType.EntityFactory<E> factory, SpawnGroup classification, float width, float height, boolean fireproof, int primary, int secondary) {
        return build(id, makeBuilder(factory, classification, width, height, 80, 3), fireproof, primary, secondary);
    }

    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> buildNoEgg(Identifier id, EntityType.Builder<E> builder, boolean fireproof) {
        if (fireproof) builder.makeFireImmune();
        return ENTITIES.register(id.getPath(), () -> builder.build(id.toString()));
    }

    @SuppressWarnings("unchecked")
    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> build(Identifier id, EntityType.Builder<E> builder, boolean fireproof, int primary, int secondary) {
        if (fireproof) builder.makeFireImmune();
        DeferredHolder<EntityType<?>, EntityType<E>> ret = ENTITIES.register(id.getPath(), () -> builder.build(id.toString()));
        if (primary != 0 && secondary != 0) {
            ModItems.ITEMS.register(id.getPath() + "_spawn_egg", () -> new SpawnEggItem((EntityType<? extends Mob>) ret.get(), primary, secondary, new Item.Properties()));
        }

        return ret;
    }

    private static <E extends Entity> EntityType.Builder<E> makeCastedBuilder(@SuppressWarnings("unused") Class<E> cast, EntityType.EntityFactory<E> factory, float width, float height, int range, int interval) {
        return makeBuilder(factory, SpawnGroup.MISC, width, height, range, interval);
    }

    private static <E extends Entity> EntityType.Builder<E> makeBuilder(EntityType.EntityFactory<E> factory, SpawnGroup classification, float width, float height, int range, int interval) {
        return EntityType.Builder.create(factory, classification).
                setDimensions(width, height).
                setTrackingRange(range).
                setUpdateInterval(interval).
                setShouldReceiveVelocityUpdates(true);
    }
}

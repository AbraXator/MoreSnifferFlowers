package net.abraxator.moresnifferflowers.init;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.entities.*;
import net.abraxator.moresnifferflowers.entities.boat.ModBoatEntity;
import net.abraxator.moresnifferflowers.entities.boat.ModChestBoatEntity;
import net.abraxator.moresnifferflowers.entities.boat.VivicusBoatEntity;
import net.abraxator.moresnifferflowers.entities.boat.VivicusChestBoatEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface MSFEntityTypes {
    DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MoreSnifferFlowers.MOD_ID);

    DeferredHolder<EntityType<?>, EntityType<BoblingEntity>> BOBLING =
            register("bobling", makeBuilder(BoblingEntity::new, MobCategory.CREATURE, 0.375F, 0.8125F));

    DeferredHolder<EntityType<?>, EntityType<DragonflyProjectile>> DRAGONFLY = 
            register("dragonfly", makeBuilder(DragonflyProjectile::new, MobCategory.MISC, 0.21875F, 0.21875F));

    DeferredHolder<EntityType<?>, EntityType<CorruptedProjectile>> CORRUPTED_SLIME_BALL = 
            register("corrupted_slime_ball", makeBuilder(CorruptedProjectile::new, MobCategory.MISC, 0.25F, 0.25F));

    DeferredHolder<EntityType<?>, EntityType<ModBoatEntity>> MOD_CORRUPTED_BOAT =
            register("mod_corrupted_boat", makeBuilder(ModBoatEntity::new, MobCategory.MISC, 1.375f, 0.5625f));

    DeferredHolder<EntityType<?>, EntityType<ModChestBoatEntity>> MOD_CORRUPTED_CHEST_BOAT =
            register("mod_corrupted_chest_boat", makeBuilder(ModChestBoatEntity::new, MobCategory.MISC, 1.375f, 0.5625f));

    DeferredHolder<EntityType<?>, EntityType<VivicusBoatEntity>> MOD_VIVICUS_BOAT =
            register("mod_vivicus_boat", makeBuilder(VivicusBoatEntity::new, MobCategory.MISC, 1.375f, 0.5625f));

    DeferredHolder<EntityType<?>, EntityType<VivicusChestBoatEntity>> MOD_VIVICUS_CHEST_BOAT =
            register("mod_vivicus_chest_boat", makeBuilder(VivicusChestBoatEntity::new, MobCategory.MISC, 1.375f, 0.5625f));

    DeferredHolder<EntityType<?>, EntityType<JarOfAcidProjectile>> JAR_OF_ACID =
            register("jar_of_acid", makeBuilder(JarOfAcidProjectile::new, MobCategory.MISC, 0.25F, 0.25F));

    DeferredHolder<EntityType<?>, EntityType<SaltBubbleProjectile>> SALT_BUBBLE =
            register("salt_bubble", makeBuilder(SaltBubbleProjectile::new, MobCategory.MISC, 0.7F, 0.7F));

    DeferredHolder<EntityType<?>, EntityType<SaltProjectile>> SALT_PROJECTILE =
            register("salt_projectile", makeBuilder(SaltProjectile::new, MobCategory.MISC, 0.25F, 0.25F));

    DeferredHolder<EntityType<?>, EntityType<GluingGumEntity>> GLUING_GUM_ENTITY =
            register("gluing_gum_entity", makeBuilder(GluingGumEntity::new, MobCategory.MISC, 0.25F, 0.25F));
    
    private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> register(String id, EntityType.Builder<E> builder) {
        return ENTITIES.register(id, () -> builder.build(MoreSnifferFlowers.loc(id).toString()));
    }

    private static <E extends Mob> DeferredHolder<EntityType<?>, EntityType<E>> registerWithEgg(String id, EntityType.Builder<E> builder, int primary, int secondary) {
        DeferredHolder<EntityType<?>, EntityType<E>> ret = ENTITIES.register(id, () -> builder.build(MoreSnifferFlowers.loc(id).toString()));
        MSFItems.ITEMS.registerItem(id + "_spawn_egg", (p) -> new DeferredSpawnEggItem(ret, primary, secondary, p));
        return ret;
    }
    
    private static <E extends Entity> EntityType.Builder<E> makeBuilder(EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height) {
        return EntityType.Builder.of(factory, classification).
                sized(width, height);
    }
}

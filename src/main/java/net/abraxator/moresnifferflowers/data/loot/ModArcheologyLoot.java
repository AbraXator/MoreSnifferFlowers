package net.abraxator.moresnifferflowers.data.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record ModArcheologyLoot(HolderLookup.Provider registries) implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pOutput) {
        pOutput.accept(
                BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(Items.ARMS_UP_POTTERY_SHERD).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.BREWER_POTTERY_SHERD).setWeight(2))
                                        .add(LootItem.lootTableItem(Items.BRICK))
                                        .add(LootItem.lootTableItem(Items.EMERALD))
                                        .add(LootItem.lootTableItem(Items.STICK))
                                        .add(
                                                LootItem.lootTableItem(Items.SUSPICIOUS_STEW)
                                                        .apply(
                                                                SetStewEffectFunction.stewEffect()
                                                                        .withEffect(MobEffects.NIGHT_VISION, UniformGenerator.between(7.0F, 10.0F))
                                                                        .withEffect(MobEffects.JUMP, UniformGenerator.between(7.0F, 10.0F))
                                                                        .withEffect(MobEffects.WEAKNESS, UniformGenerator.between(6.0F, 8.0F))
                                                                        .withEffect(MobEffects.BLINDNESS, UniformGenerator.between(5.0F, 7.0F))
                                                                        .withEffect(MobEffects.POISON, UniformGenerator.between(10.0F, 20.0F))
                                                                        .withEffect(MobEffects.SATURATION, UniformGenerator.between(7.0F, 10.0F))
                                                        )
                                        )
                        )
        );
    }
}

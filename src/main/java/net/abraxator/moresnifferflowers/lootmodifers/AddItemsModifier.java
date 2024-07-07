package net.abraxator.moresnifferflowers.lootmodifers;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.server.command.ModIdArgument;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class AddItemsModifier extends LootModifier {
    public static final Supplier<Codec<AddItemsModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).and(ExtraCodecs.nonEmptyList(BuiltInRegistries.ITEM.byNameCodec().listOf())
            .fieldOf("item").forGetter(m -> m.items)).apply(inst, AddItemsModifier::new)));

    private final List<Item> items;

    public AddItemsModifier(LootItemCondition[] conditionsIn, List<Item> items) {
        super(conditionsIn);
        this.items = items;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ObjectArrayList<ItemStack> newLoot = new ObjectArrayList<>();

        for (LootItemCondition condition : this.conditions) {
            if (!condition.test(context)) {
                return generatedLoot;
            }
        }

        if (generatedLoot.stream().anyMatch(itemStack -> itemStack.is(Items.TORCHFLOWER_SEEDS))) {
            generatedLoot.add(Items.PITCHER_POD.getDefaultInstance());
        } else {
            generatedLoot.add(Items.TORCHFLOWER_SEEDS.getDefaultInstance());
        }

        items.forEach(item -> generatedLoot.add(item.getDefaultInstance()));
        newLoot.add(Util.getRandom(generatedLoot, context.getRandom()));
        newLoot.add(Util.getRandom(generatedLoot, context.getRandom()));
        return newLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}

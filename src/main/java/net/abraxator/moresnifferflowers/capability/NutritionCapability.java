package net.abraxator.moresnifferflowers.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.networking.ModPacketHandler;
import net.abraxator.moresnifferflowers.networking.toClient.SyncGluedPacket;
import net.abraxator.moresnifferflowers.nutrition.NutritionType;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NutritionCapability {
    public Set<Item> items = new HashSet<>();
    public Set<Integer> unlockedEffects = new HashSet<>();

    public static final Codec<Set<Item>> ITEM_SET_CODEC =
            BuiltInRegistries.ITEM.byNameCodec()
                    .listOf()
                    .xmap(HashSet::new, ArrayList::new);

    public static final Codec<Set<Integer>> INT_SET_CODEC = Codec.INT.listOf().xmap(HashSet::new, ArrayList::new);

    public static final Codec<NutritionCapability> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    ITEM_SET_CODEC.fieldOf("items").forGetter(cap -> cap.items),
                    INT_SET_CODEC.fieldOf("effects").forGetter(cap -> cap.unlockedEffects))
            .apply(instance, (items, effects) -> {
                NutritionCapability capability  = new NutritionCapability();
                capability.items = items;
                capability.unlockedEffects = effects;
                return capability;
            }));


    public Set<Item> getItems() {
        return this.items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        var set = getItems();
        set.add(item);
        setItems(set);
    }

    public void sync(Player player) {}

    public static Holder<MobEffect> effectFromId(int id){
        if (id % 2 == 0){
           return NutritionType.getEffect(NutritionType.byId(id / 2), false);
        }
        return NutritionType.getEffect(NutritionType.byId(Mth.floor(id / 2f)), true);
    }

    public static int idFromNutrition(NutritionType type, boolean isPositive){
        int id = type.ordinal() * 2;
        if (isPositive) id++;
        return id;
    }

    public static final Map<Integer, ResourceLocation> ICON_FROM_ID = Map.of(
            0, MoreSnifferFlowers.loc("textures/mob_effect/slippery.png"),
            1, MoreSnifferFlowers.loc("textures/mob_effect/untouchable.png"),
            2, MoreSnifferFlowers.loc("textures/mob_effect/salty.png"),
            3, MoreSnifferFlowers.loc("textures/mob_effect/combo_meal.png"),
            4, MoreSnifferFlowers.loc("textures/mob_effect/pants_on_fire.png"),
            5, MoreSnifferFlowers.loc("textures/mob_effect/hardened_mouth.png"),
            6, MoreSnifferFlowers.loc("textures/mob_effect/sticky.png"),
            7, MoreSnifferFlowers.loc("textures/mob_effect/gluing_touch.png"),
            8, MoreSnifferFlowers.loc("textures/mob_effect/bland.png"),
            9, MoreSnifferFlowers.loc("textures/mob_effect/well_balanced.png")
    );
}

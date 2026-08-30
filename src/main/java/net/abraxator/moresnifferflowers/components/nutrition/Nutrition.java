package net.abraxator.moresnifferflowers.components.nutrition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.init.MSFDataMaps;
import net.minecraft.world.item.Item;

import java.util.*;

public record Nutrition(Map<NutritionType, Integer> nutritionEntries) {
    public static final Codec<Nutrition> CODEC = Codec.unboundedMap(NutritionType.CODEC, Codec.INT).xmap(Nutrition::new, Nutrition::nutritionEntries);
    public static final Nutrition EMPTY = new Nutrition(Map.of());

    @SuppressWarnings("deprecation")
    public static Nutrition getNutritionForItem(Item item) {
        Nutrition data = item.builtInRegistryHolder().getData(MSFDataMaps.NUTRITION);
        return data != null ? data : EMPTY;
    }

    public List<NutritionEntry> entryList(){
        return nutritionEntries.entrySet().stream().map(entry -> new NutritionEntry(entry.getKey(), entry.getValue())).toList();
    }

    public boolean hasType(NutritionType type) {
        if (!nutritionEntries.containsKey(type)) return false;
        return nutritionEntries.get(type) > 0;
    }

    public static NutritionType getLargestNutrition(Item item) {
        Nutrition nutrition = Nutrition.getNutritionForItem(item);

        List<NutritionEntry> list = new ArrayList<>(nutrition.entryList());
        if (list.isEmpty()) return NutritionType.NEUTRAL;
        list.sort(Comparator.comparing(nutritionEntry -> -(nutritionEntry.weight() + nutritionEntry.nutrition().priority)));

        return list.getFirst().nutrition();
    }

    public boolean isEmpty() {
        return this.equals(EMPTY);
    }

    public static Nutrition of(int neutral, int salty, int sweet, int sour, int spicy) {
        Map<NutritionType, Integer> map = new HashMap<>();
        if (neutral > 0) map.put(NutritionType.NEUTRAL, neutral);
        if (salty > 0) map.put(NutritionType.SALTY, salty);
        if (sweet > 0) map.put(NutritionType.SWEET, sweet);
        if (sour > 0) map.put(NutritionType.SOUR, sour);
        if (spicy > 0) map.put(NutritionType.SPICY, spicy);
        return new Nutrition(map);
    }

    public record NutritionEntry(NutritionType nutrition, int weight) {
        public static final Codec<NutritionEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                NutritionType.CODEC.fieldOf("nutrition").forGetter(NutritionEntry::nutrition),
                Codec.INT.fieldOf("weight").forGetter(NutritionEntry::weight)
        ).apply(instance, NutritionEntry::new));
    }

    public record NutritionPair(Item item, Nutrition nutrition){}
}

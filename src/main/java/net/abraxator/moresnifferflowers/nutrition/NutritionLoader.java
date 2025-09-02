package net.abraxator.moresnifferflowers.nutrition;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.*;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.*;
import java.util.stream.Collectors;

public class NutritionLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final Codec<Map<Either<TagKey<Item>, Item>, Integer>> CODEC = Codec.unboundedMap(
            Codec.either(
                    TagKey.hashedCodec(Registries.ITEM), BuiltInRegistries.ITEM.byNameCodec()
            ), Codec.INT
    );
    
    public static Map<String, Set<Nutrition>> modNutritions = ImmutableMap.of();
    public static Map<NutritionType, Set<Nutrition>> typeNutritions = ImmutableMap.of();
    public static Set<Nutrition> allNutritions = ImmutableSet.of();
    
    public NutritionLoader() {
        super(GSON, "nutrition");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        Map<String, Set<Nutrition>> modNutritions = new HashMap<>();
        Map<NutritionType, Set<Nutrition>> typeNutritions = new HashMap<>();
        Set<Nutrition> allNutritions = new HashSet<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            Map<Item, List<NutritionEntry>> currentModNutritions = Maps.newHashMap();
            ResourceLocation path = entry.getKey();
            
            try {
                for (NutritionType nutritionType : NutritionType.values()) {
                    JsonObject entries = entry.getValue().getAsJsonObject().getAsJsonObject(nutritionType.name);

                    DataResult<Map<Either<TagKey<Item>, Item>, Integer>> parse = CODEC.parse(JsonOps.INSTANCE, entries);

                    if(parse.isSuccess()) {

                        Map<Either<TagKey<Item>, Item>, Integer> values = parse.getOrThrow(); //this seems dangerous
                        Map<Item, NutritionEntry> map = new HashMap<>();
                        
                        for (Map.Entry<Either<TagKey<Item>, Item>, Integer> mapEntry : values.entrySet()) {
                            Set<Item> itemList = new HashSet<>();
                            Map.Entry<Item, NutritionEntry> ret;

                            if (mapEntry.getKey().left().isPresent()) {
                                itemList = (Arrays.stream(Ingredient.of(mapEntry.getKey().left().get()).getItems())
                                        .map(ItemStack::getItem)
                                        .collect(Collectors.toSet())
                                );
                            }
                            if (mapEntry.getKey().right().isPresent()) {
                                itemList = Set.of(mapEntry.getKey().right().get());
                            }

                            for (Item item : itemList) {
                                map.put(item, new NutritionEntry(nutritionType, mapEntry.getValue()));
                            }
                        }
                        
                        map.forEach((item, nutritionEntry) -> {
                            if (nutritionEntry.weight() <= 0) return;
                            currentModNutritions.merge(item,
                                    new ArrayList<>(List.of(nutritionEntry)),
                                    (existingList, newList) -> {
                                        existingList.addAll(newList);
                                        return existingList;
                                    });
                        });
                    }
                }
            } catch (IllegalStateException | JsonParseException e) {
                MoreSnifferFlowers.LOGGER.error("Error parsing nutrition {}", path, e);
            }
        
            var nutritionList = currentModNutritions.entrySet().stream()
                    .map(itemListEntry -> new Nutrition(itemListEntry.getKey(), itemListEntry.getValue()))
                    .collect(Collectors.toSet());
            modNutritions.put(
                    path.getPath(), 
                    nutritionList);
            allNutritions.addAll(nutritionList);

            for (Nutrition nutrition : allNutritions) {
                for (NutritionEntry nutritionEntry : nutrition.getNutritionEntries()) {
                    NutritionType type  = nutritionEntry.nutrition();
                    typeNutritions
                            .computeIfAbsent(type, k -> new HashSet<>())
                            .add(nutrition);
                }
            }
        }
        
        NutritionLoader.modNutritions = modNutritions;
        NutritionLoader.typeNutritions = typeNutritions;
        NutritionLoader.allNutritions = allNutritions;
    }
}

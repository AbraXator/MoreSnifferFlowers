package net.abraxator.moresnifferflowers.data.recipe.builder;

import com.google.gson.JsonObject;
import net.abraxator.moresnifferflowers.init.ModRecipeSerializers;
import net.abraxator.moresnifferflowers.recipes.CropressingRecipe;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.io.output.ThresholdingOutputStream;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

public class CropressingRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private Ingredient ingredient;
    private int count;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public CropressingRecipeBuilder(ItemLike result) {
        this.result = result.asItem();
    }

    public RecipeBuilder requiresCrop(Item crop) {
        this.ingredient = Ingredient.of(new ItemStack(crop));
        this.count = 16;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String pName, Criterion<?> pCriterion) {
        this.criteria.put(pName, pCriterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        this.ensureValid(pId);
        Advancement.Builder advancement = pRecipeOutput.advancement()
                        .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pId))
                        .rewards(AdvancementRewards.Builder.recipe(pId))
                        .requirements(AdvancementRequirements.Strategy.OR);
        CropressingRecipe cropressingRecipe = new CropressingRecipe(this.ingredient, this.count, this.result.getDefaultInstance());
        
        this.criteria.forEach(advancement::addCriterion);
        pRecipeOutput.accept(pId, cropressingRecipe, advancement.build(pId));
    }

    private void ensureValid(ResourceLocation id) {
        if(this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}

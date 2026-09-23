package com.humusberg.astralprogress.recipe;

import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.humusberg.astralprogress.AstralProgress;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class MachineRecipeBuilder implements RecipeBuilder {
    private String group;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private Item result;
    private Ingredient ingredient;
    @SuppressWarnings("rawtypes")
    private RecipeSerializer serializer;

    private MachineRecipeBuilder(Ingredient pIngredient, Item pResult, @SuppressWarnings("rawtypes") RecipeSerializer pSerializer) {
        this.ingredient = pIngredient;
        this.result = pResult.asItem();
        this.serializer = pSerializer;
    }
   public static MachineRecipeBuilder generic(Ingredient pIngredient, Item pResult, @SuppressWarnings("rawtypes")    RecipeSerializer pSerializer) {
      return new MachineRecipeBuilder(pIngredient, pResult, pSerializer);
   }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.group, this.ingredient, this.result,pRecipeId.withPrefix("recipes/"), this.advancement, this.serializer));
    }




    static class Result implements FinishedRecipe {

        private final ResourceLocation id;
        private final String group;
        private final Item result;
        private final Ingredient ingredient;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        @SuppressWarnings("rawtypes")
        private final RecipeSerializer serializer;
        
        public Result(ResourceLocation pId, String pGroup, Ingredient pIngredient, Item pResult, ResourceLocation pAdvancementId, Builder pAdvancement, @SuppressWarnings("rawtypes") RecipeSerializer pSerializer) {
            this.id = pId;
            this.group = pGroup;
            this.ingredient = pIngredient;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
            this.result = pResult;
            this.serializer = pSerializer;
        }
        @SuppressWarnings("deprecation")
        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.addProperty("type", AstralProgress.MODID + ":" + this.group);
            JsonArray ingredientArray = new JsonArray();
            ingredientArray.add(this.ingredient.toJson());
            ingredientArray.add(Ingredient.EMPTY.toJson());
            ingredientArray.add(Ingredient.EMPTY.toJson());

            pJson.add("ingredients", ingredientArray);
            JsonObject outputItem = new JsonObject();
            outputItem.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
            pJson.add("output", outputItem);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}

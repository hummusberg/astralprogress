package com.humusberg.astralprogress.recipe;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class AbstractRecipe implements Recipe<SimpleContainer> {
    final NonNullList<Ingredient> inputItems;
    final TagKey<Item> ingredientTag;
    private final ItemStack output;
    private final ResourceLocation id;

    public AbstractRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id) {
        this.inputItems = inputItems;
        this.ingredientTag = null;
        this.output = output;
        this.id = id;
    }

    public AbstractRecipe(TagKey<Item> ingredientTag, ResourceLocation id2) {
        this.inputItems = null;
        this.ingredientTag = ingredientTag;
        this.output = null;
        this.id = id2;

    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        Item[] containerArray = {};
        for (int i = 0; i < pContainer.getContainerSize(); i++) {
            containerArray = ArrayUtils.add(containerArray, pContainer.getItem(i).getItem());            
        }
        for (int i = 0; i < inputItems.size(); i++) {
            if (!(inputItems.get(i).isEmpty())) {
                if (!ArrayUtils.contains(containerArray, inputItems.get(i).getItems()[0].getItem())) {
                    return false;
                }
            }
        }
        return true;

    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    public TagKey<Item> getIngredienTagKey() {
        return ingredientTag;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public abstract RecipeSerializer<?> getSerializer();

    @Override
    public abstract RecipeType<?> getType();

    public static class Type implements RecipeType<AbstractRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "example";
    }
    
    public abstract static class Serializer implements RecipeSerializer<AbstractRecipe> {

    }
}

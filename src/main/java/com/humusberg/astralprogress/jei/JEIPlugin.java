package com.humusberg.astralprogress.jei;

import java.util.List;

import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.recipe.LatheRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

@JeiPlugin
public class JEIPlugin implements IModPlugin{

    @SuppressWarnings("removal")
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(AstralProgress.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new LatheCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<LatheRecipe> latheRecipes = recipeManager.getAllRecipesFor(LatheRecipe.Type.INSTANCE);
        registration.addRecipes(LatheCategory.LATHE_RECIPE_TYPE, latheRecipes);
    }  
}

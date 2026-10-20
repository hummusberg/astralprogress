package com.humusberg.astralprogress.jei;

import org.jetbrains.annotations.Nullable;

import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.recipe.PressRecipe;
import com.humusberg.astralprogress.util.ItemUtil;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class PressCategory implements IRecipeCategory<PressRecipe> {
    @SuppressWarnings("removal")
    public static final ResourceLocation UID = new ResourceLocation(AstralProgress.MODID, "press");
    @SuppressWarnings("removal")
    public static final ResourceLocation TEXTURE = new ResourceLocation(AstralProgress.MODID, "textures/gui/machinery_jei.png");

    public static RecipeType<PressRecipe> PRESS_RECIPE_TYPE =
        new RecipeType<>(UID, PressRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public PressCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 87);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemUtil.getBlock("press")));
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public RecipeType<PressRecipe> getRecipeType() {
        return PRESS_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Press");
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PressRecipe recipe, IFocusGroup focuses) {
        builder.addInvisibleIngredients(RecipeIngredientRole.CATALYST).addIngredients(Ingredient.of(ItemUtil.getBlock("press")));
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 35).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116 , 35).addItemStack(recipe.getResultItem(null));
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return this.background;
    }
    
}

package com.humusberg.astralprogress.recipe;

import com.humusberg.astralprogress.AstralProgress;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AstralProgress.MODID);

    public static final RegistryObject<RecipeSerializer<LatheRecipe>> LATHE_SERIALIZER =
        SERIALIZERS.register("lathe", () -> LatheRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<PressRecipe>> PRESS_SERIALIZER =
        SERIALIZERS.register("press", () -> PressRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<SolidBurnerRecipe>> SOLID_BURNER_SERIALIZER =
        SERIALIZERS.register("solid_burner", () -> SolidBurnerRecipe.Serializer.INSTANCE);
}

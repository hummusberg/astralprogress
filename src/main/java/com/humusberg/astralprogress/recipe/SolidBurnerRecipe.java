package com.humusberg.astralprogress.recipe;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.humusberg.astralprogress.AstralProgress;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class SolidBurnerRecipe extends AbstractRecipe {
    public SolidBurnerRecipe(TagKey<Item> ingredientTag, ResourceLocation id) {
        super(ingredientTag, id);
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        if (pContainer.getItem(0).is(ingredientTag)) {
            return true;
        }
        
        return false;

    }

    public static class Type implements RecipeType<SolidBurnerRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "solid_burner";
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<SolidBurnerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        @SuppressWarnings("removal")
        public static final  ResourceLocation ID = new ResourceLocation(AstralProgress.MODID, "solid_burner");

        @Override
        public SolidBurnerRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {    
            @SuppressWarnings("removal")
            ResourceLocation tagId = new ResourceLocation(GsonHelper.getAsString(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"), "tag"));
            TagKey<Item> tag = TagKey.create(Registries.ITEM, tagId);
            return new SolidBurnerRecipe(tag, pRecipeId);
        }

        @Override
        public @Nullable SolidBurnerRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            ResourceLocation tagId = pBuffer.readResourceLocation();
            TagKey<Item> tag = TagKey.create(Registries.ITEM, tagId);

            return new SolidBurnerRecipe(tag, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SolidBurnerRecipe pRecipe) {
            ResourceLocation tagId = pRecipe.getIngredienTagKey().cast(Registries.ITEM).get().location();
            pBuffer.writeResourceLocation(tagId);
        }
    }
}

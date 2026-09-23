package com.humusberg.astralprogress.datagen;

import java.util.concurrent.CompletableFuture;
import com.humusberg.astralprogress.AstralProgress;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import com.humusberg.astralprogress.util.ItemUtil;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagsProvider, AstralProgress.MODID, existingFileHelper);
    }
@SuppressWarnings("removal")
public static final TagKey<Item> HAMMERS = ItemTags.create(new ResourceLocation(AstralProgress.MODID, "hammers"));

    @Override
    protected void addTags(Provider pProvider) {
        this.tag(HAMMERS).add(ItemUtil.getItem("hammer"));
        this.tag(HAMMERS).add(ItemUtil.getItem("reinforced_hammer"));
    }
}

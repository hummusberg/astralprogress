package com.humusberg.astralprogress.datagen;

import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.Nullable;
import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.creativetabs.ModCreativeTabs;
import com.humusberg.astralprogress.util.ItemUtil;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;


public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider,
        @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AstralProgress.MODID, existingFileHelper);
    }
    @Override
    protected void addTags(Provider pProvider) {
        for (int i1 = 0; i1 < ModCreativeTabs.material.length; i1++) {
            for (int i2 = 0; i2 < ModBlockModelProvider.type.length; i2++) {
                Block block = ItemUtil.getBlock(ModCreativeTabs.material[i1]+"_"+ModBlockModelProvider.type[i2]);
                if (block != null && block != Blocks.AIR) {
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
                    this.tag(BlockTags.NEEDS_STONE_TOOL).add(block);
                }
            }
        }
    }
}

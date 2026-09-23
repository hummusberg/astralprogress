package com.humusberg.astralprogress.datagen;

import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.creativetabs.ModCreativeTabs;
import com.humusberg.astralprogress.util.ItemUtil;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider{
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AstralProgress.MODID, existingFileHelper);
    }

    @SuppressWarnings("removal")
    @Override
    protected void registerStatesAndModels() {
        for (int i1 = 0; i1 < ModCreativeTabs.material.length; i1++) {
            for (int i2 = 0; i2 < ModBlockModelProvider.type.length; i2++) {
                Block block = ItemUtil.getBlock(ModCreativeTabs.material[i1]+"_"+ModBlockModelProvider.type[i2]);
                if (block != null && block != Blocks.AIR) {
                    ModelFile model = models().getExistingFile(new ResourceLocation(AstralProgress.MODID, "block/" + ModCreativeTabs.material[i1]+"_"+ModBlockModelProvider.type[i2]));
                    simpleBlock(block, model);
                }
            }
        }
    }
}

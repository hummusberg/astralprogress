package com.humusberg.astralprogress.datagen;

import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.creativetabs.ModCreativeTabs;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BlockModelProvider{
    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AstralProgress.MODID, existingFileHelper);
    }
    public static String type[] = {
        "block",
        "ore"
    };

    @Override
    protected void registerModels() {
        for (int i1 = 0; i1 < ModCreativeTabs.material.length; i1++) {
            createTintedBlockModel(ModCreativeTabs.material[i1]);
            createTintedOreModel(ModCreativeTabs.material[i1]);
        }
    }

    @SuppressWarnings("removal")
    public BlockModelBuilder createTintedBlockModel(String material) {
        return withExistingParent(material+"_block",
                new ResourceLocation("block/cube_all")).texture("all",
                modLoc("item/block"))
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .face(Direction.NORTH).texture("#all").tintindex(0).end()
                .face(Direction.EAST).texture("#all").tintindex(0).end()
                .face(Direction.SOUTH).texture("#all").tintindex(0).end()
                .face(Direction.WEST).texture("#all").tintindex(0).end()
                .face(Direction.UP).texture("#all").tintindex(0).end()
                .face(Direction.DOWN).texture("#all").tintindex(0).end()
            .end();
    }
    @SuppressWarnings("removal")
    public BlockModelBuilder createTintedOreModel(String material) {
        if (material != "sulfur") {
            return withExistingParent(material + "_ore", new ResourceLocation("minecraft","block/block"))
                .renderType("minecraft:cutout")
                .texture("particle", new ResourceLocation("minecraft", "block/deepslate"))
                .texture("base", new ResourceLocation("minecraft", "block/deepslate"))
                .texture("overlay", modLoc("item/ore"))
                .element()
                    .from(0, 0, 0)
                    .to(16, 16, 16)
                    .face(Direction.DOWN).texture("#base").cullface(Direction.DOWN).end()
                    .face(Direction.UP).texture("#base").cullface(Direction.UP).end()
                    .face(Direction.NORTH).texture("#base").cullface(Direction.NORTH).end()
                    .face(Direction.SOUTH).texture("#base").cullface(Direction.SOUTH).end()
                    .face(Direction.WEST).texture("#base").cullface(Direction.WEST).end()
                    .face(Direction.EAST).texture("#base").cullface(Direction.EAST).end()
                .end()
                .element()
                    .from(0, 0, 0)
                    .to(16, 16, 16)
                    .face(Direction.DOWN).texture("#overlay").tintindex(0).cullface(Direction.DOWN).end()
                    .face(Direction.UP).texture("#overlay").tintindex(0).cullface(Direction.UP).end()
                    .face(Direction.NORTH).texture("#overlay").tintindex(0).cullface(Direction.NORTH).end()
                    .face(Direction.SOUTH).texture("#overlay").tintindex(0).cullface(Direction.SOUTH).end()
                    .face(Direction.WEST).texture("#overlay").tintindex(0).cullface(Direction.WEST).end()
                    .face(Direction.EAST).texture("#overlay").tintindex(0).cullface(Direction.EAST).end()
                .end();
        } else {
            return withExistingParent(material + "_ore", new ResourceLocation("minecraft","block/block"))
                .renderType("minecraft:cutout")
                .texture("particle", new ResourceLocation("minecraft", "block/netherrack"))
                .texture("base", new ResourceLocation("minecraft", "block/netherrack"))
                .texture("overlay", modLoc("item/ore"))
                .element()
                    .from(0, 0, 0)
                    .to(16, 16, 16)
                    .face(Direction.DOWN).texture("#base").cullface(Direction.DOWN).end()
                    .face(Direction.UP).texture("#base").cullface(Direction.UP).end()
                    .face(Direction.NORTH).texture("#base").cullface(Direction.NORTH).end()
                    .face(Direction.SOUTH).texture("#base").cullface(Direction.SOUTH).end()
                    .face(Direction.WEST).texture("#base").cullface(Direction.WEST).end()
                    .face(Direction.EAST).texture("#base").cullface(Direction.EAST).end()
                .end()
                .element()
                    .from(0, 0, 0)
                    .to(16, 16, 16)
                    .face(Direction.DOWN).texture("#overlay").tintindex(0).cullface(Direction.DOWN).end()
                    .face(Direction.UP).texture("#overlay").tintindex(0).cullface(Direction.UP).end()
                    .face(Direction.NORTH).texture("#overlay").tintindex(0).cullface(Direction.NORTH).end()
                    .face(Direction.SOUTH).texture("#overlay").tintindex(0).cullface(Direction.SOUTH).end()
                    .face(Direction.WEST).texture("#overlay").tintindex(0).cullface(Direction.WEST).end()
                    .face(Direction.EAST).texture("#overlay").tintindex(0).cullface(Direction.EAST).end()
                .end();
        }
    }   
}
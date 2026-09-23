package com.humusberg.astralprogress.datagen;

import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.creativetabs.ModCreativeTabs;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
public class ModItemModelProvider extends ItemModelProvider{
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AstralProgress.MODID, existingFileHelper);
    }
    public static String type[] = {
        "raw",
        "ingot",
        "gem",
        "dust",
        "nugget",
        "plate",
        "rod",
        "foil",
        "gear"
    };
    @Override
    public void registerModels() {
        for (int i1 = 0; i1 < ModCreativeTabs.material.length; i1++) {
            for (int i2 = 0; i2 < type.length; i2++) {
                createItemModel(ModCreativeTabs.material[i1],type[i2]);
            }
            createTintedBucket(ModCreativeTabs.material[i1] + "_bucket");
        }
        for (int i1 = 0; i1 < ModCreativeTabs.material.length; i1++) {
            createImpureDust(ModCreativeTabs.material[i1]);
            createTintedOreModel(ModCreativeTabs.material[i1]);
            for (int i2 = 0; i2 < ModBlockModelProvider.type.length; i2++) {
                createTintedItemModel(ModCreativeTabs.material[i1],ModBlockModelProvider.type[i2]);
            }
        }
        simpleItem("hammer");
        simpleItem("reinforced_hammer");
        simpleItem("wire_cutters");
        simpleItem("copper_coil");
        simpleItem("copper_wire");
        simpleItem("tier_0_capacitor");
        simpleItem("tier_0_resistor");
        simpleItem("tier_0_circuit");
        for (int i = 0; i < ModCreativeTabs.buckets.length; i++) {
            createTintedBucket(ModCreativeTabs.buckets[i]);
        }
    }
    @SuppressWarnings("removal")
    public ItemModelBuilder createTintedBucket(String name) {
        return withExistingParent(getBuilder(name).getLocation().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation("minecraft", "item/bucket")).
                texture("layer1",
                modLoc("item/liquid_overlay"));
    }
    @SuppressWarnings("removal")
    public ItemModelBuilder createImpureDust(String name) {
        return withExistingParent(getBuilder(name + "_impure_dust").getLocation().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                modLoc( "item/dust")).
                texture("layer1",
                modLoc("item/impure_dust"));
    }
    @SuppressWarnings("removal")
    private ItemModelBuilder simpleItem(String item) {
        return withExistingParent(getBuilder(item).getLocation().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(AstralProgress.MODID, getBuilder(item).getLocation().getPath()));
    }
    @SuppressWarnings("removal")
    public ItemModelBuilder createItemModel(String material,String type) {
        return withExistingParent(material+"_"+type,
                new ResourceLocation("item/generated")).texture("layer0",
                modLoc("item/"+type));
    }
    @SuppressWarnings("removal")
    public ItemModelBuilder createTintedItemModel(String material,String type) {
        return withExistingParent(material+"_"+type,
                new ResourceLocation("block/cube_all")).texture("all",
                modLoc("item/"+type))
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
    public ItemModelBuilder createTintedOreModel(String material) {
        if (material != "sulfur") {
            return withExistingParent(material + "_ore", new ResourceLocation("minecraft","block/block"))
                .renderType("minecraft:cutout")
                .texture("particle", new ResourceLocation("minecraft", "block/stone"))
                .texture("base", new ResourceLocation("minecraft", "block/stone"))
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

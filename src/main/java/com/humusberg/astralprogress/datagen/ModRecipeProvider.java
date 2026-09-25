package com.humusberg.astralprogress.datagen;

import java.util.function.Consumer;

import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.creativetabs.ModCreativeTabs;
import com.humusberg.astralprogress.recipe.LatheRecipe;
import com.humusberg.astralprogress.recipe.MachineRecipeBuilder;
import com.humusberg.astralprogress.recipe.PressRecipe;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import com.humusberg.astralprogress.util.ItemUtil;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        for (int i = 0; i < ModCreativeTabs.material.length; i++) {
            if (ItemUtil.getItem(ModCreativeTabs.material[i] + "_gem") == Items.AIR) { 
                makeMaterialRecipes(ModCreativeTabs.material[i], pWriter);
            } else {
                makeDustfromGem(ModCreativeTabs.material[i], pWriter);
            }
            if (ItemUtil.getItem(ModCreativeTabs.material[i]+"_ingot") != Items.AIR) {
                makeDustfromIngot(ModCreativeTabs.material[i], pWriter);
            }
            if (ItemUtil.getItem(ModCreativeTabs.material[i]+"_raw") != Items.AIR) {
                makeImpureDustfromRaw(ModCreativeTabs.material[i], pWriter);
            }
        }
        makeRecipes(pWriter);
    }
    protected static void makeMaterialRecipes(String material, Consumer<FinishedRecipe> pWriter) {
        oreMelting(material, pWriter);
        makeBlockfromIngot(material,pWriter);
        makeIngotfromNugget(material,pWriter);
        makeIngotfromDust(material,pWriter);
        makeNuggetfromIngot(material,pWriter);
        makeIngotfromBlock(material,pWriter);
        if (ItemUtil.getItem(material+"_plate") != Items.AIR) {
            makePlate(material, pWriter);
            makeRod(material, pWriter);
            makeFoil(material, pWriter);
            makeGear(material, pWriter);
        }
    }
    
    protected static void oreMelting(String material, Consumer<FinishedRecipe> pWriter) {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ItemUtil.getItem(material+"_raw")), RecipeCategory.MISC, ItemUtil.getItem(material+"_ingot"), 0,200, RecipeSerializer.SMELTING_RECIPE)
            .group("smelting").unlockedBy(material, has(ItemUtil.getItem(material+"_ingot")))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_smelting");
    }

    protected static void makeBlockfromIngot(String material, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getBlock(material+"_block"))
            .pattern("III")
            .pattern("III")
            .pattern("III")
            .define('I', ItemUtil.getItem(material+"_ingot"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter,AstralProgress.MODID + ":" + material + "_makeblockfromingot");
    }

    protected static void makeIngotfromNugget(String material, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem(material+"_ingot"))
            .pattern("III")
            .pattern("III")
            .pattern("III")
            .define('I', ItemUtil.getItem(material+"_nugget"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_makeingotfromnugget");
    }

    protected static void makeNuggetfromIngot(String material, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemUtil.getItem(material+"_nugget"),9)
            .requires(ItemUtil.getItem(material+"_ingot"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_makenuggetfromingot");
    }

    protected static void makeIngotfromBlock(String material, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemUtil.getItem(material+"_ingot"),9)
            .requires(ItemUtil.getBlock(material+"_block"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_makeingotfromblock");
    }

    @SuppressWarnings("removal")
    protected static void makeDustfromIngot(String material, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemUtil.getItem(material+"_dust"),1)
            .requires(ItemUtil.getItem(material+"_ingot"))
            .requires(ItemTags.create(new ResourceLocation(AstralProgress.MODID, "hammers")))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_makedustfromingot");
    }
    @SuppressWarnings("removal")
    protected static void makeImpureDustfromRaw(String material, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemUtil.getItem(material +"_impure_dust"),1)
            .requires(ItemUtil.getItem(material+"_raw"))
            .requires(ItemTags.create(new ResourceLocation(AstralProgress.MODID, "hammers")))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_makeimpuredustfromraw");
    }
    protected static void makeIngotfromDust(String material, Consumer<FinishedRecipe> pWriter) {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(ItemUtil.getItem(material+"_dust")), RecipeCategory.MISC, ItemUtil.getItem(material+"_ingot"), 0,200, RecipeSerializer.SMELTING_RECIPE)
            .group("smelting").unlockedBy(material, has(ItemUtil.getItem(material+"_ingot")))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_makeingotfromdust");
    }

    @SuppressWarnings("removal")
    protected static void makeDustfromGem(String material, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemUtil.getItem(material+"_dust"),1)
            .requires(ItemUtil.getItem(material+"_gem"))
            .requires(ItemTags.create(new ResourceLocation(AstralProgress.MODID, "hammers")))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_makedustfromingot");
    }

    @SuppressWarnings("removal")
    protected static void makePlate(String material, Consumer<FinishedRecipe> pWriter) {
        MachineRecipeBuilder.generic(Ingredient.of(ItemUtil.getItem(material+"_ingot")), ItemUtil.getItem(material+"_plate"), PressRecipe.Serializer.INSTANCE)
            .group("press")
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_press");
            
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem(material+"_plate"))
            .pattern("MM")
            .pattern("H ")
            .define('M',ItemUtil.getItem(material+"_ingot"))
            .define('H', ItemTags.create(new ResourceLocation(AstralProgress.MODID, "hammers")))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_makeplate");
    }
    @SuppressWarnings("removal")
    protected static void makeFoil(String material, Consumer<FinishedRecipe> pWriter) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemUtil.getItem(material+"_foil"))
            .requires(ItemUtil.getItem(material+"_plate"))
            .requires(ItemTags.create(new ResourceLocation(AstralProgress.MODID, "hammers")))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_makefoil");
    }
    @SuppressWarnings("removal")
    protected static void makeRod(String material, Consumer<FinishedRecipe> pWriter) {
        MachineRecipeBuilder.generic(Ingredient.of(ItemUtil.getItem(material+"_ingot")), ItemUtil.getItem(material+"_rod"), LatheRecipe.Serializer.INSTANCE)
            .group("lathe")
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_lathe");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem(material+"_rod"))
            .pattern("MH")
            .pattern("M ")
            .define('M', ItemUtil.getItem(material+"_ingot"))
            .define('H', ItemTags.create(new ResourceLocation(AstralProgress.MODID, "hammers")))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_makerod");
    }
    @SuppressWarnings("removal")
    protected static void makeGear(String material, Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem(material+"_gear"))
            .pattern(" P ")
            .pattern("PHP")
            .pattern(" P ")
            .define('P', ItemUtil.getItem(material+"_plate"))
            .define('H', ItemTags.create(new ResourceLocation(AstralProgress.MODID, "hammers")))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter, AstralProgress.MODID + ":" + material + "_makegear");
    }
    @SuppressWarnings("removal")
    static void makeRecipes(Consumer<FinishedRecipe> pWriter) {
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ItemUtil.getItem("aqueous_magnetic_iron_bucket")), RecipeCategory.MISC, ItemUtil.getItem("magnetic_iron_residiue_bucket"), 0,200)
            .group("campfire_cooking")
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemUtil.getItem("magnetic_iron_dust"))
            .requires(ItemUtil.getItem("magnetic_iron_residiue_bucket"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("wire_cutters"))
            .pattern("PHP")
            .pattern(" R ")
            .pattern("S S")
            .define('P', ItemUtil.getItem("iron_plate"))
            .define('R', ItemUtil.getItem("iron_rod"))
            .define('S', Items.STICK)
            .define('H', ItemTags.create(new ResourceLocation(AstralProgress.MODID, "hammers")))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("hammer"))
            .pattern(" CP")
            .pattern(" SC")
            .pattern("S  ")
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .define('C', Items.STONE)
            .define('S', Items.STICK)
            .define('P', ItemTags.PLANKS)
            .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("reinforced_hammer"))
            .pattern(" PI")
            .pattern(" SP")
            .pattern("S  ")
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .define('I', Items.IRON_INGOT)
            .define('S', Items.STICK)
            .define('P', ItemUtil.getItem("iron_plate"))
            .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("machine_casing"))
            .pattern("PPP")
            .pattern("R R")
            .pattern("PPP")
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .define('R', ItemUtil.getItem("iron_rod"))
            .define('P', ItemUtil.getItem("iron_plate"))
            .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("rotor"))
            .pattern("PRP")
            .pattern("RIR")
            .pattern("PRP")
            .define('P', ItemUtil.getItem("magnetic_iron_plate"))
            .define('R', ItemUtil.getItem("iron_rod"))
            .define('I', ItemUtil.getItem("magnetic_iron_ingot"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("stator"))
            .pattern("RCR")
            .pattern("CPC")
            .pattern("RCR")
            .define('R', ItemUtil.getItem("iron_rod"))
            .define('C', ItemUtil.getItem("copper_coil"))
            .define('P', ItemUtil.getItem("iron_plate"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("motor"))
            .pattern("RSR")
            .pattern("POP")
            .pattern("RPR")
            .define('R', ItemUtil.getItem("iron_rod"))
            .define('S', ItemUtil.getItem("stator"))
            .define('P', ItemUtil.getItem("iron_plate"))
            .define('O', ItemUtil.getItem("rotor"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("alternator"))
            .pattern("ROR")
            .pattern("PSP")
            .pattern("RPR")
            .define('R', ItemUtil.getItem("iron_rod"))
            .define('S', ItemUtil.getItem("stator"))
            .define('P', ItemUtil.getItem("iron_plate"))
            .define('O', ItemUtil.getItem("rotor"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("servo"))
            .pattern("CGC")
            .pattern("RPR")
            .pattern("CAC")
            .define('C', ItemUtil.getItem("copper_coil"))
            .define('G', ItemUtil.getItem("iron_gear"))
            .define('R', Items.REDSTONE)
            .define('P', ItemUtil.getItem("iron_plate"))
            .define('A', ItemUtil.getItem("rubber_sheet"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("solenoid"))
            .pattern("CRC")
            .pattern("IPI")
            .pattern("CAC")
            .define('C', ItemUtil.getItem("copper_coil"))
            .define('R', Items.REDSTONE)
            .define('I', ItemUtil.getItem("iron_rod"))
            .define('P', ItemUtil.getItem("iron_plate"))
            .define('A', ItemUtil.getItem("rubber_sheet"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("insulated_paper"))
            .pattern(" P ")
            .pattern(" R ")
            .pattern(" W ")
            .define('P', Items.PAPER)
            .define('R', Items.REDSTONE)
            .define('W', Items.WHITE_WOOL)
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("tier_0_capacitor"))
            .pattern(" L ")
            .pattern(" P ")
            .pattern(" C ")
            .define('L', ItemUtil.getItem("lead_foil"))
            .define('P', ItemUtil.getItem("insulated_paper"))
            .define('C', ItemUtil.getItem("copper_nugget"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("tier_0_resistor"))
            .pattern("   ")
            .pattern("PCP")
            .pattern("N N")
            .define('P', ItemUtil.getItem("insulated_paper"))
            .define('C', Items.CHARCOAL)
            .define('N', ItemUtil.getItem("copper_nugget"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("tier_0_circuit"))
            .pattern("RWR")
            .pattern("CWC")
            .pattern("PPP")
            .define('P', ItemTags.WOODEN_PRESSURE_PLATES)
            .define('R', ItemUtil.getItem("tier_0_resistor"))
            .define('C', ItemUtil.getItem("tier_0_capacitor"))
            .define('W', ItemUtil.getItem("copper_wire"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);
        
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("tier_1_transistor"))
            .pattern(" W ")
            .pattern("PSW")
            .pattern(" W ")
            .define('W', ItemUtil.getItem("copper_wire"))
            .define('P', ItemUtil.getItem("polyethylene_sheet"))
            .define('S', ItemUtil.getItem("silicon"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("tier_1_capacitor"))
            .pattern("PC")
            .pattern("CP")
            .pattern("NN")
            .define('P', ItemUtil.getItem("polyethylene_foil"))
            .define('C', ItemUtil.getItem("copper_foil"))
            .define('N', ItemUtil.getItem("copper_nugget"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemUtil.getItem("tier_1_insulated_wire"))
            .requires(ItemUtil.getItem("copper_wire"))
            .requires(ItemUtil.getItem("polyethylene_sheet"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemUtil.getItem("copper_wire"))
            .requires(ItemUtil.getItem("copper_foil"))
            .requires(ItemUtil.getItem("wire_cutters"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemUtil.getItem("tier_1_circuit"))
            .pattern("RWR")
            .pattern("LPS")
            .pattern("CWC")
            .define('R', ItemUtil.getItem("tier_1_resistor"))
            .define('W', ItemUtil.getItem("tier_1_insulated_wire"))
            .define('C', ItemUtil.getItem("tier_1_capacitor"))
            .define('P', ItemUtil.getItem("etched_pcb"))
            .define('L', ItemUtil.getItem("logic_board"))
            .define('S', ItemUtil.getItem("status_light"))
            .unlockedBy(getHasName(Items.AIR), has(Items.AIR))
            .save(pWriter);
    }
}

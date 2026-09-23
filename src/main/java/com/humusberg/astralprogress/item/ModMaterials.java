package com.humusberg.astralprogress.item;

import com.humusberg.astralprogress.block.TintedBlock;
import com.humusberg.astralprogress.fluid.ModFluidTypes;
import com.humusberg.astralprogress.fluid.ModFluids;
import com.humusberg.astralprogress.fluid.RegisterFluids;
import com.humusberg.astralprogress.util.ItemUtil;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;

public class ModMaterials { 
    public static String registerSoftMaterial(String material, DeferredRegister<Item> ItemRegister, DeferredRegister<Block> BlockRegister, DeferredRegister<Fluid> FluidRegister, Boolean RegisterVanilla, Boolean RegisterOre, int Tint) {
        if (RegisterVanilla == true) {
            ItemRegister.register(material + "_ingot", () -> new TintedItem(Tint, new Item.Properties()));
            ItemRegister.register(material + "_nugget", () -> new TintedItem(Tint, new Item.Properties()));

            BlockRegister.register(material + "_block", () -> new TintedBlock(Tint, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
            ItemRegister.register(material + "_block", () -> new BlockItem(ItemUtil.getBlock(material + "_block"), new Item.Properties()));
        }
        if (RegisterOre == true) {
            ItemRegister.register(material + "_raw", () -> new TintedItem(Tint, new Item.Properties()));
            BlockRegister.register(material + "_ore", () -> new TintedBlock(Tint, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
            ItemRegister.register(material + "_ore", () -> new BlockItem(ItemUtil.getBlock(material + "_ore"), new Item.Properties()));
            ItemRegister.register(material + "_impure_dust", () -> new TintedItem(Tint, new Item.Properties()));
        }
        ItemRegister.register(material + "_dust", () -> new TintedItem(Tint, new Item.Properties()));
        ItemRegister.register(material + "_plate", () -> new TintedItem(Tint, new Item.Properties()));
        ItemRegister.register(material + "_foil", () -> new TintedItem(Tint, new Item.Properties()));
        ItemRegister.register(material + "_rod", () -> new TintedItem(Tint, new Item.Properties()));
        ItemRegister.register(material + "_gear", () -> new TintedItem(Tint, new Item.Properties()));
        RegisterFluids.registerFluid(ModFluids.FLUIDS, ModFluidTypes.FLUID_TYPES, "molten_" + material, Tint | 0xFF000000, true);
        return material;
    }
    public static String registerHardMaterial(String material, DeferredRegister<Item> ItemRegister, DeferredRegister<Block> BlockRegister, DeferredRegister<Fluid> FluidRegister, Boolean RegisterVanilla, Boolean RegisterOre, int Tint) {
        if (RegisterVanilla == true) {
            ItemRegister.register(material + "_ingot", () -> new TintedItem(Tint, new Item.Properties()));
            ItemRegister.register(material + "_nugget", () -> new TintedItem(Tint, new Item.Properties()));

            BlockRegister.register(material + "_block", () -> new TintedBlock(Tint, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
            ItemRegister.register(material + "_block", () -> new BlockItem(ItemUtil.getBlock(material + "_block"), new Item.Properties()));
        }
        if (RegisterOre == true) {
            ItemRegister.register(material + "_raw", () -> new TintedItem(Tint, new Item.Properties()));
            BlockRegister.register(material + "_ore", () -> new TintedBlock(Tint, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
            ItemRegister.register(material + "_ore", () -> new BlockItem(ItemUtil.getBlock(material + "_ore"), new Item.Properties()) );
            ItemRegister.register(material + "_impure_dust", () -> new TintedItem(Tint, new Item.Properties()));
        }
        ItemRegister.register(material + "_dust", () -> new TintedItem(Tint, new Item.Properties()));
        RegisterFluids.registerFluid(ModFluids.FLUIDS, ModFluidTypes.FLUID_TYPES, "molten_" + material, Tint | 0xFF000000, true);
        return material;
    }
    public static String registerGemMaterial(String material, DeferredRegister<Item> ItemRegister, DeferredRegister<Block> BlockRegister, Boolean RegisterVanilla, Boolean RegisterOre, int Tint) {
        if (RegisterOre == true) {
            ItemRegister.register(material + "_gem", () -> new TintedItem(Tint, new Item.Properties()));
        }
        ItemRegister.register(material + "_dust", () -> new TintedItem(Tint, new Item.Properties()));
        if (RegisterOre == true) {
            BlockRegister.register(material + "_ore", () -> new TintedBlock(Tint, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
            ItemRegister.register(material + "_ore", () -> new BlockItem(ItemUtil.getBlock(material + "_ore"), new Item.Properties()));
        }
        return material;
    }
}

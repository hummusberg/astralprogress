package com.humusberg.astralprogress.util;

import com.humusberg.astralprogress.AstralProgress;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ItemUtil {
    @SuppressWarnings({ "deprecation", "removal" })
    public static Item getItem(String item) {
        if (item.equals("iron_ingot")) {
            return Items.IRON_INGOT;
        } else if (item.equals("iron_nugget")) {
            return Items.IRON_NUGGET;
        } else if (item.equals("copper_ingot")) {
            return Items.COPPER_INGOT;
        } else {
            return BuiltInRegistries.ITEM.get(new ResourceLocation(AstralProgress.MODID, item));
        }
    }
    @SuppressWarnings({ "deprecation", "removal" })
    public static Block getBlock(String block) {
        if (block.equals("iron_block")) {
            return Blocks.IRON_BLOCK;
        } else if (block.equals("copper_block")) {
            return Blocks.COPPER_BLOCK;
        } else {
            return BuiltInRegistries.BLOCK.get(new ResourceLocation(AstralProgress.MODID, block));
        }
    }
}

package com.humusberg.astralprogress.datagen;

import java.util.Set;

import com.humusberg.astralprogress.creativetabs.ModCreativeTabs;
import com.humusberg.astralprogress.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;
import com.humusberg.astralprogress.util.ItemUtil;

public class ModBlockLootSubProvider extends BlockLootSubProvider {
    protected ModBlockLootSubProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }
    
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModItems.BLOCKS.getEntries().stream()
            .flatMap(RegistryObject::stream)
            ::iterator;        
    }

    @Override
    protected void generate() {
        this.dropSelf(ItemUtil.getBlock("kiln_hearth"));
        this.dropSelf(ItemUtil.getBlock("kiln_chimney"));
        this.dropSelf(ItemUtil.getBlock("lathe"));
        this.dropSelf(ItemUtil.getBlock("solid_burner"));
        this.dropSelf(ItemUtil.getBlock("basic_battery"));
        this.dropSelf(ItemUtil.getBlock("cauldron"));
        for (int i1 = 0; i1 < ModCreativeTabs.material.length; i1++) {
            if (ModCreativeTabs.material[i1] == "iron") {
                i1 =+ 2;
            }
            Block block = ItemUtil.getBlock(ModCreativeTabs.material[i1]+"_block");
            if (block != Blocks.AIR) {
                this.dropSelf(block);
            }
            Block block1 = ItemUtil.getBlock(ModCreativeTabs.material[i1]+"_ore");
            if (ItemUtil.getItem(ModCreativeTabs.material[i1] + "_gem") != Items.AIR) {
                Item item = ItemUtil.getItem(ModCreativeTabs.material[i1]+"_gem");
                if (block1 != null && block1 != Blocks.AIR) {
                    this.dropOther(block1, item);
                }
            } else if (ItemUtil.getItem(ModCreativeTabs.material[i1] + "_raw") != Items.AIR) {
                Item item = ItemUtil.getItem(ModCreativeTabs.material[i1]+"_raw");
                if (block1 != null && block1 != Blocks.AIR) {
                    this.dropOther(block1, item);
                }
            }
        }
    }
}
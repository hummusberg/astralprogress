package com.humusberg.astralprogress.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CraftingTool extends Item {
    private final int durability;

    public CraftingTool(int durability) {
        super(new Item.Properties()
            .durability(durability)
        );
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }
    

    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack newStack = itemStack.copy();
        newStack.setDamageValue(itemStack.getDamageValue() + 1);

        if (newStack.getDamageValue() >= newStack.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return newStack;
    }
}   

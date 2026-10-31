package com.humusberg.astralprogress.machines.press;

import java.util.Optional;

import com.humusberg.astralprogress.machines.AbstractMachineTile;
import com.humusberg.astralprogress.machines.ModTileEntities;
import com.humusberg.astralprogress.menu.press.PressMenu;
import com.humusberg.astralprogress.recipe.PressRecipe;
import net.minecraft.core.BlockPos;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PressTile extends AbstractMachineTile implements MenuProvider {
    @SuppressWarnings("rawtypes")
    public static Optional<Recipe> recipe;

    public PressTile(BlockPos pPos, BlockState pBlockState) {
        super(ModTileEntities.PRESS.get(), pPos, pBlockState, recipe);
    }
    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pBlockPos, BlockState pState, T blockEntity) {
        if (pLevel.isClientSide) {
            return;
        }
        if (!(blockEntity instanceof PressTile tile)) {
            return;
        }
        tile.autoOutput(tile.itemStackHandler, tile.direction, tile.outputDirection, pLevel, pBlockPos);
        tile.processItem();
    }

    @Override
    public boolean hasRecipe() {
        Optional<PressRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        return recipe.isPresent();
    }

    @Override
    public Optional<PressRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        return level.getRecipeManager().getRecipeFor(PressRecipe.Type.INSTANCE, inventory, level);
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PressMenu(pContainerId, pPlayerInventory, this);
    }
    @Override
    public Component getDisplayName() {
        return Component.literal("Press");
    }
}

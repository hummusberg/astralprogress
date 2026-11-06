package com.humusberg.astralprogress.machines.wiremill;

import java.util.Optional;

import com.humusberg.astralprogress.machines.AbstractMachineTile;
import com.humusberg.astralprogress.machines.ModTileEntities;
import com.humusberg.astralprogress.menu.wiremill.WiremillMenu;
import com.humusberg.astralprogress.recipe.WiremillRecipe;
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

public class WiremillTile extends AbstractMachineTile implements MenuProvider {
    public static Optional<? extends Recipe<?>> recipe;

    public WiremillTile(BlockPos pPos, BlockState pBlockState) {
        super(ModTileEntities.WIREMILL.get(), pPos, pBlockState, recipe, 3000, 1024);
    }
    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pBlockPos, BlockState pState, T blockEntity) {
        if (pLevel.isClientSide) {
            return;
        }
        if (!(blockEntity instanceof WiremillTile tile)) {
            return;
        }
        tile.autoOutput(tile.itemStackHandler, tile.direction, tile.outputDirection, pLevel, pBlockPos);
        tile.processItem();
    }

    @Override
    public boolean hasRecipe() {
        Optional<WiremillRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        return recipe.isPresent();
    }

    @Override
    public Optional<WiremillRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        return level.getRecipeManager().getRecipeFor(WiremillRecipe.Type.INSTANCE, inventory, level);
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new WiremillMenu(pContainerId, pPlayerInventory, this);
    }
    @Override
    public Component getDisplayName() {
        return Component.literal("Wiremill");
    }
}

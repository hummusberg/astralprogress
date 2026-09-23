package com.humusberg.astralprogress.machines;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.humusberg.astralprogress.menu.SolidBurnerMenu;
import com.humusberg.astralprogress.recipe.SolidBurnerRecipe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class SolidBurnerTile extends BlockEntity implements MenuProvider{
    public int timer;
    public int burnTime = 1;
    private final LazyOptional<ItemStackHandler> inventoryOptional = LazyOptional.of(() -> this.itemStackHandler);

    private final GeneratorEnergyStorage energyStorage = new GeneratorEnergyStorage(19200, 1024, 1024, 0);

    private final LazyOptional<EnergyStorage> energyOptional = LazyOptional.of(() -> this.energyStorage);

    public SolidBurnerTile(BlockPos pPos, BlockState pBlockState) {
        super(ModTileEntities.SOLID_BURNER.get(), pPos, pBlockState);
    }

    ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        protected void onContentsChanged(int slot) {
            setChanged();
        };
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return super.insertItem(slot, stack, simulate);
        }
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return super.extractItem(slot, amount, simulate);
        };

        public int getSlotLimit(int slot) {
            return 64;
        };
    };

    private LazyOptional<IItemHandler> itemHandlerCapability =
        LazyOptional.of(() -> itemStackHandler);

    public LazyOptional<ItemStackHandler> getInventoryOptional() {
        return this.inventoryOptional;
    }
    public LazyOptional<EnergyStorage> getEnergyOptional() {
        return this.energyOptional;
    }
    public EnergyStorage getEnergyStorage() {
        return this.energyStorage;
    }
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemHandlerCapability.cast();
        }
        if (cap == ForgeCapabilities.ENERGY) {
            return energyOptional.cast();
        }
        return super.getCapability(cap, side);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandlerCapability.invalidate();
        energyOptional.invalidate();
    }

    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pBlockPos, BlockState pState, T blockEntity) {
        if (!(blockEntity instanceof SolidBurnerTile tile)) {
            return;
        }
        if (pLevel.isClientSide()) {
            return;
        }
        processItem(tile);
    }

    public static ItemStack getSlot(SolidBurnerTile tile, int slot) {
        return tile.itemStackHandler.getStackInSlot(slot);
    }
    public static void setSlot(SolidBurnerTile tile, int slot, Item item) {
        tile.itemStackHandler.setStackInSlot(slot, new ItemStack(item));
    }
    private static boolean hasRecipe(SolidBurnerTile tile) {
        Optional<SolidBurnerRecipe> recipe = getCurrentRecipe(tile);

        if (recipe.isEmpty()) {
            return false;
        }

        return recipe.isPresent();
    }
    private static void processItem(SolidBurnerTile tile) {
        if (tile.timer > 0) {
            tile.timer--;
            tile.energyStorage.getEnergy(60);
        }
        tile.setChanged();
        if (!hasRecipe(tile)) {
            return;
        }
        int fuelBurnTime = ForgeHooks.getBurnTime(getSlot(tile, 0).copyWithCount(1), RecipeType.SMELTING) / 10;
        if (tile.timer == 0 && !(tile.energyStorage.getEnergyStored() > tile.energyStorage.getMaxEnergyStored() - 1000)) {
            tile.burnTime = fuelBurnTime;
            tile.timer += fuelBurnTime;
            getSlot(tile, 0).shrink(1);
        }
    }

    private static Optional<SolidBurnerRecipe> getCurrentRecipe(SolidBurnerTile tile) {
        SimpleContainer inventory = new SimpleContainer(tile.itemStackHandler.getSlots());
        inventory.setItem(0, tile.itemStackHandler.getStackInSlot(0));

        return tile.level.getRecipeManager().getRecipeFor(SolidBurnerRecipe.Type.INSTANCE, inventory, tile.level);
    }
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("Inventory")) {
            itemStackHandler.deserializeNBT(pTag.getCompound("Inventory"));
        }
        if (pTag.contains("Energy")) {
            energyStorage.setEnergy(pTag.getInt("Energy"));
        }

        if (pTag.contains("Timer")) {
            timer = pTag.getInt("Timer");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("Timer", timer);
        pTag.putInt("Energy", energyStorage.getEnergyStored());
        pTag.put("Inventory", itemStackHandler.serializeNBT());
    }
    
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SolidBurnerMenu(pContainerId, pPlayerInventory, this);
    }
    @Override
    public Component getDisplayName() {
        return Component.literal("Solid Fuel Burner");
    }
}

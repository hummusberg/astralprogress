package com.humusberg.astralprogress.machines;

import java.util.Optional;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class AbstractMachineTile extends BlockEntity implements DirectionOutputTile{
    @SuppressWarnings("rawtypes")
    public Optional<Recipe> recipe;

    public AbstractMachineTile(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, @SuppressWarnings("rawtypes") Optional<Recipe> recipe) {
        super(pType, pPos, pBlockState);
        this.recipe = recipe;
    }

    @SuppressWarnings("rawtypes")
    public abstract Optional getCurrentRecipe();
    public abstract boolean hasRecipe();
    @SuppressWarnings("unchecked")
    public void processItem() {
        if (energyStorage.getEnergyStored() < 20) {
            return;
        }

        if (!processingItem) {
            recipe = getCurrentRecipe();
            if (!hasRecipe()) {
                return;
            }
        }
        
        NonNullList<Ingredient> ingredients = recipe.get().getIngredients();
        int outputSlot = 0;
        
        //Find Output Slot
        for (int i = 3; i < 6; i++) {
            if (canInsert(recipe.get().getResultItem(null), itemStackHandler.getStackInSlot(i))) {
                outputSlot = i;
                break;
            } else {
                outputSlot = 0;
            }
        }

        // Use Ingredients
        if (!processingItem && outputSlot != 0) {
            for (int i = 0; i < ingredients.size(); i++) {
                for (int j = 0; j < 3; j++) {
                    if (ingredients.get(i).getItems().length != 0 && itemStackHandler.getStackInSlot(j).is(ingredients.get(i).getItems()[0].getItem())) {
                        itemStackHandler.getStackInSlot(j).shrink(1);
                        break;
                    }
                }
            }
            processingItem = true;
        } 
        
        // Process Items
        if (processingItem) {
            timer++;
            energyStorage.consumeEnergy(20);
            if (timer >= 60) {
                if ((itemStackHandler.getStackInSlot(outputSlot).is(recipe.get().getResultItem(null).getItem()))) {
                    itemStackHandler.getStackInSlot(outputSlot).grow(1);
                } else {
                    itemStackHandler.setStackInSlot(outputSlot, new ItemStack(recipe.get().getResultItem(null).getItem()));
                }
                timer = 0;
                processingItem = false;
            }
        }
    }
    
    public static boolean canInsert(ItemStack inputStack, ItemStack outputStack) {
        if ((outputStack.isEmpty() && inputStack.getCount() <= inputStack.getMaxStackSize()) || (outputStack.is(inputStack.getItem()) && outputStack.getCount() + inputStack.getCount() <= outputStack.getMaxStackSize())) {
            return true;
        } else {
            return false;
        }
    }

    public Direction[] outputDirection = {

    };
    public Direction[] direction = {};
    public int timer;
    public boolean processingItem = false;
    public final LazyOptional<ItemStackHandler> inventoryOptional = LazyOptional.of(() -> this.itemStackHandler);

    public final MachineEnergyStorage energyStorage = new MachineEnergyStorage(5000, 1024, 1024, 0);

    public final LazyOptional<EnergyStorage> energyOptional = LazyOptional.of(() -> this.energyStorage);

    public ItemStackHandler itemStackHandler = new ItemStackHandler(6) {
        protected void onContentsChanged(int slot) {
            setChanged();
        };
        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot >= 3) {
                return stack;
            }

            return super.insertItem(slot, stack, simulate);
        }
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return super.extractItem(slot, amount, simulate);
        };

        public int getSlotLimit(int slot) {
            return 64;
        };
    };
    ItemStackHandler outputStackHandler = new ItemStackHandler(6) {
        protected void onContentsChanged(int slot) {
            setChanged();
        };

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return inputStackHandler.insertItem(slot, stack, simulate);
        }

        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot < 3) {
                return ItemStack.EMPTY;
            }
            return itemStackHandler.extractItem(slot, amount, simulate);
        };

        public int getSlotLimit(int slot) {
            return itemStackHandler.getSlotLimit(slot);
        };
    };

    ItemStackHandler inputStackHandler = new ItemStackHandler(6) {
        protected void onContentsChanged(int slot) {
            setChanged();
        };

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot >= 3) {
                return stack;
            }
            return itemStackHandler.insertItem(slot, stack, simulate);
        }

        public int getSlotLimit(int slot) {
            return itemStackHandler.getSlotLimit(slot);
        };
    };
    private LazyOptional<IItemHandler> itemHandlerCapability =
        LazyOptional.of(() -> itemStackHandler);

    private LazyOptional<IItemHandler> outputHandlerCapability =
        LazyOptional.of(() -> outputStackHandler);

    private LazyOptional<IItemHandler> inputHandlerCapability =
        LazyOptional.of(() -> inputStackHandler);

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
            return outputHandlerCapability.cast();
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
        inputHandlerCapability.invalidate();
        outputHandlerCapability.invalidate();
        energyOptional.invalidate();
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
        loadDirections(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        saveDirections(pTag);
        pTag.putInt("Timer", timer);
        pTag.putInt("Energy", energyStorage.getEnergyStored());
        pTag.put("Inventory", itemStackHandler.serializeNBT());
    }

    @Override
    public Direction[] getOutputDirection() {
        return outputDirection;
    }
    @Override
    public void setOutputDirection(Direction[] directions) {
        outputDirection = directions.clone();
    }
}

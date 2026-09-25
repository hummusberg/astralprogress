package com.humusberg.astralprogress.machines;

import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.humusberg.astralprogress.menu.PressMenu;
import com.humusberg.astralprogress.recipe.PressRecipe;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class PressTile extends BlockEntity implements MenuProvider, DirectionOutputTile {
    public Direction[] outputDirection = {

    };
    public Direction[] direction = {};
    public int timer;
    public int InitialTime;
    private final LazyOptional<ItemStackHandler> inventoryOptional = LazyOptional.of(() -> this.itemStackHandler);

    private final MachineEnergyStorage energyStorage = new MachineEnergyStorage(5000, 1024, 30, 0);

    private final LazyOptional<EnergyStorage> energyOptional = LazyOptional.of(() -> this.energyStorage);

    ItemStackHandler itemStackHandler = new ItemStackHandler(6) {
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
    public PressTile(BlockPos pPos, BlockState pBlockState) {
        super(ModTileEntities.PRESS.get(), pPos, pBlockState);
    }
    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pBlockPos, BlockState pState, T blockEntity) {
        if (!(blockEntity instanceof PressTile tile)) {
            return;
        }
        if (!pLevel.isClientSide()) {
            for (int i1 = 0; i1 < tile.outputDirection.length; i1++) {
                if (pLevel.getBlockEntity(pBlockPos.relative(tile.outputDirection[i1])) != null) {
                    LazyOptional<IItemHandler> handler = pLevel.getBlockEntity(pBlockPos.relative(tile.outputDirection[i1])).getCapability(
                        ForgeCapabilities.ITEM_HANDLER,
                        tile.outputDirection[i1].getOpposite()
                    );
                    handler.ifPresent(itemHandler -> {
                        for (int i = 0; i < tile.itemStackHandler.getSlots(); i++) {
                            if (!(i >= 3)) {
                                continue;
                            };

                            ItemStack fromStack = tile.itemStackHandler.getStackInSlot(i);
                            if (fromStack.isEmpty()) {
                                continue;
                            }

                            for (int j = 0; j < itemHandler.getSlots(); j++) {
                                if (itemHandler.insertItem(j, fromStack.copyWithCount(1), true).isEmpty()) {
                                    itemHandler.insertItem(j, fromStack.copyWithCount(1), false);
                                    fromStack.shrink(1);
                                    break;
                                }                                
                            }
                        }
                    });
                }
            }
            tile.InitialTime = tile.timer;
            processItem(tile);
            if (tile.InitialTime == tile.timer) {
                tile.timer = 0;
            }
            tile.direction = tile.outputDirection;
        }
    }

    public static ItemStack getSlot(PressTile tile, int slot) {
        return tile.itemStackHandler.getStackInSlot(slot);
    }
    public static void setSlot(PressTile tile, int slot, Item item) {
        tile.itemStackHandler.setStackInSlot(slot, new ItemStack(item));
    }
    private static boolean hasRecipe(PressTile tile) {
        Optional<PressRecipe> recipe = getCurrentRecipe(tile);

        if (recipe.isEmpty()) {
            return false;
        }

        return recipe.isPresent();
    }
    private static void processItem(PressTile tile) {
        if (!hasRecipe(tile)) {
            return;
        }
        if (tile.energyStorage.getEnergyStored() < 30) {
            return;
        }
        Optional<PressRecipe> recipe = getCurrentRecipe(tile);
        Item result = recipe.get().getResultItem(null).getItem();
        int inputSlot = 0;
        int outputSlot = 3;
        for (int i = 0; i < 3; i++) {
            if (recipe.get().getIngredients().get(0).test(getSlot(tile, i))) {
                inputSlot = i;
                i = 0;
                break;
            }
        }
        for (int i = 3; i < 6; i++) {
            if (getSlot(tile, i).is(result) && getSlot(tile, i).getCount() < 64) {
                outputSlot = i;
                i = 3;
                break;
            } else if (getSlot(tile, i).is(Items.AIR)) {
                outputSlot = i;
                i = 3;
                break;
            }
        }
        if (getSlot(tile, outputSlot).is(Items.AIR)) {
            tile.timer++;
            tile.energyStorage.consumeEnergy(20);
            tile.setChanged();
            if (tile.timer == 60) {
                setSlot(tile, outputSlot, result);
                getSlot(tile, inputSlot).shrink(1);
                tile.timer = 0;
                tile.setChanged();
            }
        } else if (getSlot(tile, outputSlot).is(result) && getSlot(tile, outputSlot).getCount() < 64) {
            tile.timer++;
            tile.energyStorage.consumeEnergy(20);
            tile.setChanged();
            if (tile.timer == 60) {
                getSlot(tile, outputSlot).grow(1);
                getSlot(tile, inputSlot).shrink(1);
                tile.timer = 0;
                tile.setChanged();
            }
        }
    }

    private static Optional<PressRecipe> getCurrentRecipe(PressTile tile) {
        SimpleContainer inventory = new SimpleContainer(tile.itemStackHandler.getSlots());
        for (int i = 0; i < tile.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, tile.itemStackHandler.getStackInSlot(i));
        }

        return tile.level.getRecipeManager().getRecipeFor(PressRecipe.Type.INSTANCE, inventory, tile.level);
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
        if (pTag.contains("Sides")) {
            int[] intOutputDirections = pTag.getIntArray("Sides");
            for (int i = 0; i < intOutputDirections.length; i++) {
                outputDirection = ArrayUtils.add(outputDirection, Direction.from3DDataValue(intOutputDirections[i]));
            }
        }
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
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new PressMenu(pContainerId, pPlayerInventory, this);
    }
    @Override
    public Component getDisplayName() {
        return Component.literal("Press");
    }
    public int getOutputArrayAsInt(PressTile tile) {
        String arrayAsString = "";
        for (int i = 0; i < outputDirection.length; i++) {
            arrayAsString = arrayAsString + Integer.toString(outputDirection[i].get3DDataValue());
        }
        if (arrayAsString.isBlank()) {
            return 6;
        }
        return Integer.parseInt(arrayAsString);
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

package com.humusberg.astralprogress.menu;

import org.apache.commons.lang3.ArrayUtils;

import com.humusberg.astralprogress.machines.PressTile;
import com.humusberg.astralprogress.util.ItemUtil;

import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

public class PressMenu extends AbstractContainerMenu{
    private Direction[] empty = {};
    private Direction[] outputDirection = {};
    private final PressTile blockEntity;
    private final ContainerLevelAccess levelAccess;
    private int energyStored;
    private int maxEnergy;
    private int timer;
    private boolean error;

    public PressMenu(int pContainerId, Inventory pInventory, FriendlyByteBuf additionalDataBuf) {
        this(pContainerId, pInventory, pInventory.player.level().getBlockEntity(additionalDataBuf.readBlockPos()));

    } 

    public PressMenu(int pContainerId, Inventory pInventory, BlockEntity pBlockEntity) {
        super(ModMenus.PRESS_MENU.get(), pContainerId);
        if(pBlockEntity instanceof PressTile tile) {
            this.blockEntity = tile;
        } else {
            throw new IllegalStateException("tile enity is not press");
        }

        this.levelAccess = ContainerLevelAccess.create(pBlockEntity.getLevel(), pBlockEntity.getBlockPos());
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return tile.getOutputArrayAsInt(tile);
            }

            @Override
            public void set(int pValue) {
                if (pValue == 6) {
                    outputDirection = empty;
                    return;
                }
                String stringDirections = Integer.toString(pValue);
                char[] charDirections = stringDirections.toCharArray();
                outputDirection = empty;
                for (int i = 0; i < charDirections.length; i++) {
                    outputDirection = ArrayUtils.add(outputDirection, Direction.from3DDataValue(((int) charDirections[i])));
                }

            }
            
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return tile.getEnergyStorage().getEnergyStored();
            }
            @Override
            public void set(int value) {
                energyStored = value;
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return tile.getEnergyStorage().getMaxEnergyStored();
            }
            @Override
            public void set(int value) {
                maxEnergy = value;
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return tile.timer;
            }
            @Override
            public void set(int value) {
                timer = value;
            }
        });
        addDataSlot(new DataSlot() {
			@Override
            public int get() {
                if (PressTile.getSlot(tile, 0).getItem() != Items.AIR || PressTile.getSlot(tile, 1).getItem() != Items.AIR || PressTile.getSlot(tile, 2).getItem() != Items.AIR) {
                     if (tile.timer == 0) {
                        return 1;
                     }
                }
                return 0;
            }
            @Override
            public void set(int value) {
                if (value == 1) {
                    error = true;
                } else {
                    error = false;
                }
                
            }
        });
        
        createPlayerHotbar(pInventory);
        createPlayerInventory(pInventory);
        createBlockEntityInventory(tile);
    }

    public int getEnergyStored() {
        return energyStored;
    }
    public int getMaxEnergy() {
        return maxEnergy;
    }
    public int getTimer() {
        return timer;
    }
    public boolean getError() {
        return error;
    }
    public Direction[] getDirections() {
        return outputDirection;
    }
    private void createBlockEntityInventory(PressTile tile) {
        tile.getInventoryOptional().ifPresent(inputStackHandler -> {
            addSlot(new SlotItemHandler(inputStackHandler, 0 , 17 , 26));
            addSlot(new SlotItemHandler(inputStackHandler, 1 , 35 , 26));
            addSlot(new SlotItemHandler(inputStackHandler, 2 , 53 , 26));
            addSlot(new SlotItemHandler(inputStackHandler, 3 , 107 , 26));
            addSlot(new SlotItemHandler(inputStackHandler, 4 , 125 , 26));
            addSlot(new SlotItemHandler(inputStackHandler, 5 , 143 , 26));
        });
    }

    private void createPlayerInventory(Inventory pInventory) {
        for (int row = 0; row < 3; row++) {
            for (int collumn = 0; collumn < 9; collumn++) {
                addSlot(new Slot(pInventory, 9 + collumn + (row * 9), 8 + (collumn * 18), 84 + (row * 18) ));
            }
        }
    }

    private void createPlayerHotbar(Inventory pInventory) {
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(pInventory, i, 8 + (i * 18), 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) { 
        Slot fromSlot = getSlot(pIndex);
        ItemStack fromStack = fromSlot.getItem();
        
        if(fromStack.getCount() <= 0) {
            fromSlot.set(ItemStack.EMPTY);
        }

        if (!fromSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack copyFromStack = fromStack.copy();
        if (pIndex >= 36) {
            if(!moveItemStackTo(fromStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if(!moveItemStackTo(fromStack, 36, 39, false)) {
                return ItemStack.EMPTY;
            }
        }
        fromSlot.setChanged();
        fromSlot.onTake(pPlayer, fromStack);

        return copyFromStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.levelAccess, pPlayer, ItemUtil.getBlock("press"));

    }
    
    public PressTile getBlockEntity() {
        return this.blockEntity;
    }
}

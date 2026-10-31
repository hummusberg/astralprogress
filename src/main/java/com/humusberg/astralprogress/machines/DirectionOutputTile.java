package com.humusberg.astralprogress.machines;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public interface DirectionOutputTile {
    public Direction[] getOutputDirection();
    public void setOutputDirection(Direction[] directions);

    default void saveDirections(CompoundTag pTag) {
        int[] intOutputDirections = {};
        for (int i = 0; i < getOutputDirection().length; i++) {
            intOutputDirections = ArrayUtils.add(intOutputDirections, getOutputDirection()[i].get3DDataValue());
        }
        
        pTag.putIntArray("Sides", intOutputDirections);
    }
    default void loadDirections(CompoundTag pTag) {
        if (pTag.contains("Sides")) {
            int[] intOutputDirections = pTag.getIntArray("Sides");
            for (int i = 0; i < intOutputDirections.length; i++) {
                setOutputDirection(ArrayUtils.add(getOutputDirection(), Direction.from3DDataValue(intOutputDirections[i])));
            }
        }
    };
    public default int getOutputArrayAsInt() {
        String arrayAsString = "";
        for (int i = 0; i < getOutputDirection().length; i++) {
            arrayAsString = arrayAsString + Integer.toString(getOutputDirection()[i].get3DDataValue());
        }
        if (arrayAsString.isBlank()) {
            return 6;
        }
        return Integer.parseInt(arrayAsString);
    }

    public default void autoOutput(ItemStackHandler itemStackHandler, Direction[] direction, Direction[] outputDirection, Level pLevel, BlockPos pBlockPos) {
        for (int i1 = 0; i1 < outputDirection.length; i1++) {
            if (pLevel.getBlockEntity(pBlockPos.relative(outputDirection[i1])) != null) {
                LazyOptional<IItemHandler> handler = pLevel.getBlockEntity(pBlockPos.relative(outputDirection[i1])).getCapability(
                    ForgeCapabilities.ITEM_HANDLER,
                    outputDirection[i1].getOpposite()
                );
                handler.ifPresent(itemHandler -> {
                    for (int i = 0; i < itemStackHandler.getSlots(); i++) {
                        if (!(i >= 3)) {
                            continue;
                        };

                        ItemStack fromStack = itemStackHandler.getStackInSlot(i);
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
            direction = outputDirection;
        }
    }
}

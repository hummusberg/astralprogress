package com.humusberg.astralprogress.machines;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

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
}

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
}

package com.humusberg.astralprogress.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class BasicBatteryTile extends AbstractBattery {
    public BasicBatteryTile(BlockPos pPos, BlockState pBlockState) {
        super(ModTileEntities.BASIC_BATTERY.get(), pPos, pBlockState, 200000, 1024);
    }
}

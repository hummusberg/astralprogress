package com.humusberg.astralprogress.machines.battery;

import com.humusberg.astralprogress.machines.ModTileEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BasicBattery extends Block implements EntityBlock {
    public BasicBattery(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ModTileEntities.BASIC_BATTERY.get().create(pPos, pState);
    }
}

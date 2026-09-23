package com.humusberg.astralprogress.machines;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;

public abstract class AbstractBattery extends BlockEntity {
    private final BatteryEnergyStorage energyStorage;
    private final LazyOptional<EnergyStorage> energyOptional;

    public AbstractBattery(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, int capacity, int transfer) {
        super(pType, pPos, pBlockState); 

        this.energyStorage = new BatteryEnergyStorage(capacity, transfer, transfer, 0);

        this.energyOptional = LazyOptional.of(() -> energyStorage);
   
    }


    public EnergyStorage getEnergyStorage() {
        return this.energyStorage;
    }

    public LazyOptional<EnergyStorage> getEnergyOptional() {
        return this.energyOptional;
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return this.energyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.energyOptional.invalidate();
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("Energy")) {
            this.energyStorage.setEnergy(pTag.getInt("Energy"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("Energy", this.energyStorage.getEnergyStored());
    }
}

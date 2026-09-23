package com.humusberg.astralprogress.machines;

import net.minecraftforge.energy.EnergyStorage;

public class MachineEnergyStorage extends EnergyStorage{

    public MachineEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }
    public int consumeEnergy(int amount) {
        return super.extractEnergy(amount, false);
    }
    
    public void setEnergy(int energy) {
        this.energy = Math.min(energy, this.capacity);
    }
    
    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }
}

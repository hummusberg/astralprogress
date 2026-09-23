package com.humusberg.astralprogress.machines;

import net.minecraftforge.energy.EnergyStorage;

public class GeneratorEnergyStorage extends EnergyStorage{

    public GeneratorEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }
    public int getEnergy(int amount) {
        return super.receiveEnergy(amount, false);
    }
    
    public void setEnergy(int energy) {
        this.energy = Math.min(energy, this.capacity);
    }

    @Override
    public int receiveEnergy(int maxExtract, boolean simulate) {
        return 0;
    }
}

package com.humusberg.astralprogress.machines;

import net.minecraftforge.energy.EnergyStorage;

public class BatteryEnergyStorage extends EnergyStorage {
    public BatteryEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }
    
    public void setEnergy(int energy) {
        this.energy = Math.min(energy, this.capacity);
    }
}

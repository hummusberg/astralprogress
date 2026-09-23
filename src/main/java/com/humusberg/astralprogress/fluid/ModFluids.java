package com.humusberg.astralprogress.fluid;

import com.humusberg.astralprogress.AstralProgress;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
        DeferredRegister.create(ForgeRegistries.FLUIDS, AstralProgress.MODID);

    public static final RegistryObject<FlowingFluid> HYDROFLUORIC_ACID =
        FLUIDS.register("hydrofluoric_acid",
                () -> new ForgeFlowingFluid.Source(ModFluids.HYDROFLUORIC_ACID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> HYDROFLUORIC_ACID_FLOWING =
        FLUIDS.register("hydrofluoric_acid_flowing",
                () -> new ForgeFlowingFluid.Flowing(ModFluids.HYDROFLUORIC_ACID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties HYDROFLUORIC_ACID_PROPERTIES = new ForgeFlowingFluid.Properties(ModFluidTypes.HYDROFLUORIC_ACID, HYDROFLUORIC_ACID, HYDROFLUORIC_ACID_FLOWING);
}

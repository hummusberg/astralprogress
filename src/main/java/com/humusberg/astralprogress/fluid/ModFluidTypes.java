package com.humusberg.astralprogress.fluid;

import java.util.function.Consumer;

import com.humusberg.astralprogress.AstralProgress;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
        DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, AstralProgress.MODID);

    public static final RegistryObject<FluidType> HYDROFLUORIC_ACID =
        FLUID_TYPES.register("hydrofluoric_acid",
            () -> new FluidType(FluidType.Properties.create()) {

                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    consumer.accept(new IClientFluidTypeExtensions() {

                        @SuppressWarnings("removal")
                        @Override
                        public ResourceLocation getStillTexture() {
                            return new ResourceLocation(
                                "minecraft",
                                "block/water_still"
                            );
                        }

                        @SuppressWarnings("removal")
                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return new ResourceLocation(
                                "minecraft",
                                "block/water_flow"
                            );
                        }

                        @Override
                        public int getTintColor() {
                            return 0xFFC87533;
                        }
                    });
                }
            });
}
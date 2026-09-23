package com.humusberg.astralprogress.fluid;

import java.util.function.Consumer;

import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.item.ModItems;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class RegisterFluids {
    @SuppressWarnings("removal")
    public static void registerFluid(DeferredRegister<Fluid> FluidRegister, DeferredRegister<FluidType> FluidTypeRegister, String name, int color, Boolean isMolten) {
        final RegistryObject<FluidType> FLUID_TYPE =
            FluidTypeRegister.register(name,
                () -> new FluidType(FluidType.Properties.create()) {

                    @Override
                    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                        consumer.accept(new IClientFluidTypeExtensions() {

                            @Override
                            public ResourceLocation getStillTexture() {
                                if (isMolten) {
                                    return new ResourceLocation(AstralProgress.MODID, "block/molten_still");
                                } else {
                                    return new ResourceLocation("minecraft", "block/water_still");
                                }

                            }

                            @Override
                            public ResourceLocation getFlowingTexture() {
                                if (isMolten) {
                                    return new ResourceLocation(AstralProgress.MODID, "block/molten_flow");
                                } else {
                                    return new ResourceLocation("minecraft", "block/water_flow");
                                }
                            }

                            @Override
                            public int getTintColor() {
                                return color;
                            }
                        });
                    }
                });

        final ForgeFlowingFluid.Properties[] properties =
                new ForgeFlowingFluid.Properties[1];

        RegistryObject<FlowingFluid> FLUID =
                FluidRegister.register(name,
                        () -> new ForgeFlowingFluid.Source(properties[0]));

        RegistryObject<FlowingFluid> FLUID_FLOWING =
                FluidRegister.register(name + "_flowing",
                        () -> new ForgeFlowingFluid.Flowing(properties[0]));

        properties[0] = new ForgeFlowingFluid.Properties(
                FLUID_TYPE,
                FLUID,
                FLUID_FLOWING);
        ModItems.registerTintedBucket(ModItems.ITEMS, name, color);
    }
}

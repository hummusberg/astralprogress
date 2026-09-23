package com.humusberg.astralprogress.worldgen;

import com.humusberg.astralprogress.AstralProgress;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES =
        DeferredRegister.create(ForgeRegistries.FEATURES, AstralProgress.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ORE_BLOB =
        FEATURES.register("ore_blob",
            () -> new OreBlobFeature(NoneFeatureConfiguration.CODEC)
        );
}

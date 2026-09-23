package com.humusberg.astralprogress.sound;

import com.humusberg.astralprogress.AstralProgress;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
        DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, AstralProgress.MODID);
    @SuppressWarnings("removal")
    public static final RegistryObject<SoundEvent> GRAPPLING_HOOK =
    SOUNDS.register("grappling_hook",
        () -> SoundEvent.createVariableRangeEvent(
            new ResourceLocation(AstralProgress.MODID, "grappling_hook")
        )
    );
    @SuppressWarnings("removal")
    public static final RegistryObject<SoundEvent> GUN =
    SOUNDS.register("gun",
        () -> SoundEvent.createVariableRangeEvent(
            new ResourceLocation(AstralProgress.MODID, "gun")
        )
    );
}

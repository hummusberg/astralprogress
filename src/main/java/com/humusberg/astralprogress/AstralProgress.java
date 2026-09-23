package com.humusberg.astralprogress;

import com.humusberg.astralprogress.creativetabs.ModCreativeTabs;
import com.humusberg.astralprogress.fluid.ModFluidTypes;
import com.humusberg.astralprogress.fluid.ModFluids;
import com.humusberg.astralprogress.item.ModItems;
import com.humusberg.astralprogress.machines.ModTileEntities;
import com.humusberg.astralprogress.menu.ModMenus;
import com.humusberg.astralprogress.packets.PacketHandler;
import com.humusberg.astralprogress.recipe.ModRecipes;
import com.humusberg.astralprogress.sound.ModSounds;
import com.humusberg.astralprogress.util.ItemUtil;
import com.humusberg.astralprogress.worldgen.ModFeatures;
import com.mojang.logging.LogUtils;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AstralProgress.MODID)
public class AstralProgress {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "astralprogress";

    public static final Logger LOGGER = LogUtils.getLogger();


    public AstralProgress(FMLJavaModLoadingContext context) {
        
        IEventBus modEventBus = context.getModEventBus();
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModItems.BLOCKS.register(modEventBus);
        ModRecipes.SERIALIZERS.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        ModTileEntities.TILE_ENTITY_TYPES.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);
        ModFluidTypes.FLUID_TYPES.register(modEventBus);
        ModMenus.MENU_TYPES.register(modEventBus);
        ModFeatures.FEATURES.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    
    @SuppressWarnings("removal")
    private void commonSetup(final FMLCommonSetupEvent event) {
        ItemProperties.register(
            ItemUtil.getItem("grappling_hook"),
            new ResourceLocation(AstralProgress.MODID, "fired"),
            (stack, level, entity, seed) ->
                stack.getOrCreateTag().getBoolean("fired") ? 1.0F : 0.0F
        );
        event.enqueueWork(() -> {
            PacketHandler.register();
        });
    }
}
package com.humusberg.astralprogress;

import com.humusberg.astralprogress.block.TintedBlock;
import com.humusberg.astralprogress.item.ModItems;
import com.humusberg.astralprogress.item.TintedBucket;
import com.humusberg.astralprogress.item.TintedItem;
import com.humusberg.astralprogress.menu.LatheMenuScreen;
import com.humusberg.astralprogress.menu.ModMenus;
import com.humusberg.astralprogress.menu.PressMenuScreen;
import com.humusberg.astralprogress.menu.SolidBurnerMenuScreen;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = AstralProgress.MODID,value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
            (stack, layer) -> {
                if (stack.getItem() instanceof TintedItem item && layer == 0) {
                    return item.getTint();
                }
                if (stack.getItem() instanceof TintedBucket item && layer == 1) {
                    return item.getTint();
                }
                return 0xFFFFFF;
            },
            ModItems.ITEMS.getEntries()
                .stream()
                .map(holder -> holder.get())    
                .toArray(Item[]::new)
        );
        event.register(
            (stack, layer) -> {
                if (stack.getItem() instanceof BlockItem blockItem) {
                    Block block = blockItem.getBlock();
                    if (block instanceof TintedBlock tintedBlock) {
                        return tintedBlock.getTint();
                    }
                }
                return 0xFFFFFF;
            },
            ModItems.BLOCKS.getEntries()
                .stream()
                .map(holder -> holder.get())
                .filter(block -> block instanceof TintedBlock)
                .toArray(Block[]::new)
        );
    }
    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(
            (state, world, pos, layer) -> {
                if (state.getBlock() instanceof TintedBlock tintedBlock) {
                    return tintedBlock.getTint();
                }
                return 0xFFFFFF;
            },
            ModItems.BLOCKS.getEntries()
                    .stream()
                    .map(holder -> holder.get())
                    .toArray(Block[]::new)
        );
    }
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.LATHE_MENU.get(), LatheMenuScreen::new);
            MenuScreens.register(ModMenus.PRESS_MENU.get(), PressMenuScreen::new);
            MenuScreens.register(ModMenus.SOLID_BURNER_MENU.get(), SolidBurnerMenuScreen::new);
        });
    }
}
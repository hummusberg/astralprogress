package com.humusberg.astralprogress.menu;

import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.menu.lathe.LatheMenu;
import com.humusberg.astralprogress.menu.press.PressMenu;
import com.humusberg.astralprogress.menu.solidBurner.SolidBurnerMenu;
import com.humusberg.astralprogress.menu.wiremill.WiremillMenu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = 
        DeferredRegister.create(ForgeRegistries.MENU_TYPES, AstralProgress.MODID);

    public static final RegistryObject<MenuType<LatheMenu>> LATHE_MENU =
        MENU_TYPES.register("lathe_menu", () -> IForgeMenuType.create(LatheMenu::new));

    public static final RegistryObject<MenuType<WiremillMenu>> WIREMILL_MENU =
        MENU_TYPES.register("wiremill_menu", () -> IForgeMenuType.create(WiremillMenu::new));

    public static final RegistryObject<MenuType<PressMenu>> PRESS_MENU =
        MENU_TYPES.register("press_menu", () -> IForgeMenuType.create(PressMenu::new));

    public static final RegistryObject<MenuType<SolidBurnerMenu>> SOLID_BURNER_MENU =
        MENU_TYPES.register("solid_burner_menu", () -> IForgeMenuType.create(SolidBurnerMenu::new));
}

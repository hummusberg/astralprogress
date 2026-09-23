package com.humusberg.astralprogress.creativetabs;

import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.fluid.ModFluids;
import com.humusberg.astralprogress.item.ModItems;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import com.humusberg.astralprogress.util.ItemUtil;
import net.minecraft.world.item.ItemStack;

@Mod.EventBusSubscriber(modid = AstralProgress.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = 
            DeferredRegister.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, AstralProgress.MODID);
    public static final RegistryObject<CreativeModeTab> MATERIALS_TAB = CREATIVE_MODE_TABS.register("materials_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.literal("Astral Materials"))
                    .icon(() -> new ItemStack(ItemUtil.getItem("fluorite_gem")))
                    .build());
    public static final RegistryObject<CreativeModeTab> MACHINES_TAB = CREATIVE_MODE_TABS.register("machines_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.literal("Astral Machines"))
                    .icon(() -> new ItemStack(ItemUtil.getBlock("lathe")))
                    .build());
    public static final RegistryObject<CreativeModeTab> TOOLS_TAB = CREATIVE_MODE_TABS.register("tools_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.literal("Astral Tools"))
                    .icon(() -> new ItemStack(ItemUtil.getItem("reinforced_hammer")))
                    .build());
    public static final RegistryObject<CreativeModeTab> BUCKETS_TAB = CREATIVE_MODE_TABS.register("buckets_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.literal("Astral Buckets"))
                    .icon(() -> new ItemStack(ItemUtil.getItem("red_acid_bucket")))
                    .build());
    public static String materials[] = {
        "exhaust_pipe",
        "turbine_blade",
        "polyethylene_pellets",
        "polyethylene_sheet",
        "polyethylene_foil",
        "rotor",
        "stator",
        "alternator",
        "motor",
        "insulated_paper",
        "copper_wire",
        "copper_coil",
        "servo",
        "solenoid",
        "tier_0_capacitor",
        "tier_0_resistor",
        "tier_0_insulated_wire",
        "logic_board",
        "tier_0_circuit",
        "status_light",
        "tier_1_capacitor",
        "tier_1_resistor",
        "tier_1_transistor",
        "tier_1_circuit_stencil",
        "tier_1_insulated_wire",
        "tier_1_circuit",
        "silicon",
        "crushed_dandelion",
        "raw_rubber",
        "rubber",
        "rubber_sheet",
        "phenolic_board",
        "copper_clad_board",
        "etched_pcb"
    };
    public static String material[] = ModItems.registers(ModItems.ITEMS,ModItems.BLOCKS, ModFluids.FLUIDS);
    public static String type[] = {
        "raw",
        "ingot",
        "gem",
        "dust",
        "impure_dust",
        "nugget",
        "block",
        "ore",
        "plate",
        "rod",
        "foil",
        "gear"
    };
    public static String machines[] = {
        "basic_battery",
        "press",
        "lathe",
        "solid_burner",
        "kiln_hearth",
        "kiln_chimney",
        "turbine_base",
        "turbine_body",
        "turbine_head",
        "machine_casing",
        "chemical_reactor",
        "cooler",
        "electrolyzer",
        "refinery",
        "crusher",
        "compressor",
        "isotopic_centrifuge",
        "chemical_infuser",
        "fermentation_vat",
        "distillation_tower",
        "steam_cracker",
        "hydro_cracker",
        "precision_saw"
    };
    public static String tools[] = {
        "hammer",
        "reinforced_hammer",
        "wire_cutters",
        "astral_wrench",
        "weapon_stock",
        "grappling_hook",
        "magnetic_accelerator"
    };
    public static String buckets[] = {
        "red_acid_bucket"
    };
    @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == MATERIALS_TAB.getKey()) {
            for (int i1 = 0; i1 < material.length; i1++) {
                for (int i2 = 0; i2 < type.length; i2++) {
                    Item item = ItemUtil.getItem(material[i1]+"_"+type[i2]);
                    if (item != Items.AIR) {
                        event.accept(item);
                    }
                }
            }
            for (int i3 = 0; i3 < materials.length; i3++) {
                Item item = ItemUtil.getItem(materials[i3]);
                if (item != Items.AIR) {
                    event.accept(item);
                }
            }
        }
        if (event.getTabKey() == TOOLS_TAB.getKey()) {
            for (int i = 0; i < tools.length; i++) {
                Item item = ItemUtil.getItem(tools[i]);
                if (item != Items.AIR) {
                    event.accept(item);
                }
            }
        }
        if (event.getTabKey() == MACHINES_TAB.getKey()) {
            for (int i = 0; i < machines.length; i++) {
                Item item = ItemUtil.getItem(machines[i]);
                if (item != null && item != Items.AIR) {
                    event.accept(item);
                }
            }
        }
        if (event.getTabKey() == BUCKETS_TAB.getKey()) {
            for (int i = 0; i < buckets.length; i++) {
                Item item = ItemUtil.getItem(buckets[i]);
                if (item != null && item != Items.AIR) {
                    event.accept(item);
                }
            }
        }
    }
}
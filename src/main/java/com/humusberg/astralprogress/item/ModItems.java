package com.humusberg.astralprogress.item;

import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.block.TintedBlock;
import com.humusberg.astralprogress.creativetabs.ModCreativeTabs;
import com.humusberg.astralprogress.fluid.ModFluidTypes;
import com.humusberg.astralprogress.fluid.ModFluids;
import com.humusberg.astralprogress.fluid.RegisterFluids;
import com.humusberg.astralprogress.machines.Lathe;
import com.humusberg.astralprogress.machines.Press;
import com.humusberg.astralprogress.machines.SolidBurner;
import com.humusberg.astralprogress.machines.BasicBattery;
import com.humusberg.astralprogress.machines.Cauldron;
import com.humusberg.astralprogress.util.ItemUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, AstralProgress.MODID);
    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(ForgeRegistries.BLOCKS, AstralProgress.MODID);

    public static void registerItem(String namespace, DeferredRegister<Item> ItemRegister) {
        ItemRegister.register(namespace, () -> new Item(new Item.Properties()));
    }
    public static void registerBlock(String namespace, DeferredRegister<Item> ItemRegister, DeferredRegister<Block> BlockRegister) {
        BlockRegister.register(namespace, () -> new TintedBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        ItemRegister.register(namespace ,() -> new BlockItem(ItemUtil.getBlock(namespace), new Item.Properties()));
    }
    public static void registerBlock(String namespace, DeferredRegister<Item> ItemRegister, DeferredRegister<Block> BlockRegister, BlockBehaviour.Properties property) {
        BlockRegister.register(namespace, () -> new TintedBlock(property));
        ItemRegister.register(namespace , () -> new BlockItem(ItemUtil.getBlock(namespace), new Item.Properties()));
    }
    public static String[] registers(DeferredRegister<Item> ItemRegister, DeferredRegister<Block> BlockRegister, DeferredRegister<Fluid> FluidRegister) {
        ItemRegister.register("machine_casing", () -> new Item(new Item.Properties()));
        ItemRegister.register("wrench", () -> new Item(new Item.Properties()));
        ItemRegister.register("magnetic_accelerator", () -> new Gun(5 ,20, 30, ParticleTypes.POOF, 10, new Item.Properties()));
        ItemRegister.register("optical_beam_weapon", () -> new Gun(8 ,0, 40, ParticleTypes.END_ROD, 1000, new Item.Properties()));
        ItemRegister.register("grappling_hook", () -> new GrapplingHook(new Item.Properties()));
        ItemRegister.register("hammer", () -> new CraftingTool(64));
        ItemRegister.register("wire_cutters", () -> new CraftingTool(256));
        ItemRegister.register("reinforced_hammer", () -> new CraftingTool(256));
        
        BlockRegister.register("lathe", () -> new Lathe(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        ItemRegister.register("lathe" , () -> new BlockItem(ItemUtil.getBlock("lathe"), new Item.Properties()));
        BlockRegister.register("press", () -> new Press(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        ItemRegister.register("press" , () -> new BlockItem(ItemUtil.getBlock("press"), new Item.Properties()));
        BlockRegister.register("basic_battery", () -> new BasicBattery(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        ItemRegister.register("basic_battery" , () -> new BlockItem(ItemUtil.getBlock("basic_battery"), new Item.Properties()));
        BlockRegister.register("solid_burner", () -> new SolidBurner(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
        ItemRegister.register("solid_burner" , () -> new BlockItem(ItemUtil.getBlock("solid_burner"), new Item.Properties()));
        BlockRegister.register("cauldron", () -> new Cauldron(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
        ItemRegister.register("cauldron" , () -> new BlockItem(ItemUtil.getBlock("cauldron"), new Item.Properties()));
        BlockRegister.register("kiln_hearth", () -> new TintedBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
        ItemRegister.register("kiln_hearth" , () -> new BlockItem(ItemUtil.getBlock("kiln_hearth"), new Item.Properties()));
        BlockRegister.register("kiln_chimney", () -> new TintedBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
        ItemRegister.register("kiln_chimney" , () -> new BlockItem(ItemUtil.getBlock("kiln_chimney"), new Item.Properties()));
        ItemRegister.register("copper_nugget", () -> new TintedItem(0xC87533, new Item.Properties()));
        ItemRegister.register("magnetic_iron_residiue_bucket", () -> new Item(new Item.Properties().craftRemainder(Items.BUCKET)));

        String materials[] = {
            ModMaterials.registerSoftMaterial("iron", ItemRegister, BlockRegister, FluidRegister, false, false, 0xFFFFFF),
            ModMaterials.registerSoftMaterial("copper", ItemRegister, BlockRegister, FluidRegister, false, false, 0xC86B3C),
            ModMaterials.registerSoftMaterial("magnetic_iron", ItemRegister, BlockRegister, FluidRegister, true, false, 0x806873),
            ModMaterials.registerSoftMaterial("brass", ItemRegister, BlockRegister, FluidRegister, true, false, 0xD4AF37),
            ModMaterials.registerSoftMaterial("steel", ItemRegister, BlockRegister, FluidRegister, true, false, 0x56616D),
            ModMaterials.registerSoftMaterial("vanadium", ItemRegister, BlockRegister, FluidRegister, true, true, 0x4E7180),
            ModMaterials.registerSoftMaterial("aluminium", ItemRegister, BlockRegister, FluidRegister, true, false, 0xB8D4E8),
            ModMaterials.registerHardMaterial("bauxite", ItemRegister, BlockRegister, FluidRegister, false, true, 0xC86B3C),
            ModMaterials.registerSoftMaterial("stainless_steel", ItemRegister, BlockRegister, FluidRegister, true, false, 0xC8D0D6),
            ModMaterials.registerSoftMaterial("chrome", ItemRegister, BlockRegister, FluidRegister, true, false, 0x727C85),
            ModMaterials.registerSoftMaterial("silver", ItemRegister, BlockRegister, FluidRegister, true, false, 0xF2F4F5),
            ModMaterials.registerSoftMaterial("lead", ItemRegister, BlockRegister, FluidRegister, true, true, 0x4A596B),
            ModMaterials.registerHardMaterial("zinc", ItemRegister, BlockRegister, FluidRegister, true, true, 0xA6B8B5),
            ModMaterials.registerHardMaterial("uranium", ItemRegister, BlockRegister, FluidRegister, true, true, 0x00FF00),
            ModMaterials.registerHardMaterial("lithium", ItemRegister, BlockRegister, FluidRegister, false, true, 0xC9DFF2),
            ModMaterials.registerGemMaterial("sodium", ItemRegister, BlockRegister, false, false, 0x7189A8),
            ModMaterials.registerGemMaterial("phosphorus", ItemRegister, BlockRegister, false, false, 0xE87532),
            ModMaterials.registerGemMaterial("fluorite", ItemRegister, BlockRegister, true, true, 0x8B5CC7),
            ModMaterials.registerGemMaterial("sulfur", ItemRegister, BlockRegister, true, true, 0xE6D32F)
        };
        RegisterFluids.registerFluid(ModFluids.FLUIDS, ModFluidTypes.FLUID_TYPES, "red_acid", 0xFFFF0000, false);
        RegisterFluids.registerFluid(ModFluids.FLUIDS, ModFluidTypes.FLUID_TYPES, "aqueous_magnetic_iron", 0xFFFF9999, false);
        RegisterFluids.registerFluid(ModFluids.FLUIDS, ModFluidTypes.FLUID_TYPES, "weak_hydrofluoric_acid", 0xFFE1FFFF, false);        
        for (int i = 0; i < ModCreativeTabs.materials.length; i++) {
            ModItems.registerItem(ModCreativeTabs.materials[i], ItemRegister);
        }
        return materials;
    }
    @SuppressWarnings({ "deprecation", "removal" })
    public static void registerTintedBucket(DeferredRegister<Item> ItemRegister, String name, int color) {
        ItemRegister.register(name + "_bucket", () -> new TintedBucket(color , BuiltInRegistries.FLUID.get(new ResourceLocation(AstralProgress.MODID, name)), new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)));
    }
}

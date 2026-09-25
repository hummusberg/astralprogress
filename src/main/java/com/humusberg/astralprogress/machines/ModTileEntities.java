package com.humusberg.astralprogress.machines;

import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.util.ItemUtil;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AstralProgress.MODID);
        
    public static final RegistryObject<BlockEntityType<LatheTile>> LATHE = TILE_ENTITY_TYPES.register("lathe",
        () -> BlockEntityType.Builder.of(LatheTile::new, ItemUtil.getBlock("lathe")).build(null));

    public static final RegistryObject<BlockEntityType<PressTile>> PRESS = TILE_ENTITY_TYPES.register("press",
        () -> BlockEntityType.Builder.of(PressTile::new, ItemUtil.getBlock("press")).build(null));

    public static final RegistryObject<BlockEntityType<CauldronTile>> CAULDRON = TILE_ENTITY_TYPES.register("cauldron",
        () -> BlockEntityType.Builder.of(CauldronTile::new, ItemUtil.getBlock("cauldron")).build(null));

    public static final RegistryObject<BlockEntityType<SolidBurnerTile>> SOLID_BURNER = TILE_ENTITY_TYPES.register("solid_burner",
        () -> BlockEntityType.Builder.of(SolidBurnerTile::new, ItemUtil.getBlock("solid_burner")).build(null));

    public static final RegistryObject<BlockEntityType<BasicBatteryTile>> BASIC_BATTERY = TILE_ENTITY_TYPES.register("basic_battery",
        () -> BlockEntityType.Builder.of(BasicBatteryTile::new, ItemUtil.getBlock("basic_battery")).build(null));
}

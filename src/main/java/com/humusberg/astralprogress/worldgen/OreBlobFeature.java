package com.humusberg.astralprogress.worldgen;

import com.humusberg.astralprogress.creativetabs.ModCreativeTabs;
import com.humusberg.astralprogress.util.ItemUtil;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;


public class OreBlobFeature extends Feature<NoneFeatureConfiguration> {
    @SuppressWarnings("removal")
	private static ResourceLocation oreTag = new ResourceLocation("forge", "ores");
    public OreBlobFeature(Codec<NoneFeatureConfiguration> pCodec) {
        super(pCodec);
    }
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        WorldGenLevel pLevel = pContext.level();
        RandomSource random = pContext.random();
        if (random.nextInt(4) != 0) {
            return false;
        }
        BlockPos pBlockPos = pContext.origin();
        if (pLevel.getLevel().dimension() == Level.NETHER) {
            if (random.nextInt(3) == 0) {
                spawnBlob(pLevel, pBlockPos, 4, 4, random);
                return true;
            } else {
                return false;
            }
        }
        if (exposedToAir(pLevel, pBlockPos) && random.nextInt(5) != 0) {
            return false;
        }
        if (pLevel.getLevel().dimension() == Level.OVERWORLD) {
            spawnBlob(pLevel, pBlockPos, 4, 4, random);
            return true;
        }
        return false;
    }
    private static boolean exposedToAir(WorldGenLevel pLevel, BlockPos pBlockPos) {
        for (int i = 0; i < Direction.values().length; i++) {
            if (pLevel.getBlockState(pBlockPos.relative(Direction.values()[i])).is(Blocks.AIR)) {
                return true;
            }
        }
        return false;
    }

    private static void spawnBlob(WorldGenLevel pLevel, BlockPos pBlockPos, int radius, int rarity, RandomSource random) {
        Block blobType = ItemUtil.getBlock(ModCreativeTabs.material[random.nextInt(ModCreativeTabs.material.length)] + "_ore");
        if (blobType == Blocks.AIR) {
            spawnBlob(pLevel, pBlockPos, radius, rarity, random);
            return;
        }
        if (blobType == ItemUtil.getBlock("sulfur_ore") && pLevel.getLevel().dimension() == Level.OVERWORLD) {
            spawnBlob(pLevel, pBlockPos, radius, rarity, random);
            return;
        }
        if (pLevel.getLevel().dimension() == Level.NETHER) {
            blobType = ItemUtil.getBlock("sulfur_ore");
        }
        for (int x = radius; x >= -radius; x--) {
            for (int y = radius; y >= -radius; y--) {
                for (int z = radius; z >= -radius; z--) {
                    BlockState targetBlockState = pLevel.getBlockState(pBlockPos.above(y).east(x).south(z));
                    if (targetBlockState.is(Blocks.NETHERRACK) || targetBlockState.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES) || targetBlockState.is(BlockTags.create(oreTag))) {
                        if (random.nextInt(rarity) == 0) {
                            pLevel.setBlock(pBlockPos.above(y).east(x).south(z), blobType.defaultBlockState(), Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE);
                        }
                    }
                }
            }
        }
    }
}

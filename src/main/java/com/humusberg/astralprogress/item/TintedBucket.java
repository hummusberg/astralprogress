package com.humusberg.astralprogress.item;

import com.humusberg.astralprogress.machines.Cauldron;
import com.humusberg.astralprogress.machines.CauldronTile;
import com.humusberg.astralprogress.util.ItemUtil;
import com.humusberg.astralprogress.util.Raycasts;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;

public class TintedBucket extends BucketItem {
    private final int tint;

    @SuppressWarnings("deprecation")
    public TintedBucket(int tint, Fluid pContent, Properties pProperties) {
        super(pContent, pProperties);
        this.tint = tint;
    }

    public int getTint() {
        return tint;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level pLevel = pContext.getLevel();
        Player pPlayer = pContext.getPlayer();
        if (pLevel.isClientSide) {
            return InteractionResult.FAIL;
        }
        BlockHitResult blockHitResult = (BlockHitResult) Raycasts.RaycastBlock(pPlayer, pPlayer.getBlockReach(), false);
        if (pLevel.getBlockState(blockHitResult.getBlockPos()).is(Blocks.CAULDRON)) {
            if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(ItemUtil.getItem("weak_hydrofluoric_acid_bucket"))) {
                setCauldron(pLevel, blockHitResult.getBlockPos(), ItemUtil.getItem("weak_hydrofluoric_acid_bucket"), 1);
                pPlayer.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET));
                return InteractionResult.SUCCESS;
            }
            if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(ItemUtil.getItem("red_acid_bucket"))) {
                setCauldron(pLevel, blockHitResult.getBlockPos(), ItemUtil.getItem("red_acid_bucket"), 2);
                pPlayer.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET));
                return InteractionResult.SUCCESS;
            }
            if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(ItemUtil.getItem("aqueous_magnetic_iron_bucket"))) {
                setCauldron(pLevel, blockHitResult.getBlockPos(), ItemUtil.getItem("aqueous_magnetic_iron_bucket"), 3);
                pPlayer.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BUCKET));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    private static void setCauldron(Level pLevel, BlockPos pPos, Item inputBucket, int value) {
        pLevel.setBlock(pPos, ItemUtil.getBlock("cauldron").defaultBlockState().setValue(Cauldron.BUCKET_STACK, value), 3);
        if (!(pLevel.getBlockEntity(pPos) instanceof CauldronTile tile)) {
            return;
        }
        tile.inputBucket = new ItemStack(inputBucket);    
    }
}

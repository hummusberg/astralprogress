package com.humusberg.astralprogress.item;

import com.humusberg.astralprogress.machines.Cauldron;
import com.humusberg.astralprogress.machines.CauldronTile;
import com.humusberg.astralprogress.util.ItemUtil;
import com.humusberg.astralprogress.util.Raycasts;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;

public class TintedItem extends Item {
    private final int tint;

    public TintedItem(int tint, Properties pProperties) {
        super(pProperties);
        this.tint = tint;
    }

    public int getTint() {
        return tint;
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (pLevel.isClientSide) {
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
        }
        BlockHitResult blockHitResult = (BlockHitResult) Raycasts.RaycastBlock(pPlayer, pPlayer.getBlockReach(), false);
        if (pLevel.getBlockState(blockHitResult.getBlockPos()).is(Blocks.WATER_CAULDRON) && pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(ItemUtil.getItem("fluorite_dust"))) {
            setCauldron(pLevel, blockHitResult.getBlockPos(), ItemUtil.getItem("weak_hydrofluoric_acid_bucket"), 1);
            pPlayer.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
        }
        return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
    }

    private static void setCauldron(Level pLevel, BlockPos pPos, Item inputBucket, int value) {
        pLevel.setBlock(pPos, ItemUtil.getBlock("cauldron").defaultBlockState().setValue(Cauldron.BUCKET_STACK, value), 3);
        if (!(pLevel.getBlockEntity(pPos) instanceof CauldronTile tile)) {
            return;
        }
        tile.inputBucket = new ItemStack(inputBucket);    
    }
}

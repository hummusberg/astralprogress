package com.humusberg.astralprogress.machines;

import com.humusberg.astralprogress.util.ItemUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CauldronTile extends BlockEntity {
    public ItemStack inputBucket = new ItemStack(ItemUtil.getItem("weak_hydrofluoric_acid_bucket"));
    
    public CauldronTile(BlockPos pPos, BlockState pBlockState) {
        super(ModTileEntities.CAULDRON.get(), pPos, pBlockState);
    }
    
    public static ItemStack use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!(pLevel.getBlockEntity(pPos) instanceof CauldronTile tile)) {
            return ItemStack.EMPTY;
        }
        if (tile.inputBucket == new ItemStack(Items.AIR)) {
            tile.inputBucket = ItemStack.EMPTY;
        }
        ItemStack itemInHand = pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
        if ((itemInHand.is(ItemUtil.getItem("weak_hydrofluoric_acid_bucket")) || itemInHand.is(ItemUtil.getItem("red_acid_bucket")) || itemInHand.is(ItemUtil.getItem("aqueous_magnetic_iron_bucket"))) && (tile.inputBucket == ItemStack.EMPTY)) {
            tile.inputBucket = itemInHand.copy();
            itemInHand.shrink(1);
            pPlayer.addItem(new ItemStack(Items.BUCKET));
        }
        if (tile.inputBucket.is(ItemUtil.getItem("weak_hydrofluoric_acid_bucket")) && itemInHand.is(Items.REDSTONE)) {
            itemInHand.shrink(1);
            tile.inputBucket = new ItemStack(ItemUtil.getItem("red_acid_bucket"));
        }
        if (tile.inputBucket.is(ItemUtil.getItem("red_acid_bucket")) && itemInHand.is(ItemUtil.getItem("iron_dust"))) {
            itemInHand.shrink(1);
            tile.inputBucket = new ItemStack(ItemUtil.getItem("aqueous_magnetic_iron_bucket"));
        }
        if (!tile.inputBucket.isEmpty() && itemInHand.is(Items.BUCKET)) {
            itemInHand.shrink(1);
            pPlayer.addItem(tile.inputBucket.copy());
            tile.inputBucket = ItemStack.EMPTY;
        }
        return tile.inputBucket;
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if (pTag.contains("Bucket")) {
            inputBucket = ItemStack.of(pTag.getCompound("Bucket"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("Bucket", inputBucket.serializeNBT());
    }
}

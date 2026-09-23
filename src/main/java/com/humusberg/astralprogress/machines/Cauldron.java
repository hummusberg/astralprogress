package com.humusberg.astralprogress.machines;

import com.humusberg.astralprogress.util.ItemUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class Cauldron extends Block implements EntityBlock {
    public static final IntegerProperty BUCKET_STACK = IntegerProperty.create("bucket", 1 ,3);
    public Cauldron(Properties pProperties) {
        super(pProperties);
        registerDefaultState(this.stateDefinition.any()
            .setValue(BUCKET_STACK, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BUCKET_STACK);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ModTileEntities.CAULDRON.get().create(pPos, pState);
    }

	@Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide || pHand == InteractionHand.OFF_HAND) {
            return InteractionResult.FAIL;
        }
        if (!(pLevel.getBlockEntity(pPos) instanceof CauldronTile)) {
            return InteractionResult.FAIL;
        }
        ItemStack bucket = CauldronTile.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        if (bucket.isEmpty()) {
            pLevel.setBlock(pPos, Blocks.CAULDRON.defaultBlockState(), UPDATE_ALL);
        } else if (bucket.is(ItemUtil.getItem("weak_hydrofluoric_acid_bucket"))) {
            pLevel.setBlock(pPos, pState.setValue(BUCKET_STACK, 1), UPDATE_ALL);
        } else if (bucket.is(ItemUtil.getItem("red_acid_bucket"))) {
            pLevel.setBlock(pPos, pState.setValue(BUCKET_STACK, 2), UPDATE_ALL);
        } else if (bucket.is(ItemUtil.getItem("aqueous_magnetic_iron_bucket"))) {
            pLevel.setBlock(pPos, pState.setValue(BUCKET_STACK, 3), UPDATE_ALL);
        }
        return InteractionResult.SUCCESS;
    }
}
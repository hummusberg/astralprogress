package com.humusberg.astralprogress.machines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BasicBattery extends Block implements EntityBlock {
    public BasicBattery(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ModTileEntities.BASIC_BATTERY.get().create(pPos, pState);
    }
    /* 
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
            BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (pPlayer instanceof ServerPlayer sPlayer) {
            NetworkHooks.openScreen(sPlayer, (MenuProvider) blockEntity, pPos);
        }
        return InteractionResult.SUCCESS;
    }
    */
}

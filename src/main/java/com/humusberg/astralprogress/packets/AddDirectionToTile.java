package com.humusberg.astralprogress.packets;

import java.util.function.Supplier;

import org.apache.commons.lang3.ArrayUtils;

import com.humusberg.astralprogress.machines.DirectionOutputTile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

public class AddDirectionToTile {
    private final BlockPos position;
    private final byte direction;
    
    public AddDirectionToTile(BlockPos position, byte direction) {
        this.position = position;
        this.direction = direction;
    }

    public AddDirectionToTile(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readByte());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.position);
        buffer.writeByte(this.direction);
    }
    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        Level level = player.level();
        BlockEntity blockEntity = level.getBlockEntity(position);
        Direction direction = Direction.from3DDataValue((int)this.direction);
        if (!(blockEntity instanceof DirectionOutputTile tile)) {
            return;
        }
        if (ArrayUtils.contains(tile.getOutputDirection(), direction)) {
            tile.setOutputDirection(ArrayUtils.remove(tile.getOutputDirection(), ArrayUtils.indexOf(tile.getOutputDirection(), direction)));
        } else {
            tile.setOutputDirection(ArrayUtils.add(tile.getOutputDirection(), direction));
        }
    }
}

package com.humusberg.astralprogress.packets;

import com.humusberg.astralprogress.AstralProgress;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    @SuppressWarnings("removal")
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(AstralProgress.MODID, "main"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );
    
    public static void register() {
        INSTANCE.messageBuilder(AddDirectionToTile.class, 1, NetworkDirection.PLAY_TO_SERVER)
            .encoder(AddDirectionToTile::encode)
            .decoder(AddDirectionToTile::new)
            .consumerMainThread(AddDirectionToTile::handle)
            .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }
}

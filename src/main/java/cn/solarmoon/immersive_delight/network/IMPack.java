package cn.solarmoon.immersive_delight.network;

import cn.solarmoon.immersive_delight.ImmersiveDelight;
import cn.solarmoon.immersive_delight.network.serializer.ClientPackSerializer;
import cn.solarmoon.immersive_delight.network.serializer.ServerPackSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import static cn.solarmoon.immersive_delight.ImmersiveDelight.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IMPack {

    public static class ToServer {
        public static SimpleChannel instanceServer;
        private int packetID = 0;

        private int id() {
            return packetID++;
        }

        public void register() {

            SimpleChannel network = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(ImmersiveDelight.MOD_ID, "server"))
                    .networkProtocolVersion(() -> "1")
                    .clientAcceptedVersions(string -> true)
                    .serverAcceptedVersions(string -> true)
                    .simpleChannel();

            instanceServer = network;

            network.messageBuilder(ServerPackSerializer.class, id(), NetworkDirection.PLAY_TO_SERVER)
                    .decoder(ServerPackSerializer::decode)
                    .encoder(ServerPackSerializer::encode)
                    .consumerMainThread(ServerPackSerializer::handle)
                    .add();
        }
    }

    public static class ToClient {
        public static SimpleChannel instanceClient;
        private int packetID = 0;

        private int id() {
            return packetID++;
        }

        public void register() {

            SimpleChannel network = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(ImmersiveDelight.MOD_ID, "client"))
                    .networkProtocolVersion(() -> "1")
                    .clientAcceptedVersions(string -> true)
                    .serverAcceptedVersions(string -> true)
                    .simpleChannel();

            instanceClient = network;

            network.messageBuilder(ClientPackSerializer.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                    .decoder(ClientPackSerializer::decode)
                    .encoder(ClientPackSerializer::encode)
                    .consumerMainThread(ClientPackSerializer::handle)
                    .add();
        }
    }

    //注册数据包
    @SubscribeEvent
    public static void packRegister(final FMLCommonSetupEvent event) {
        ToServer packServer = new IMPack.ToServer();
        packServer.register();
        ToClient packClient = new IMPack.ToClient();
        packClient.register();
    }

}
package zainyest.ethercore.networking.packet;

import net.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import zainyest.ethercore.util.IEntityDataSaver;

public class EtherSyncDataS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        assert client.player != null;
        ((IEntityDataSaver) client.player).getPersistentData().putInt("ether", buf.readInt());
        ((IEntityDataSaver) client.player).getPersistentData().putInt("maxEther", buf.readInt());
    }
}

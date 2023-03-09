package zainyest.ethercore.networking.packet;

import net.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import zainyest.ethercore.util.EtherData;
import zainyest.ethercore.util.IEntityDataSaver;

public class EtherSyncDataS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if (client.player != null) {
            String name = buf.readString();
            int val = buf.readInt();
            int max = buf.readInt();
            switch (name) {
                case "mental_energy" -> {
                    ((IEntityDataSaver) client.player).getPersistentData().putInt(EtherData.MENTAL_ENERGY.getName(), val);
                    ((IEntityDataSaver) client.player).getPersistentData().putInt(EtherData.MENTAL_ENERGY.getName() + "_max", max);
                }
                case "stamina" -> {
                    ((IEntityDataSaver) client.player).getPersistentData().putInt(EtherData.STAMINA.getName(), val);
                    ((IEntityDataSaver) client.player).getPersistentData().putInt(EtherData.STAMINA.getName() + "_max", max);
                }
                case "ether" -> {
                    ((IEntityDataSaver) client.player).getPersistentData().putInt(EtherData.ETHER.getName(), val);
                    ((IEntityDataSaver) client.player).getPersistentData().putInt(EtherData.ETHER.getName() + "_max", max);
                }
            }
        }
    }
}

package zainyest.ethercore.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import zainyest.ethercore.networking.packet.PlayerDataPayload;
import zainyest.ethercore.networking.packet.PlayerDataPayloadReceiver;

public class ModPacketsClient {
    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(PlayerDataPayload.ID, PlayerDataPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(PlayerDataPayload.ID, new PlayerDataPayloadReceiver());
    }
}

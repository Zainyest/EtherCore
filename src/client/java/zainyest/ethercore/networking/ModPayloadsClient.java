package zainyest.ethercore.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import zainyest.ethercore.networking.payload.PlayerDataPayload;
import zainyest.ethercore.networking.payload.PlayerDataPayloadReceiver;

public class ModPayloadsClient {
    public static void registerS2CPayloadReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(PlayerDataPayload.ID, new PlayerDataPayloadReceiver());
    }
}

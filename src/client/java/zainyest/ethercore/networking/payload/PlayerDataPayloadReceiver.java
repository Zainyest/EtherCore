package zainyest.ethercore.networking.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import zainyest.ethercore.EtherCoreClient;

public class PlayerDataPayloadReceiver implements ClientPlayNetworking.PlayPayloadHandler<PlayerDataPayload> {
    @Override
    public void receive(PlayerDataPayload payload, ClientPlayNetworking.Context context) {
        EtherCoreClient.clientPlayerData.persistentData = payload.persistentData();
    }
}

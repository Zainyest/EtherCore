package zainyest.ethercore.networking.payload;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.Tag;
import org.jspecify.annotations.NonNull;
import zainyest.ethercore.EtherCoreClient;

import java.util.Map;

public class PlayerDataPayloadReceiver implements ClientPlayNetworking.PlayPayloadHandler<PlayerDataPayload> {

    /// Iterate entries in payload and write to ClientPlayerData, <br>frequently updated elements should not be wrapped in a compound.
    @Override
    public void receive(PlayerDataPayload payload, ClientPlayNetworking.@NonNull Context context) {
        for (Map.Entry<String, Tag> entry : payload.persistentData().entrySet()) { // TODO: Create a recursive iterator to dynamically find the right compound layer to write each element to, check if its a compound and recur
            EtherCoreClient.clientPlayerData.persistentData.put(entry.getKey(), entry.getValue());
        }
    }
}

package zainyest.ethercore.networking;


import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import zainyest.ethercore.networking.payload.PlayerDataPayload;

public class ModPayloads {
    public static void registerC2SPayloads() {

    }
    public static void registerS2CPayloads() {
        PayloadTypeRegistry.playS2C().register(PlayerDataPayload.ID, PlayerDataPayload.CODEC);
    }
}

package zainyest.ethercore.networking;


import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import zainyest.ethercore.networking.payload.PlayerDataPayload;
import zainyest.ethercore.networking.payload.TimeStopTestPayload;
import zainyest.ethercore.networking.payload.TimeStopTestPayloadReceiver;

public class ModPayloads {
    public static void registerC2SPayloads() {
        PayloadTypeRegistry.playC2S().register(TimeStopTestPayload.ID, TimeStopTestPayload.CODEC);
    }
    public static void registerS2CPayloads() {
        PayloadTypeRegistry.playS2C().register(PlayerDataPayload.ID, PlayerDataPayload.CODEC);
    }
    public static void registerC2SReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(TimeStopTestPayload.ID, new TimeStopTestPayloadReceiver());
    }
}

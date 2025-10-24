package zainyest.ethercore.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import zainyest.ethercore.networking.packet.MeridianMenuPayload;
import zainyest.ethercore.networking.packet.MeridianMenuPayloadReceiver;


public class ModPackets {
    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(MeridianMenuPayload.ID, MeridianMenuPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MeridianMenuPayload.ID, new MeridianMenuPayloadReceiver());
    }
}

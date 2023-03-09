package zainyest.ethercore.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.networking.packet.EtherSyncDataS2CPacket;

public class ModPacketsClient {
    public static final Identifier POOL_SYNC_ID = new Identifier(EtherCore.MOD_ID, "pool_sync");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(POOL_SYNC_ID, EtherSyncDataS2CPacket::receive);
    }
}

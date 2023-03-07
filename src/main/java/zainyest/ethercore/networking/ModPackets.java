package zainyest.ethercore.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.networking.packet.CastEtherBoltC2SPacket;
import zainyest.ethercore.networking.packet.MenuUpdaterC2SPacket;

public class ModPackets {
    public static final Identifier MENU_OPEN_ID = new Identifier(EtherCore.MOD_ID, "open_menu");
    public static final Identifier CAST_ETHER_BOLT_ID = new Identifier(EtherCore.MOD_ID, "cast_ether_bolt");
    public static final Identifier ETHER_SYNC_ID = new Identifier(EtherCore.MOD_ID, "ether_sync");
    public static final Identifier EXAMPLE_ID = new Identifier(EtherCore.MOD_ID, "example");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(MENU_OPEN_ID, MenuUpdaterC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(CAST_ETHER_BOLT_ID, CastEtherBoltC2SPacket::receive);
    }

    public static void registerS2CPackets() {

    }
}

package zainyest.ethercore.util;


import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class EtherData {
    // Instanced pools
    public static EtherPool ETHER = new EtherPool("ether");
    public static EtherPool STAMINA = new EtherPool("stamina");
    public static EtherPool MENTAL_ENERGY = new EtherPool("mental_energy");

    public static void c2s(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //only happens on server
    }
    public static void tickPools(MinecraftServer server) {
        ETHER.tickPool(server);
        STAMINA.tickPool(server);
        MENTAL_ENERGY.tickPool(server);
    }
}

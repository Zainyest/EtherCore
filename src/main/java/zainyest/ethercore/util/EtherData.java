package zainyest.ethercore.util;


import net.minecraft.server.MinecraftServer;

public class EtherData {
    // Instanced pools
    public static EtherPool ETHER = new EtherPool("ether");
    public static EtherPool STAMINA = new EtherPool("stamina");
    public static EtherPool MENTAL_ENERGY = new EtherPool("mental_energy");

    public static void tickPools(MinecraftServer server) {
        ETHER.tickPool(server);
        STAMINA.tickPool(server);
        MENTAL_ENERGY.tickPool(server);
    }
}

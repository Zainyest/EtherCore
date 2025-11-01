package zainyest.ethercore.util;


import net.minecraft.server.MinecraftServer;
import zainyest.ethercore.technique.TechniqueTree;
import zainyest.ethercore.util.init.EtherRegistries;

public class EtherData {
    public static void tickPools(MinecraftServer server) {
        for (EtherPool pool : EtherRegistries.ETHER_POOLS.stream().toList()) {
            pool.tickPool(server);
        }
    }

    public static void updateTrees(MinecraftServer server) {
        for (TechniqueTree tree : EtherRegistries.TECHNIQUE_TREES.stream().toList()) {
            tree.tickTree(server);
        }
    }
}

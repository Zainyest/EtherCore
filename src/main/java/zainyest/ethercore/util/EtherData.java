package zainyest.ethercore.util;


import net.minecraft.server.MinecraftServer;
import zainyest.ethercore.technique.QiGathering;

// TODO Create registry for these instances and abstract
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

    // Instanced technique trees
    public static TechniqueTree TECHNIQUE_TREE = new TechniqueTree("technique_tree", new QiGathering());

    public static void updateTrees(MinecraftServer server) {
        // validate change
        // apply change
        // sync change
        // TODO create updateTree in TechniqueTree

    }
}

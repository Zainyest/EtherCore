package zainyest.ethercore.init;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.etherstat.EtherStat;
import zainyest.ethercore.technique.Technique;
import zainyest.ethercore.technique.TechniqueTree;
import zainyest.ethercore.etherpool.EtherPool;


public class EtherRegistries {
    public static final RegistryKey<Registry<Technique>> TECHNIQUE_KEY = RegistryKey.ofRegistry(Identifier.of(EtherCore.MOD_ID, "technique"));
    public static final Registry<Technique> TECHNIQUES = FabricRegistryBuilder.createSimple(TECHNIQUE_KEY).buildAndRegister();

    public static final RegistryKey<Registry<EtherStat>> ETHER_STAT_KEY = RegistryKey.ofRegistry(Identifier.of(EtherCore.MOD_ID, "ether_stat"));
    public static final Registry<EtherStat> ETHER_STATS = FabricRegistryBuilder.createSimple(ETHER_STAT_KEY).buildAndRegister();

    public static final RegistryKey<Registry<EtherPool>> ETHER_POOL_KEY = RegistryKey.ofRegistry(Identifier.of(EtherCore.MOD_ID, "ether_pool"));
    public static final Registry<EtherPool> ETHER_POOLS = FabricRegistryBuilder.createSimple(ETHER_POOL_KEY).buildAndRegister();

    public static final RegistryKey<Registry<TechniqueTree>> TECHNIQUE_TREE_KEY = RegistryKey.ofRegistry(Identifier.of(EtherCore.MOD_ID, "technique_tree"));
    public static final Registry<TechniqueTree> TECHNIQUE_TREES = FabricRegistryBuilder.createSimple(TECHNIQUE_TREE_KEY).buildAndRegister();

    public static void init() {

    }
}

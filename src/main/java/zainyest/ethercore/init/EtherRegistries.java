package zainyest.ethercore.init;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.etherstat.EtherStat;
import zainyest.ethercore.cultivation.Path;
import zainyest.ethercore.cultivation.Stage;
import zainyest.ethercore.technique.Technique;
import zainyest.ethercore.technique.TechniqueTree;
import zainyest.ethercore.etherpool.EtherPool;


public class EtherRegistries {
    public static final ResourceKey<Registry<Technique>> TECHNIQUE_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "technique"));
    public static final Registry<Technique> TECHNIQUES = FabricRegistryBuilder.createSimple(TECHNIQUE_KEY).buildAndRegister();

    public static final ResourceKey<Registry<EtherStat>> ETHER_STAT_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "ether_stat"));
    public static final Registry<EtherStat> ETHER_STATS = FabricRegistryBuilder.createSimple(ETHER_STAT_KEY).buildAndRegister();

    public static final ResourceKey<Registry<EtherPool>> ETHER_POOL_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "ether_pool"));
    public static final Registry<EtherPool> ETHER_POOLS = FabricRegistryBuilder.createSimple(ETHER_POOL_KEY).buildAndRegister();

    public static final ResourceKey<Registry<TechniqueTree>> TECHNIQUE_TREE_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "technique_tree"));
    public static final Registry<TechniqueTree> TECHNIQUE_TREES = FabricRegistryBuilder.createSimple(TECHNIQUE_TREE_KEY).buildAndRegister();

    public static final ResourceKey<Registry<Path>> PATH_KEY = ResourceKey.createRegistryKey(EtherCore.id("path"));
    public static final Registry<Path> PATHS = FabricRegistryBuilder.createSimple(PATH_KEY).buildAndRegister();

    public static final ResourceKey<Registry<Stage>> STAGE_KEY = ResourceKey.createRegistryKey(EtherCore.id("stage"));
    public static final Registry<Stage> STAGES = FabricRegistryBuilder.createSimple(STAGE_KEY).buildAndRegister();

    public static void init() {

    }
}

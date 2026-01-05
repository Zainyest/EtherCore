package zainyest.ethercore.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.etherpool.EtherPool;

public class EtherPools {
    public static EtherPool ETHER = registerPool("ether", new EtherPool("ether", EtherStats.EMINENCE.name(), EtherStats.INFLUENCE.name(), 10.0D, 0.1D));
    public static EtherPool STAMINA = registerPool("stamina", new EtherPool("stamina", EtherStats.ENDURANCE.name(), EtherStats.RECOVERY.name(), 10.0D, 0.1D));
    public static EtherPool MENTAL_ENERGY = registerPool("mental_energy", new EtherPool("mental_energy", EtherStats.MIND.name(), EtherStats.FOCUS.name(), 10.0D, 0.1D));

    private static <T extends EtherPool> T registerPool(String name, T pool) {
        return Registry.register(EtherRegistries.ETHER_POOLS, Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, name), pool);
    }

    public static void init() {

    }
}

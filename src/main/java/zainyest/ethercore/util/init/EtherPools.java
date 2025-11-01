package zainyest.ethercore.util.init;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.util.EtherPool;

public class EtherPools {
    public static EtherPool ETHER = registerPool("ether", new EtherPool("ether"));
    public static EtherPool STAMINA = registerPool("stamina", new EtherPool("stamina"));
    public static EtherPool MENTAL_ENERGY = registerPool("mental_energy", new EtherPool("mental_energy"));

    private static <T extends EtherPool> T registerPool(String name, T pool) {
        return Registry.register(EtherRegistries.ETHER_POOLS, Identifier.of(EtherCore.MOD_ID, name), pool);
    }

    public static void init() {

    }
}

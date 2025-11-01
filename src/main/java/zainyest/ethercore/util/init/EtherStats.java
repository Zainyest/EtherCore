package zainyest.ethercore.util.init;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.etherstat.EtherStat;

public class EtherStats {
    public static final EtherStat EXAMPLE_STAT = registerStat("example_stat", new EtherStat("example_stat", 10));

    private static <T extends EtherStat> T registerStat(String name, T stat) {
        return Registry.register(EtherRegistries.ETHER_STATS, Identifier.of(EtherCore.MOD_ID, name), stat);
    }

    public static void init() {

    }
}

package zainyest.ethercore.util.init;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.techniquetree.QiGathering;
import zainyest.ethercore.technique.Technique;

public class Techniques {
    public static final QiGathering QI_GATHERING = registerTechnique("qi_gathering", new QiGathering());

    private static <T extends Technique> T registerTechnique(String name, T technique) {
        return Registry.register(EtherRegistries.TECHNIQUES, Identifier.of(EtherCore.MOD_ID, name), technique);
    }

    public static void init() {

    }
}

package zainyest.ethercore.util.init;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.QiGathering;
import zainyest.ethercore.util.Technique;

// TODO is this needed??
public class Techniques {
    public static final QiGathering QI_GATHERING = registerTechnique("qi_gathering", new QiGathering());

    private static <T extends Technique> T registerTechnique(String name, T technique) {
        return Registry.register(Registries.TECHNIQUE, Identifier.of(EtherCore.MOD_ID, name), technique);
    }
}

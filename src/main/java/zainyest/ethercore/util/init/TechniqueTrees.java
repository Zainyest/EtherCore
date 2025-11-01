package zainyest.ethercore.util.init;

import net.minecraft.registry.Registry;
import zainyest.ethercore.technique.TechniqueTree;

public class TechniqueTrees {
    public static final TechniqueTree TECHNIQUE_TREE = registerTree("technique_tree", new TechniqueTree("technique_tree", Techniques.QI_GATHERING));

    private static <T extends TechniqueTree> T registerTree(String name, T tree) {
        return Registry.register(EtherRegistries.TECHNIQUE_TREES, name, tree);
    }

    public static void init() {

    }
}

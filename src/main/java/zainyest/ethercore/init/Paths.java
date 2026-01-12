package zainyest.ethercore.init;

import net.minecraft.core.Registry;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.cultivation.Path;

public class Paths {

    public static final Path ETHER = registerPath("ether", new Path("ether", Stages.ENERGY_GATHERING, EtherCore.id("icon.png")));
    public static final Path MENTAL = registerPath("mental", new Path("mental", Stages.MIND_SHARPENING, EtherCore.id("icon.png")));
    public static final Path BODY = registerPath("body", new Path("body", Stages.BODY_TEMPERING, EtherCore.id("icon.png")));

    private static <T extends Path> T registerPath(String name, T path) {
        return Registry.register(EtherRegistries.PATHS, EtherCore.id(name), path);
    }

    public static void init() {}
}

package zainyest.ethercore.init;

import net.minecraft.core.Registry;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.cultivation.Stage;
import zainyest.ethercore.cultivation.body.BodyTempering;
import zainyest.ethercore.cultivation.ether.CoreFormation;
import zainyest.ethercore.cultivation.ether.EnergyGathering;
import zainyest.ethercore.cultivation.mental.MindSharpening;

public class Stages {

    // Ether
    public static Stage ENERGY_GATHERING = registerStage("energy_gathering", new EnergyGathering());
    public static Stage CORE_FORMATION = registerStage("core_formation", new CoreFormation());
    // Mental
    public static Stage MIND_SHARPENING = registerStage("mind_sharpening", new MindSharpening());
    // Body
    public static Stage BODY_TEMPERING = registerStage("body_tempering", new BodyTempering());

    private static <T extends Stage> T registerStage(String name, T stage) {
        return Registry.register(EtherRegistries.STAGES, EtherCore.id(name), stage);
    }

    public static void init() {}
}

package zainyest.ethercore.init;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.techniquetree.*;
import zainyest.ethercore.technique.Technique;

public class Techniques {
    public static final QiGathering QI_GATHERING = registerTechnique("qi_gathering", new QiGathering());
    public static final MentalAcuity MENTAL_ACUITY = registerTechnique("mental_acuity", new MentalAcuity());
    public static final BodyFortification BODY_FORTIFICATION = registerTechnique("body_fortification", new BodyFortification());
    public static final TimeStop TIME_STOP = registerTechnique("time_stop", new TimeStop());
    public static final TemporalImmunity TEMPORAL_IMMUNITY = registerTechnique("temporal_immunity", new TemporalImmunity());

    private static <T extends Technique> T registerTechnique(String name, T technique) {
        return Registry.register(EtherRegistries.TECHNIQUES, Identifier.of(EtherCore.MOD_ID, name), technique);
    }

    public static void init() {

    }
}

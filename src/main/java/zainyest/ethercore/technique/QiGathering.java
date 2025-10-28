package zainyest.ethercore.technique;

import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.util.PassiveTechnique;
import zainyest.ethercore.util.Technique;

public class QiGathering extends PassiveTechnique {
    public QiGathering(String name, Technique[] parents, Technique[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    public QiGathering() {
        super("qi_gathering", new Technique[0], new Technique[0], "Passive", "The first step", Identifier.of(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void tick() {

    }
}

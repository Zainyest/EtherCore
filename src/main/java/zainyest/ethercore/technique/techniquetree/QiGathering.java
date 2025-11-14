package zainyest.ethercore.technique.techniquetree;

import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;

public class QiGathering extends PassiveTechnique {
    public QiGathering(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    public QiGathering() {
        super("qi_gathering", new Identifier[0], new Identifier[]{Identifier.of(EtherCore.MOD_ID, "mental_acuity"), Identifier.of(EtherCore.MOD_ID, "body_fortification")}, "passive", "ethercore.text.qi_gathering.description", Identifier.of(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void tick() {

    }
}

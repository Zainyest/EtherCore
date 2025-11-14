package zainyest.ethercore.technique.techniquetree;

import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;
import zainyest.ethercore.technique.Technique;
import zainyest.ethercore.util.init.EtherRegistries;
import zainyest.ethercore.util.init.Techniques;

public class BodyFortification extends PassiveTechnique {
    public BodyFortification(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    public BodyFortification() {
        super("body_fortification", new Identifier[]{Identifier.of(EtherCore.MOD_ID, "qi_gathering")}, new Identifier[0], "passive", "ethercore.text.body_fortification.description", Identifier.of(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void tick() {

    }
}

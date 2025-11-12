package zainyest.ethercore.technique.techniquetree;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;
import zainyest.ethercore.technique.Technique;
import zainyest.ethercore.util.init.EtherRegistries;
import zainyest.ethercore.util.init.Techniques;

public class MentalAcuity extends PassiveTechnique {
    public MentalAcuity(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    public MentalAcuity() {
        super("mental_acuity", new Identifier[]{Identifier.of(EtherCore.MOD_ID, "qi_gathering")}, new Identifier[0], "passive", "brain good", Identifier.of(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void tick() {

    }
}

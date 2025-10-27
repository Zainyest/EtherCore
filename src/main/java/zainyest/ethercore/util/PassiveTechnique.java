package zainyest.ethercore.util;

import net.minecraft.util.Identifier;

public abstract class PassiveTechnique extends Technique {

    public PassiveTechnique(String name, Technique[] parents, Technique[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    @Override
    public void manifest() {

    }

    /// From registry will be called by PlayerTickHandler
    public abstract void tick();
}

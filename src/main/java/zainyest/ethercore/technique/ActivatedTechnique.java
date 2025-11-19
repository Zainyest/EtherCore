package zainyest.ethercore.technique;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public abstract class ActivatedTechnique extends Technique{
    public ActivatedTechnique(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    @Override
    public void manifest() {

    }

    public abstract void manifest(MinecraftServer server);
}

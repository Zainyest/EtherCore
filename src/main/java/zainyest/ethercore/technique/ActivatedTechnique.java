package zainyest.ethercore.technique;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public abstract class ActivatedTechnique extends Technique{
    public ActivatedTechnique(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    public abstract void manifest(MinecraftServer server, ServerPlayerEntity serverPlayer);

    /// Should only be called if this.isActive() == true
    @Override
    public abstract void tick(MinecraftServer server, ServerPlayerEntity serverPlayer);

}

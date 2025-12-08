package zainyest.ethercore.technique;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public abstract class PassiveTechnique extends Technique implements TickingTechnique {

    public PassiveTechnique(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    /// From registry will be called by PlayerTickHandler
    public abstract void tick(MinecraftServer server, ServerPlayerEntity serverPlayer, List<String> slatedForRemoval);
}

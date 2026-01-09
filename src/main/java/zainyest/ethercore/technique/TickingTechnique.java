package zainyest.ethercore.technique;

import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.UUID;

public interface TickingTechnique {
    /**
     * From registry will be called by PlayerTickHandler. <br>
     * Actualize the effects of the technique.<br>
     * For passive should be on-tick effects, <br>
     * for active should use a cost and manifest an effect in the world.
     */
    void tick(MinecraftServer server, UUID uuid, List<String> slatedForRemoval);
}

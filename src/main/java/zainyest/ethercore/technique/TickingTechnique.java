package zainyest.ethercore.technique;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public interface TickingTechnique {
    /**
     * From registry will be called by PlayerTickHandler. <br>
     * Actualize the effects of the technique.<br>
     * For passive should be on-tick effects, <br>
     * for active should use a cost and manifest an effect in the world.
     */
    void tick(MinecraftServer server, ServerPlayer serverPlayer, List<String> slatedForRemoval);
}

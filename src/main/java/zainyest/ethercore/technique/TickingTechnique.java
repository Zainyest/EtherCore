package zainyest.ethercore.technique;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

public interface TickingTechnique {
    /**
     * Actualize the effects of the technique.
     * For passive should be on-tick or entity modifier,
     * for active should use a cost and manifest an effect in the world.
     */
    void tick(MinecraftServer server, ServerPlayerEntity serverPlayer);
}

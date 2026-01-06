package zainyest.ethercore.technique;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.Identifier;

public abstract class ActivatedTechnique extends Technique {
    public ActivatedTechnique(String name, Identifier[] parents, Identifier[] children, Identifier icon) {
        super(name, parents, children, icon);
    }

    public abstract void manifest(MinecraftServer server, ServerPlayer serverPlayer);
}

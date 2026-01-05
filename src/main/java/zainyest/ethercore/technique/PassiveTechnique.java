package zainyest.ethercore.technique;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.Identifier;

import java.util.List;

public abstract class PassiveTechnique extends Technique {

    public PassiveTechnique(String name, Identifier[] parents, Identifier[] children, Identifier icon) {
        super(name, parents, children, icon);
    }

    /// Called when the technique is learned or applied by a toggle
    public abstract void apply(MinecraftServer server, ServerPlayer serverPlayer);

    /// Called when the technique is unlearned or removed by a toggle
    public abstract void remove(MinecraftServer server, ServerPlayer serverPlayer);
}

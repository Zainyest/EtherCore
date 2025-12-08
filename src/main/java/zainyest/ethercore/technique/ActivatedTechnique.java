package zainyest.ethercore.technique;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public abstract class ActivatedTechnique extends Technique implements TickingTechnique{
    public ActivatedTechnique(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    public abstract void manifest(MinecraftServer server, ServerPlayerEntity serverPlayer);

    /// Should only be called if this.isActive() == true
    public abstract void tick(MinecraftServer server, ServerPlayerEntity serverPlayer, List<String> slatedForRemoval);

}

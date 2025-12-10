package zainyest.ethercore.technique;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public abstract class ActivatedTechnique extends Technique {
    public ActivatedTechnique(String name, Identifier[] parents, Identifier[] children, Identifier icon) {
        super(name, parents, children, icon);
    }

    public abstract void manifest(MinecraftServer server, ServerPlayerEntity serverPlayer);
}

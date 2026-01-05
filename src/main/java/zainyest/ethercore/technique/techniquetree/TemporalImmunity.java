package zainyest.ethercore.technique.techniquetree;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;

import java.util.List;

public class TemporalImmunity extends PassiveTechnique {
    public TemporalImmunity(String name, Identifier[] parents, Identifier[] children, Identifier icon) {
        super(name, parents, children, icon);
    }

    public TemporalImmunity() {
        super("temporal_immunity",
                new Identifier[]{Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "time_manipulation")},
                new Identifier[]{Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "time_stop")},
                Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "icon.png"));

    }

    @Override
    public void apply(MinecraftServer server, ServerPlayer serverPlayer) {

    }

    @Override
    public void remove(MinecraftServer server, ServerPlayer serverPlayer) {

    }
}

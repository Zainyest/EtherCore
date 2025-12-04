package zainyest.ethercore.technique.techniquetree;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;

public class TemporalImmunity extends PassiveTechnique {
    public TemporalImmunity(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    public TemporalImmunity() {
        super("temporal_immunity",
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "mental_acuity")},
                new Identifier[0],
                "passive",
                "ethercore.text.temporal_immunity.description",
                Identifier.of(EtherCore.MOD_ID, "icon.png"));

    }

    @Override
    public void tick(MinecraftServer server, ServerPlayerEntity serverPlayer) {

    }
}

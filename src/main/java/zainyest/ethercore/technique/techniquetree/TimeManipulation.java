package zainyest.ethercore.technique.techniquetree;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;

import java.util.List;

public class TimeManipulation extends PassiveTechnique {
    public TimeManipulation(String name, Identifier[] parents, Identifier[] children, Identifier icon) {
        super(name, parents, children, icon);
    }

    public TimeManipulation() {
        super("time_manipulation",
                new Identifier[]{Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "mental_acuity")},
                new Identifier[]{Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "temporal_immunity"), Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "create_time_frozen_armament")},
                Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void apply(MinecraftServer server, ServerPlayer serverPlayer) {

    }

    @Override
    public void remove(MinecraftServer server, ServerPlayer serverPlayer) {

    }
}

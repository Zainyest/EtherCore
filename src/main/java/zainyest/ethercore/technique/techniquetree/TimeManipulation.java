package zainyest.ethercore.technique.techniquetree;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;

import java.util.List;

public class TimeManipulation extends PassiveTechnique {
    public TimeManipulation(String name, Identifier[] parents, Identifier[] children, Identifier icon) {
        super(name, parents, children, icon);
    }

    public TimeManipulation() {
        super("time_manipulation",
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "mental_acuity")},
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "temporal_immunity"), Identifier.of(EtherCore.MOD_ID, "create_time_frozen_armament")},
                Identifier.of(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void apply(MinecraftServer server, ServerPlayerEntity serverPlayer) {

    }

    @Override
    public void remove(MinecraftServer server, ServerPlayerEntity serverPlayer) {

    }
}

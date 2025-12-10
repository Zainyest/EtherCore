package zainyest.ethercore.technique.techniquetree;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;

import java.util.List;

public class QiGathering extends PassiveTechnique {
    public QiGathering(String name, Identifier[] parents, Identifier[] children, Identifier icon) {
        super(name, parents, children, icon);
    }

    public QiGathering() {
        super("qi_gathering",
                new Identifier[0],
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "mental_acuity"), Identifier.of(EtherCore.MOD_ID, "body_fortification"), EtherCore.id("ether_manipulation")},
                Identifier.of(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void apply(MinecraftServer server, ServerPlayerEntity serverPlayer) {

    }

    @Override
    public void remove(MinecraftServer server, ServerPlayerEntity serverPlayer) {

    }
}

package zainyest.ethercore.technique.techniquetree;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;

public class QiGathering extends PassiveTechnique {
    public QiGathering() {
        super("qi_gathering",
                new Identifier[0],
                new Identifier[]{Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "mental_acuity"), Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "body_fortification"), EtherCore.id("ether_manipulation")},
                Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void apply(MinecraftServer server, ServerPlayer serverPlayer) {

    }

    @Override
    public void remove(MinecraftServer server, ServerPlayer serverPlayer) {

    }
}

package zainyest.ethercore.technique.techniquetree;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;

public class EtherWeaving extends PassiveTechnique {
    public EtherWeaving() {
        super("ether_weaving",
                new Identifier[]{Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "ether_manipulation")},
                new Identifier[]{Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "time_manipulation")},
                Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void apply(MinecraftServer server, ServerPlayer serverPlayer) {

    }

    @Override
    public void remove(MinecraftServer server, ServerPlayer serverPlayer) {

    }
}

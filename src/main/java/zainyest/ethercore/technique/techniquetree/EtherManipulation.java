package zainyest.ethercore.technique.techniquetree;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;

import java.util.List;

public class EtherManipulation extends PassiveTechnique {
    public EtherManipulation(String name, Identifier[] parents, Identifier[] children, Identifier icon) {
        super(name, parents, children, icon);
    }

    public EtherManipulation() {
        super("ether_manipulation",
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "qi_gathering")},
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "ether_weaving")},
                Identifier.of(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void apply(MinecraftServer server, ServerPlayerEntity serverPlayer) {

    }

    @Override
    public void remove(MinecraftServer server, ServerPlayerEntity serverPlayer) {

    }
}

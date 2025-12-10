package zainyest.ethercore.technique.techniquetree;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;

import java.util.List;

public class BodyFortification extends PassiveTechnique {
    public BodyFortification(String name, Identifier[] parents, Identifier[] children, Identifier icon) {
        super(name, parents, children, icon);
    }

    public BodyFortification() {
        super("body_fortification",
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "qi_gathering")},
                new Identifier[0],
                Identifier.of(EtherCore.MOD_ID, "textures/gui/technique/heart.png"));
    }

    @Override
    public void apply(MinecraftServer server, ServerPlayerEntity serverPlayer) {

    }

    @Override
    public void remove(MinecraftServer server, ServerPlayerEntity serverPlayer) {

    }
}

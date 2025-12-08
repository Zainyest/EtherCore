package zainyest.ethercore.technique.techniquetree;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;

import java.util.List;

public class BodyFortification extends PassiveTechnique {
    public BodyFortification(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    public BodyFortification() {
        super("body_fortification",
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "qi_gathering")},
                new Identifier[0], "passive", "ethercore.text.body_fortification.description",
                Identifier.of(EtherCore.MOD_ID, "textures/gui/technique/heart.png"));
    }

    @Override
    public void tick(MinecraftServer server, ServerPlayerEntity serverPlayer, List<String> slatedForRemoval) {

    }
}

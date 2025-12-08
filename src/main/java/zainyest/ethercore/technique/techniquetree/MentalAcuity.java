package zainyest.ethercore.technique.techniquetree;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;

import java.util.List;

public class MentalAcuity extends PassiveTechnique {
    public MentalAcuity(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    public MentalAcuity() {
        super("mental_acuity",
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "qi_gathering")},
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "time_stop"), Identifier.of(EtherCore.MOD_ID, "temporal_immunity")},
                "passive",
                "ethercore.text.mental_acuity.description",
                Identifier.of(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void tick(MinecraftServer server, ServerPlayerEntity serverPlayer, List<String> slatedForRemoval) {

    }
}

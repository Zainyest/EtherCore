package zainyest.ethercore.networking.payload;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.util.StateSaverAndLoader;
import zainyest.ethercore.init.EtherRegistries;
import zainyest.ethercore.init.TechniqueTrees;

import java.util.Objects;

public class LearnTechniquePayloadReceiver implements ServerPlayNetworking.PlayPayloadHandler<LearnTechniquePayload>{
    @Override
    public void receive(LearnTechniquePayload payload, ServerPlayNetworking.Context context) {
        TechniqueTrees.TECHNIQUE_TREE.setTechnique(context.player(),
                StateSaverAndLoader.getPlayerState(context.player()),
                Objects.requireNonNull(EtherRegistries.TECHNIQUES.getValue(Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, payload.technique().asString().orElseThrow()))));
    }
}

package zainyest.ethercore.networking.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.util.StateSaverAndLoader;
import zainyest.ethercore.util.init.EtherRegistries;
import zainyest.ethercore.util.init.TechniqueTrees;

public class LearnTechniquePayloadReceiver implements ServerPlayNetworking.PlayPayloadHandler<LearnTechniquePayload>{
    @Override
    public void receive(LearnTechniquePayload payload, ServerPlayNetworking.Context context) {
        TechniqueTrees.TECHNIQUE_TREE.setTechnique(context.player(),
                StateSaverAndLoader.getPlayerState(context.player()),
                EtherRegistries.TECHNIQUES.get(Identifier.of(EtherCore.MOD_ID, payload.technique().asString().orElseThrow())));
    }
}

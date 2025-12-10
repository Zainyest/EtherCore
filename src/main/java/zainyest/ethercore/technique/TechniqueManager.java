package zainyest.ethercore.technique;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.init.EtherRegistries;

public class TechniqueManager {
    public static void handleComposedTechniqueRequest(MinecraftServer server, ServerPlayerEntity serverPlayer, ComposedTechnique composedTechnique) {
        for (String name : composedTechnique.techniqueComponents()) {
            manifestTechnique(server, serverPlayer, name);
        }
    }

    public static void manifestTechnique(MinecraftServer server, ServerPlayerEntity serverPlayer, String name) {
        if (!EtherRegistries.TECHNIQUES.containsId(EtherCore.id(name))) {
            EtherCore.LOGGER.warn("Invalid name for manifestTechnique request: {}", name);
            return;
        }
        if (EtherRegistries.TECHNIQUES.get(EtherCore.id(name)) instanceof ActivatedTechnique) {
            ((ActivatedTechnique) EtherRegistries.TECHNIQUES.get(EtherCore.id(name))).manifest(server, serverPlayer);
        } else {
            EtherCore.LOGGER.warn("Invalid Technique for manifestTechnique request: {}", EtherRegistries.TECHNIQUES.get(EtherCore.id(name)));
        }
    }
}

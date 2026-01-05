package zainyest.ethercore.technique;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.init.EtherRegistries;

public class TechniqueManager {
    public static void handleComposedTechniqueRequest(MinecraftServer server, ServerPlayer serverPlayer, ComposedTechnique composedTechnique) {
        for (String name : composedTechnique.techniqueComponents()) {
            manifestTechnique(server, serverPlayer, name);
        }
    }

    public static void manifestTechnique(MinecraftServer server, ServerPlayer serverPlayer, String name) {
        if (!EtherRegistries.TECHNIQUES.containsKey(EtherCore.id(name))) {
            EtherCore.LOGGER.warn("Invalid name for manifestTechnique request: {}", name);
            return;
        }
        if (EtherRegistries.TECHNIQUES.getValue(EtherCore.id(name)) instanceof ActivatedTechnique) {
            ((ActivatedTechnique) EtherRegistries.TECHNIQUES.getValue(EtherCore.id(name))).manifest(server, serverPlayer);
        } else {
            EtherCore.LOGGER.warn("Invalid Technique for manifestTechnique request: {}", EtherRegistries.TECHNIQUES.getValue(EtherCore.id(name)));
        }
    }
}

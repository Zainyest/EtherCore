package zainyest.ethercore.technique;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.init.EtherRegistries;
import zainyest.ethercore.util.StateSaverAndLoader;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TickingTechniquesManager {

    public static final String ACTIVE_SINGLETON_TECHNIQUES_KEY = "active_singleton_techniques";
    public static final String NAME_KEY = "name";
    public static final String UUID_KEY = "uuid";

    public static void addSingletonTechniqueInstance(MinecraftServer server, ServerPlayer serverPlayer, Technique technique) {
        CompoundTag worldlyData = StateSaverAndLoader.getWorldlyData(server);

        CompoundTag activeTechniques =  worldlyData.getCompoundOrEmpty(ACTIVE_SINGLETON_TECHNIQUES_KEY);

        CompoundTag out = new CompoundTag();
        out.putString(NAME_KEY, technique.getName());
        out.putString(UUID_KEY, serverPlayer.getStringUUID());

        activeTechniques.put(technique.getName() + "." + serverPlayer.getStringUUID(), out);
        worldlyData.put(ACTIVE_SINGLETON_TECHNIQUES_KEY, activeTechniques);
    }

    public static void tickSingletonTechniques(MinecraftServer server) {
        CompoundTag worldlyData = StateSaverAndLoader.getWorldlyData(server);
        CompoundTag activeTechniques =  worldlyData.getCompoundOrEmpty(ACTIVE_SINGLETON_TECHNIQUES_KEY);
        List<String> slatedForRemoval = new LinkedList<>();

        for (Map.Entry<String, Tag> entry : activeTechniques.entrySet()) {
            String techniqueName = entry.getValue().asCompound().get().getString(NAME_KEY).orElseThrow();
            UUID uuid = UUID.fromString(entry.getValue().asCompound().get().getString(UUID_KEY).orElseThrow());
            if (EtherRegistries.TECHNIQUES.containsKey(Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, techniqueName))) {
                ((TickingTechnique) EtherRegistries.TECHNIQUES.getValue(Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, techniqueName))).tick(server, server.getPlayerList().getPlayer(uuid), slatedForRemoval);
            } else {
                EtherCore.LOGGER.atError().log("NO TECHNIQUE FOUND: " + techniqueName);
            }
        }

        for (String entry : slatedForRemoval) {
            activeTechniques.remove(entry);
        }
        worldlyData.put(ACTIVE_SINGLETON_TECHNIQUES_KEY, activeTechniques);
    }
}

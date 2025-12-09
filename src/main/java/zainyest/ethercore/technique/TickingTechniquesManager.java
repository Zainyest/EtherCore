package zainyest.ethercore.technique;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.init.EtherRegistries;
import zainyest.ethercore.util.StateSaverAndLoader;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TickingTechniquesManager {

    public static final String ACTIVE_TECHNIQUES_KEY = "active_techniques";
    public static final String NAME_KEY = "name";
    public static final String UUID_KEY = "uuid";

    public static void addTechniqueInstance(MinecraftServer server, ServerPlayerEntity serverPlayer, Technique technique) {
        NbtCompound worldlyData = StateSaverAndLoader.getWorldlyData(server);

        NbtCompound activeTechniques =  worldlyData.getCompoundOrEmpty(ACTIVE_TECHNIQUES_KEY);

        NbtCompound out = new NbtCompound();
        out.putString(NAME_KEY, technique.getName());
        out.putString(UUID_KEY, serverPlayer.getUuidAsString());

        activeTechniques.put(technique.getName() + "." + serverPlayer.getUuidAsString(), out);
        worldlyData.put(ACTIVE_TECHNIQUES_KEY, activeTechniques);
    }

    public static void tickTechniques(MinecraftServer server) {
        NbtCompound worldlyData = StateSaverAndLoader.getWorldlyData(server);
        NbtCompound activeTechniques =  worldlyData.getCompoundOrEmpty(ACTIVE_TECHNIQUES_KEY);
        List<String> slatedForRemoval = new LinkedList<>();

        for (Map.Entry<String, NbtElement> entry : activeTechniques.entrySet()) {
            String techniqueName = entry.getValue().asCompound().get().getString(NAME_KEY).orElseThrow();
            UUID uuid = UUID.fromString(entry.getValue().asCompound().get().getString(UUID_KEY).orElseThrow());
            if (EtherRegistries.TECHNIQUES.containsId(Identifier.of(EtherCore.MOD_ID, techniqueName))) {
                ((TickingTechnique) EtherRegistries.TECHNIQUES.get(Identifier.of(EtherCore.MOD_ID, techniqueName))).tick(server, server.getPlayerManager().getPlayer(uuid), slatedForRemoval);
            } else {
                EtherCore.LOGGER.atError().log("NO TECHNIQUE FOUND: " + Identifier.of(EtherCore.MOD_ID, techniqueName));
            }
        }

        for (String entry : slatedForRemoval) {
            activeTechniques.remove(entry);
        }
        worldlyData.put(ACTIVE_TECHNIQUES_KEY, activeTechniques);
    }
}

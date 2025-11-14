package zainyest.ethercore.util;


import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import zainyest.ethercore.etherstat.PlayerEtherStats;
import zainyest.ethercore.networking.payload.PlayerDataPayload;
import zainyest.ethercore.technique.TechniqueTree;
import zainyest.ethercore.util.init.EtherRegistries;

public class EtherData {
    public static void tickPools(MinecraftServer server) {
        for (EtherPool pool : EtherRegistries.ETHER_POOLS) {
            pool.tickPool(server);
        }
    }

    public static void updateTrees(MinecraftServer server) {
        for (TechniqueTree tree : EtherRegistries.TECHNIQUE_TREES) {
            tree.tickTree(server);
        }
    }

    public static void updatePlayerStats(MinecraftServer server) {
        PlayerEtherStats.updateStats(server);
    }

    public static void sendPlayerDataPayloads(MinecraftServer server) {
        for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
            PlayerData playerData = StateSaverAndLoader.getPlayerState(serverPlayer);
            if (playerData.isDirty()) {
                playerData.unMarkDirty();
                ServerPlayNetworking.send(serverPlayer, new PlayerDataPayload(playerData.getPersistentData()));
            }
        }
    }
}

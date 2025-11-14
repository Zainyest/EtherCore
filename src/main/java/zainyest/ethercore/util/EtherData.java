package zainyest.ethercore.util;


import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import zainyest.ethercore.etherstat.PlayerEtherStats;
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
        for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
            PlayerData playerData = StateSaverAndLoader.getPlayerState(serverPlayer);
            playerData.persistentData.put(PlayerEtherStats.PLAYER_ETHER_STATS_KEY, PlayerEtherStats.getOrCreateNbt(playerData));
        }
    }
}

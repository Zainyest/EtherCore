package zainyest.ethercore.util;


import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import zainyest.ethercore.etherpool.EtherPool;
import zainyest.ethercore.etherstat.PlayerEtherStats;
import zainyest.ethercore.networking.payload.PlayerDataPayload;
import zainyest.ethercore.technique.TechniqueTree;
import zainyest.ethercore.init.EtherRegistries;

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
        for (ServerPlayer serverPlayer : server.getPlayerList().getPlayers()) {
            PlayerData playerData = StateSaverAndLoader.getPlayerState(serverPlayer);
            if (playerData.isDirty()) {
                CompoundTag nbtOut = new CompoundTag();

                for (String key : playerData.getDirtyElements()) {
                    nbtOut.put(key, playerData.getPersistentData().get(key));
                }

                if (!nbtOut.isEmpty()) {
                    ServerPlayNetworking.send(serverPlayer, new PlayerDataPayload(nbtOut));
                }

                playerData.unMarkDirty();
            }
        }
    }
}

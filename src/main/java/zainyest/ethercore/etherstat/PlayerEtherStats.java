package zainyest.ethercore.etherstat;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.util.PlayerData;
import zainyest.ethercore.util.StateSaverAndLoader;
import zainyest.ethercore.init.EtherRegistries;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


public class PlayerEtherStats {
    public static final String PLAYER_ETHER_STATS_KEY = "player_ether_stats";

    public static NbtCompound instantiateNbt() {
        LinkedHashMap<String, EtherStatView> statViewList = new LinkedHashMap<>();
        for (EtherStat stat : EtherRegistries.ETHER_STATS) {
            statViewList.put(stat.name(), stat.instantiateNbt());
        }
        return toNbt(new PlayerEtherStatsView(statViewList));
    }

    public static NbtCompound toNbt(PlayerEtherStatsView view) {
        NbtCompound out = new NbtCompound();
        for (Map.Entry<String, EtherStatView> e : view.statViewList().sequencedEntrySet()) {
            out.put(e.getKey(), Objects.requireNonNull(EtherRegistries.ETHER_STATS.get(Identifier.of(EtherCore.MOD_ID, e.getKey()))).toNbt(e.getValue()));
        }
        return out;
    }

    public static PlayerEtherStatsView fromPlayerData(PlayerData playerData) {
        NbtCompound nbt = playerData.getPersistentData().getCompoundOrEmpty(PLAYER_ETHER_STATS_KEY);
        LinkedHashMap<String, EtherStatView> statViewList = new LinkedHashMap<>();
        for (EtherStat stat : EtherRegistries.ETHER_STATS) {
            statViewList.put(stat.name(), stat.fromNbt(nbt.getCompoundOrEmpty(stat.name())));
        }
        return new PlayerEtherStatsView(statViewList);
    }

    public static NbtCompound getOrCreateNbt(PlayerData playerData) {
        if (playerData.getPersistentData().getCompoundOrEmpty(PLAYER_ETHER_STATS_KEY).isEmpty()) {
            return instantiateNbt();
        }
        return toNbt(fromPlayerData(playerData));
    }

    public static void updateStats(MinecraftServer server) {
        for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
            if (serverPlayer == null) {
                //EtherCore.LOGGER.info("Null Player, skipping tickPool");
                EtherCore.LOGGER.atError().log("Null Player, skipping tickPool");
                return;
            }
            PlayerData playerData = StateSaverAndLoader.getPlayerState(serverPlayer);
            playerData.persistentData.put(PlayerEtherStats.PLAYER_ETHER_STATS_KEY, PlayerEtherStats.getOrCreateNbt(playerData));
            playerData.markDirty(PLAYER_ETHER_STATS_KEY);
        }
    }
}

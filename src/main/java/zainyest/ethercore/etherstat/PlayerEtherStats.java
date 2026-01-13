package zainyest.ethercore.etherstat;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.util.PlayerData;
import zainyest.ethercore.init.EtherRegistries;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


public class PlayerEtherStats {
    public static final String PLAYER_ETHER_STATS_KEY = "player_ether_stats";

    public static CompoundTag instantiateNbt() {
        LinkedHashMap<String, EtherStatView> statViewList = new LinkedHashMap<>();
        for (EtherStat stat : EtherRegistries.ETHER_STATS) {
            statViewList.put(stat.name(), stat.instantiateNbt());
        }
        return toNbt(new PlayerEtherStatsView(statViewList));
    }

    public static CompoundTag toNbt(PlayerEtherStatsView view) {
        CompoundTag out = new CompoundTag();
        for (Map.Entry<String, EtherStatView> e : view.statViewList().sequencedEntrySet()) {
            out.put(e.getKey(), Objects.requireNonNull(EtherRegistries.ETHER_STATS.getValue(Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, e.getKey()))).toNbt(e.getValue()));
        }
        return out;
    }

    public static PlayerEtherStatsView fromPlayerData(PlayerData playerData) {
        CompoundTag nbt = playerData.getPersistentData().getCompoundOrEmpty(PLAYER_ETHER_STATS_KEY);
        LinkedHashMap<String, EtherStatView> statViewList = new LinkedHashMap<>();
        for (EtherStat stat : EtherRegistries.ETHER_STATS) {
            statViewList.put(stat.name(), stat.fromNbt(nbt));
        }
        return new PlayerEtherStatsView(statViewList);
    }

    public static CompoundTag getOrCreateNbt(PlayerData playerData) {
        if (playerData.getPersistentData().getCompoundOrEmpty(PLAYER_ETHER_STATS_KEY).isEmpty()) {
            return instantiateNbt();
        }
        return toNbt(fromPlayerData(playerData));
    }

//    public static void updateStats(MinecraftServer server) {
//        for (ServerPlayer serverPlayer : server.getPlayerList().getPlayers()) {
//            PlayerData playerData = StateSaverAndLoader.getPlayerState(serverPlayer);
//            if (playerData.getDirtyElements().contains(PLAYER_ETHER_STATS_KEY)) {
//                continue;
//            }
//            playerData.persistentData.put(PlayerEtherStats.PLAYER_ETHER_STATS_KEY, PlayerEtherStats.getOrCreateNbt(playerData));
//            playerData.markDirty(PLAYER_ETHER_STATS_KEY);
//        }
//    }
}

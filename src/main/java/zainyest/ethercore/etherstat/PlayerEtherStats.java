package zainyest.ethercore.etherstat;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.util.PlayerData;
import zainyest.ethercore.util.init.EtherRegistries;

import java.util.LinkedHashMap;
import java.util.Map;


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
            out.put(e.getKey(), EtherRegistries.ETHER_STATS.get(Identifier.of(EtherCore.MOD_ID, e.getKey())).toNbt(e.getValue()));
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
}

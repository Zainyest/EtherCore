package zainyest.ethercore.etherstat;

import net.minecraft.nbt.NbtCompound;
import zainyest.ethercore.util.PlayerData;
import zainyest.ethercore.util.init.EtherRegistries;

import java.util.LinkedHashMap;
import java.util.Map;


// TODO is this needed? could place in Etherdata some accessors/util methods to do the same thing without the object instantiation
public class PlayerEtherStats {
    public LinkedHashMap<String, EtherStat> statList = new LinkedHashMap<>();

    public PlayerEtherStats(LinkedHashMap<String, EtherStat> statList) {
        this.statList = statList;
    }

    public NbtCompound toNbt() {
        NbtCompound out = new NbtCompound();
        for (Map.Entry<String, EtherStat> e : this.statList.sequencedEntrySet()) {
            out.put(e.getKey(), e.getValue().toNbt());
        }
        return out;
    }

    public void fromNbt(PlayerData playerData) {
        NbtCompound nbt = playerData.getPersistentData().getCompoundOrEmpty("player_ether_stats");
        for (EtherStat stat : EtherRegistries.ETHER_STATS) {
            stat.fromNbt(nbt.getCompoundOrEmpty(stat.name));
            this.statList.put(stat.name, stat);
        }
    }
}

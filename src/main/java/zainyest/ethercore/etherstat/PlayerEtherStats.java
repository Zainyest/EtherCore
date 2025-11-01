package zainyest.ethercore.etherstat;

import net.minecraft.nbt.NbtCompound;

import java.util.LinkedHashMap;
import java.util.Map;

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
}

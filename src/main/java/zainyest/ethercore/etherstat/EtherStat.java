package zainyest.ethercore.etherstat;

import net.minecraft.nbt.NbtCompound;

import java.util.LinkedHashMap;
import java.util.Map;

public class EtherStat {
    public String name;
    public int base;
    public LinkedHashMap<String, Integer> statModifiers = new LinkedHashMap<>();

    public EtherStat(String name, int base) {
        this.name = name;
        this.base = base;
    }

    public int getStatTotal() {
        int total = base;
        for (int e : statModifiers.sequencedValues()) {
            total += e;
        }
        return total;
    }

    public NbtCompound toNbt() {
        NbtCompound out = new NbtCompound();
        out.putString("name", name);
        out.putInt("base", base);
        NbtCompound statModifierCompound = new NbtCompound();
        for (Map.Entry<String, Integer> entry : this.statModifiers.sequencedEntrySet()) {
            statModifierCompound.putInt(entry.getKey(), entry.getValue());
        }
        out.put("modifiers", statModifierCompound);
        return out;
    }
}

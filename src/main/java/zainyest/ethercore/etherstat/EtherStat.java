package zainyest.ethercore.etherstat;

import net.minecraft.nbt.NbtCompound;
import zainyest.ethercore.util.PlayerData;

import java.util.LinkedHashMap;
import java.util.Map;

// TODO maybe recreate this to not use local variables? EtherPool as example, but use less variable reference as it reads strangely
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

    public void fromNbt(NbtCompound nbtIn) { // TODO REMOVE THESE from "Technique" classes, replace with getters from playerdata (like EtherPool), but maybe with view-like object? YES, Make fromNbt methods return a nbtView object
        NbtCompound nbt = nbtIn.getCompoundOrEmpty(this.name);
        this.base = nbt.getInt("base").orElse(0);
        nbt.getCompoundOrEmpty("modifiers").entrySet().forEach((entry) -> {
            this.statModifiers.put(entry.getKey(), entry.getValue().asInt().orElse(0));
        });
    }
}

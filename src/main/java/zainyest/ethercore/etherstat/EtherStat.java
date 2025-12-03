package zainyest.ethercore.etherstat;

import net.minecraft.nbt.NbtCompound;
import zainyest.ethercore.EtherCore;

import java.util.LinkedHashMap;
import java.util.Map;

public record EtherStat(String name, int base) {

    public String getTranslatableName() {
        return EtherCore.MOD_ID + ".text." + this.name() + ".name";
    }
    public String getTranslatableDescription() {
        return EtherCore.MOD_ID + ".text." + this.name() + ".description";
    }

    public NbtCompound toNbt(EtherStatView view) {
        NbtCompound out = new NbtCompound();
        out.putString("name", name);
        out.putInt("base", base);
        NbtCompound statModifierCompound = new NbtCompound();
        for (Map.Entry<String, Integer> entry : view.statModifiers().sequencedEntrySet()) {
            statModifierCompound.putInt(entry.getKey(), entry.getValue());
        }
        out.put("modifiers", statModifierCompound);
        return out;
    }

    public EtherStatView fromNbt(NbtCompound nbtIn) {
        NbtCompound nbt = nbtIn.getCompoundOrEmpty(this.name);
        int base;
        LinkedHashMap<String, Integer> statModifiers = new LinkedHashMap<>();
        base = nbt.getInt("base").orElse(this.base);
        nbt.getCompoundOrEmpty("modifiers").entrySet().forEach((entry) -> statModifiers.put(entry.getKey(), entry.getValue().asInt().orElse(0)));
        return new EtherStatView(this.name, base, statModifiers);
    }

    public EtherStatView instantiateNbt() {
        return new EtherStatView(this.name, this.base, new LinkedHashMap<>());
    }
}

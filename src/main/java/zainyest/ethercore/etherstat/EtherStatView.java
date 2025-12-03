package zainyest.ethercore.etherstat;

import net.minecraft.text.Text;
import zainyest.ethercore.EtherCore;

import java.util.LinkedHashMap;
import java.util.Map;

public record EtherStatView(String name, int base, LinkedHashMap<String, Integer> statModifiers) {
    public int getStatTotal() {
        int total = base;
        for (int e : statModifiers.sequencedValues()) {
            total += e;
        }
        return total;
    }

    public String statModifiersTranslated() {
        StringBuilder out = new StringBuilder();

        for (Map.Entry<String, Integer> entry : this.statModifiers().sequencedEntrySet()) {
            String translatableKey = EtherCore.MOD_ID + ".text." + this.name() + ".modifier." + entry.getKey();
            out.append(Text.translatable(translatableKey)).append(": ").append(entry.getValue().toString());
            out.append("\n");
        }

        return out.toString().stripTrailing();
    }
}

package zainyest.ethercore.etherstat;

import net.minecraft.network.chat.Component;
import zainyest.ethercore.EtherCore;

import java.util.LinkedHashMap;
import java.util.Map;

public record EtherStatView(String name, int base, LinkedHashMap<String, Integer> statModifiers) {
    public int getStatTotal() {
        int total = this.base();
        for (Map.Entry<String, Integer> entry : statModifiers.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }

    public String statModifiersTranslated() {
        StringBuilder out = new StringBuilder();

        for (Map.Entry<String, Integer> entry : this.statModifiers().sequencedEntrySet()) {
            String translatableKey = EtherCore.MOD_ID + ".text.modifier." + entry.getKey();
            out.append(Component.translatable(translatableKey).getString()).append(": ").append(entry.getValue().toString());
            out.append("\n");
        }

        return out.toString().stripTrailing();
    }
}

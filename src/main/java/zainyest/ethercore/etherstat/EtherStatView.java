package zainyest.ethercore.etherstat;

import java.util.LinkedHashMap;

public record EtherStatView(String name, int base, LinkedHashMap<String, Integer> statModifiers) {
    public int getStatTotal() {
        int total = base;
        for (int e : statModifiers.sequencedValues()) {
            total += e;
        }
        return total;
    }
}

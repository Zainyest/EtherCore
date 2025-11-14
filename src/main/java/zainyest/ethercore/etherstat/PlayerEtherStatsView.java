package zainyest.ethercore.etherstat;

import java.util.LinkedHashMap;

public record PlayerEtherStatsView(LinkedHashMap<String, EtherStatView> statViewList) {
}

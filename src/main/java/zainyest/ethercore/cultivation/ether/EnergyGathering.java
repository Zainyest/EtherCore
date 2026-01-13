package zainyest.ethercore.cultivation.ether;

import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.cultivation.Stage;
import zainyest.ethercore.tag.EtherStatTagProvider;

/// Applies a +10 bonus to all Ether stats
public class EnergyGathering extends Stage {

    public static final int STAT_BONUS = 10;

    public EnergyGathering() {
        super("energy_gathering",
                new Identifier[0],
                new Identifier[]{EtherCore.id("core_formation")},
                EtherCore.id("icon.png"),
                0);
    }

    @Override
    public void apply(MinecraftServer server, ServerPlayer serverPlayer) {
        applyStatBonuses(serverPlayer, EtherStatTagProvider.ETHER, STAT_BONUS);
    }

    @Override
    public void remove(MinecraftServer server, ServerPlayer serverPlayer) {
        removeStatBonuses(serverPlayer, EtherStatTagProvider.ETHER);
    }
}

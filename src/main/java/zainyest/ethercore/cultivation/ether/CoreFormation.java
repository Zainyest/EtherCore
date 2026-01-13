package zainyest.ethercore.cultivation.ether;

import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.cultivation.Stage;
import zainyest.ethercore.tag.EtherStatTagProvider;

public class CoreFormation extends Stage {

    public CoreFormation() {
        super("core_formation",
                new Identifier[]{EtherCore.id("energy_gathering")},
                new Identifier[0],
                EtherCore.id("icon.png"),
                1000);
    }

    @Override
    public void apply(MinecraftServer server, ServerPlayer serverPlayer) {
        applyStatBonuses(serverPlayer, EtherStatTagProvider.ETHER, 100);
    }

    @Override
    public void remove(MinecraftServer server, ServerPlayer serverPlayer) {
        removeStatBonuses(serverPlayer, EtherStatTagProvider.ETHER);
    }
}

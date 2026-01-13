package zainyest.ethercore.cultivation.mental;

import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.cultivation.Stage;
import zainyest.ethercore.tag.EtherStatTagProvider;

public class MindSharpening extends Stage {
    public MindSharpening() {
        super("mind_sharpening",
                new Identifier[0],
                new Identifier[0],
                EtherCore.id("icon.png"),
                0);
    }

    @Override
    public void apply(MinecraftServer server, ServerPlayer serverPlayer) {
        applyStatBonuses(serverPlayer, EtherStatTagProvider.MENTAL, 10);
    }

    @Override
    public void remove(MinecraftServer server, ServerPlayer serverPlayer) {
        removeStatBonuses(serverPlayer, EtherStatTagProvider.MENTAL);
    }
}

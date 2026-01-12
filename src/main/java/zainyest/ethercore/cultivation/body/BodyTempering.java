package zainyest.ethercore.cultivation.body;

import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.cultivation.Stage;

public class BodyTempering extends Stage {
    public BodyTempering() {
        super("body_tempering",
                new Identifier[0],
                new Identifier[0],
                EtherCore.id("icon.png"),
                0);
    }

    @Override
    public void apply(MinecraftServer server, ServerPlayer serverPlayer) {

    }

    @Override
    public void remove(MinecraftServer server, ServerPlayer serverPlayer) {

    }
}

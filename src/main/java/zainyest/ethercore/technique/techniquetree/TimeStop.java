package zainyest.ethercore.technique.techniquetree;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.ActivatedTechnique;

public class TimeStop extends ActivatedTechnique {
    public TimeStop(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    public TimeStop() {
        super("time_stop",
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "mental_acuity")},
                new Identifier[0],
                "active",
                "ethercore.text.time_stop.description",
                Identifier.of(EtherCore.MOD_ID, "icon.png"));

    }

    @Override
    public void manifest(MinecraftServer server) {

    }
}

package zainyest.ethercore.cultivation;

import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import zainyest.ethercore.EtherCore;

/// A step on the path, bundled with benefits. Includes advancement and loss functionality
public abstract class Stage {
    private final String name;
    private final Identifier[] parents;
    private final Identifier[] children;
    private final Identifier icon;

    private final int requiredAura;

    public Stage(String name, Identifier[] parents, Identifier[] children, Identifier icon, int requiredAura) {
        this.name = name;
        this.parents = parents;
        this.children = children;
        this.icon = icon;
        this.requiredAura = requiredAura;
    }

    public String getName() {
        return name;
    }
    public Identifier[] getParents() {
        return this.parents;
    }
    public Identifier[] getChildren() {
        return this.children;
    }
    public String getDescription() {
        return "ethercore.text." + this.name + ".description";
    }
    public Identifier getIcon() {
        return icon;
    }
    public String getTranslatableName() {
        return EtherCore.MOD_ID + ".text." + this.name + ".name";
    }

    public int getRequiredAura() {
        return this.requiredAura;
    }

    public abstract void apply(MinecraftServer server, ServerPlayer serverPlayer);

    public abstract void remove(MinecraftServer server, ServerPlayer serverPlayer);

}

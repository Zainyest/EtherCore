package zainyest.ethercore.technique;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;

public abstract class Technique {
    private final String techniqueName;
    private final Identifier[] parents;
    private final Identifier[] children;
    private final String techniqueType;
    private final String description;
    private final Identifier icon;

    public Technique(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        this.techniqueName = name;
        this.parents = parents;
        this.children = children;
        this.techniqueType = techniqueType;
        this.description = description;
        this.icon = icon;
    }

    // Getters and Setters
    public String getName() {
        return techniqueName;
    }
    public Identifier[] getParents() {
        return this.parents;
    }
    public Identifier[] getChildren() {
        return this.children;
    }
    public String getDescription() {
        return description;
    }
    public Identifier getIcon() {
        return icon;
    }
    public String getTechniqueType() {
        return this.techniqueType;
    }

    public String getTranslatableName() {
        return EtherCore.MOD_ID + ".text." + this.techniqueName + ".name";
    }

    /// Returns the Technique as NbtCompound for networking and displaying
    public NbtCompound toNbt() {
        NbtCompound techniqueData = new NbtCompound();

        techniqueData.putString("name", techniqueName);

        //keep this
        boolean learned = false;
        techniqueData.putBoolean("learned", learned);

        return techniqueData;
    }

    /**
     * Actualize the effects of the technique.
     * For passive should be on-tick or entity modifier,
     * for active should use a cost and manifest an effect in the world.
     */
    public abstract void tick(MinecraftServer server, ServerPlayerEntity serverPlayer);
}

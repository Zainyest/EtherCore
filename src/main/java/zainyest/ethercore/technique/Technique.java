package zainyest.ethercore.technique;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;

/// <h4>To create a technique:</h4>
/// <ul>
/// <li>extend one of the abstract classes</li>
/// <li>create a no-arg constructor</li>
/// <li>register in Techniques</li>
/// <li>add fields to lang file</li>
/// </ul>
public abstract class Technique {
    private final String techniqueName;
    private final Identifier[] parents;
    private final Identifier[] children;
    private final Identifier icon;

    public Technique(String name, Identifier[] parents, Identifier[] children, Identifier icon) {
        this.techniqueName = name;
        this.parents = parents;
        this.children = children;
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
        return "ethercore.text." + this.techniqueName + ".description";
    }
    public Identifier getIcon() {
        return icon;
    }

    public String getTranslatableName() {
        return EtherCore.MOD_ID + ".text." + this.techniqueName + ".name";
    }

    /// Returns the Technique as NbtCompound for networking and displaying
    public CompoundTag toNbt() {
        CompoundTag techniqueData = new CompoundTag();

        techniqueData.putString("name", techniqueName);

        //keep this
        boolean learned = false;
        techniqueData.putBoolean("learned", learned);

        return techniqueData;
    }
}

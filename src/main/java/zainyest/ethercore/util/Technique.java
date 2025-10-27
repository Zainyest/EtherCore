package zainyest.ethercore.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public abstract class Technique {
    private String techniqueName;
    private Technique[] parents;
    private Technique[] children;
    private String techniqueType; // TODO could replace this with "instanceof" keyword if types are subsidiary abstracts
    private String description;
    private Identifier icon;
    private Boolean learned = false;

    public Technique(String name, Technique[] parents, Technique[] children, String techniqueType, String description, Identifier icon) {
        this.techniqueName = name;
        this.parents = parents;
        this.children = children;
        this.techniqueType = techniqueType;
        this.description = description;
        this.icon = icon;
    }

    // Getters and Setters
    public void setName(String name) {
        this.techniqueName = name;
    }
    public String getName() {
        return techniqueName;
    }
    public void setParents(Technique[] parents) {
        this.parents = parents;
    }
    public Technique[] getParents() {
        return this.parents;
    }
    public void setChildren(Technique[] children) {
        this.children = children;
    }
    public Technique[] getChildren() {
        return this.children;
    }
    public void setType(String techniqueType) {
        this.techniqueType = techniqueType;
    }
    public String getType() {
        return this.techniqueType;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Identifier getIcon() {
        return icon;
    }
    public void setIcon(Identifier icon) {
        this.icon = icon;
    }
    public Boolean getLearned() {
        return this.learned;
    }
    public void setLearned(Boolean learned) {
        this.learned = learned;
    }

    /// Returns the Technique as NbtCompound for networking and displaying
    public NbtCompound toNbt() {
        NbtCompound techniqueData = new NbtCompound();

        techniqueData.putString("name", techniqueName);
        // if null, this is a root
        for (Technique p : this.parents) {
            techniqueData.putString("parent", p.getName());
        }
        // if null, this is a leaf
        for (Technique c : this.children) {
            techniqueData.putString("child", c.getName());
        }
        techniqueData.putString("type", this.techniqueType);
        techniqueData.putString("description", this.description);
        techniqueData.putString("icon", this.icon.toString());
        techniqueData.putBoolean("learned", this.learned);

        return techniqueData;
    }

    /**
     * Actualize the effects of the technique.
     * For passive should be on-tick or entity modifier,
     * for active should use a cost and manifest an effect in the world.
     */
    public abstract void manifest();
}

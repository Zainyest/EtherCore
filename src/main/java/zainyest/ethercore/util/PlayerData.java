package zainyest.ethercore.util;

import net.minecraft.nbt.NbtCompound;

import java.util.HashSet;
import java.util.Set;

public class PlayerData {
    public NbtCompound persistentData = new NbtCompound();
    private boolean dirty = false;
    private final Set<String> dirtyElements = new HashSet<>();

    public boolean isDirty() {
        return this.dirty;
    }

    public void markDirty() {
        this.dirty = true;
    }

    public void markDirty(String key) {
        this.dirty = true;
        this.dirtyElements.add(key);
    }

    public void unMarkDirty() {
        this.dirty = false;
        this.dirtyElements.clear();
    }

    public Set<String> getDirtyElements() {
        return dirtyElements;
    }

    public PlayerData() {}

    public PlayerData(NbtCompound persistentData) {
        this.persistentData = persistentData;
    }

    public NbtCompound getPersistentData() {
        return persistentData;
    }
}

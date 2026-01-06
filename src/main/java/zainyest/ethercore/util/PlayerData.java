package zainyest.ethercore.util;

import net.minecraft.nbt.CompoundTag;

import java.util.HashSet;
import java.util.Set;

public class PlayerData {
    public CompoundTag persistentData = new CompoundTag();
    private boolean dirty = false;
    private final Set<String> dirtyElements = new HashSet<>();

    public boolean isDirty() {
        return this.dirty;
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

    public PlayerData(CompoundTag persistentData) {
        this.persistentData = persistentData;
    }

    public CompoundTag getPersistentData() {
        return persistentData;
    }
}

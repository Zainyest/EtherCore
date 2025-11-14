package zainyest.ethercore.util;

import net.minecraft.nbt.NbtCompound;

public class PlayerData {
    public NbtCompound persistentData = new NbtCompound();
    private boolean dirty = false;

    public boolean isDirty() {
        return this.dirty;
    }

    public void markDirty() {
        this.dirty = true;
    }

    public void unMarkDirty() {
        this.dirty = false;
    }

    public PlayerData() {}

    public PlayerData(NbtCompound persistentData) {
        this.persistentData = persistentData;
    }

    public NbtCompound getPersistentData() {
        return persistentData;
    }
}

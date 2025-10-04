package zainyest.ethercore.util;

import net.minecraft.nbt.NbtCompound;

public class PlayerData {
    public NbtCompound persistentData = new NbtCompound();

    public PlayerData() {}

    public PlayerData(NbtCompound persistentData) {
        this.persistentData = persistentData;
    }

    public NbtCompound getPersistentData() {
        return persistentData;
    }
}

package zainyest.ethercore.util;

import net.minecraft.nbt.NbtCompound;

public class EtherData {
    public static void setMaxEther(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt("maxEther", amount);
    }
    public static int getMaxEther(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt("maxEther");
    }

    public static int getEther(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt("ether");
    }

    public static int addEther(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int ether = nbt.getInt("ether");
        if (ether + amount >= nbt.getInt("maxEther")) {
            ether = nbt.getInt("maxEther");
        } else {
            ether += amount;
        }

        nbt.putInt("ether", ether);
        // SYNC HERE
        return ether;
    }
    public static int removeEther(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int ether = nbt.getInt("ether");
        if (ether - amount < 0) {
            ether = 0;
        } else {
            ether -= amount;
        }

        nbt.putInt("ether", ether);
        // SYNC HERE
        return ether;
    }
}

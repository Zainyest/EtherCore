package zainyest.ethercore.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import zainyest.ethercore.networking.ModPackets;

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
        syncEther(ether, (ServerPlayerEntity) player);
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

        syncEther(ether, (ServerPlayerEntity) player);
        return ether;
    }

    public static void  syncEther(int ether, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeInt(ether);
        buffer.writeInt(((IEntityDataSaver) player).getPersistentData().getInt("maxEther"));
        ServerPlayNetworking.send(player, ModPackets.ETHER_SYNC_ID, buffer);
    }
}

package zainyest.ethercore.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import zainyest.ethercore.networking.ModPackets;

import java.util.Random;

public class EtherPool {
    private String poolName;
    public EtherPool(String inputPoolName) {
        setName(inputPoolName);
    }
    public void setRegenRate(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt(poolName+"_regen_rate", amount);
    }
    public int getRegenRate(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt(poolName+"_regen_rate");
    }
    public void setName(String inputPoolName) {
        poolName = inputPoolName;
    }
    public String getName() {
        return poolName;
    }
    public void setMax(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt(poolName+"_max", amount);
    }
    public int getMax(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt(poolName+"_max");
    }

    public int getVal(IEntityDataSaver player) {
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt(poolName);
    }

    public int add(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int poolVal = nbt.getInt(poolName);
        if (poolVal + amount >= nbt.getInt(poolName+"_max")) {
            poolVal = nbt.getInt(poolName+"_max");
        } else {
            poolVal += amount;
        }

        nbt.putInt(poolName, poolVal);

        syncPool(poolVal, (ServerPlayerEntity) player);
        return poolVal;
    }
    public int remove(IEntityDataSaver player, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int poolVal = nbt.getInt(poolName);
        if (poolVal - amount < 0) {
            poolVal = 0;
        } else {
            poolVal -= amount;
        }

        nbt.putInt(poolName, poolVal);

        syncPool(poolVal, (ServerPlayerEntity) player);
        return poolVal;
    }

    public void syncPool(int poolVal, ServerPlayerEntity player) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeString(poolName);
        buffer.writeInt(poolVal);
        buffer.writeInt(((IEntityDataSaver) player).getPersistentData().getInt(poolName+"_max"));
        ServerPlayNetworking.send(player, ModPackets.POOL_SYNC_ID, buffer);
    }

    public void tickPool(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            assert player != null; //TODO MAYBE?
            IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);

            if (getRegenRate(dataPlayer) < 1) {
                setRegenRate(dataPlayer, 1);
            }

            add(dataPlayer, getRegenRate(dataPlayer));

            if (getMax(dataPlayer) < 1000) {
                setMax(dataPlayer, 1000); // TODO make pool Max & Regen data persist through death
            }
            if (new Random().nextFloat() <= 0.005f) { // TODO replace temporary pool stat increase function with Cultivation system
                setMax(dataPlayer, getMax(dataPlayer)+100);
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_STEP, SoundCategory.PLAYERS, 4.0f, 4.0f);
            }
        }
    }
}

package zainyest.ethercore.util;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import zainyest.ethercore.networking.packet.PlayerDataPayload;

import java.util.Random;

public class EtherPool {
    private String poolName;
    public EtherPool(String inputPoolName) {
        setName(inputPoolName);
    }
    public void setRegenRate(PlayerData playerData, int amount) {
        NbtCompound nbt = playerData.getPersistentData();
        nbt.putInt(poolName+"_regen_rate", amount);
    }
    public int getRegenRate(PlayerData playerData) {
        NbtCompound nbt = playerData.getPersistentData();
        return nbt.getInt(poolName+"_regen_rate").orElse(0);
    }
    public void setName(String inputPoolName) {
        poolName = inputPoolName;
    }
    public String getName() {
        return poolName;
    }
    public void setMax(PlayerData playerData, int amount) {
        NbtCompound nbt = playerData.getPersistentData();
        nbt.putInt(poolName+"_max", amount);
    }
    public int getMax(PlayerData playerData) {
        NbtCompound nbt = playerData.getPersistentData();
        return nbt.getInt(poolName+"_max").orElse(0);
    }

    public int getMax(NbtCompound nbt) {
        return nbt.getInt(poolName+"_max").orElse(0);
    }

    public int getVal(NbtCompound nbt) {
        return nbt.getInt(poolName).orElse(0);
    }

    public int add(ServerPlayerEntity serverPlayer, PlayerData playerData, int amount) {
        NbtCompound nbt = playerData.getPersistentData();
        int poolVal = nbt.getInt(poolName).orElse(0);
        if (poolVal + amount >= nbt.getInt(poolName+"_max").orElse(0)) {
            poolVal = nbt.getInt(poolName+"_max").orElse(0);
        } else {
            poolVal += amount;
        }

        nbt.putInt(poolName, poolVal);

        syncPool(serverPlayer);
        return poolVal;
    }

    public int remove(ServerPlayerEntity serverPlayer, PlayerData playerData, int amount) {
        NbtCompound nbt = playerData.getPersistentData();
        int poolVal = nbt.getInt(poolName).orElse(0);
        if (poolVal - amount < 0) {
            poolVal = 0;
        } else {
            poolVal -= amount;
        }

        nbt.putInt(poolName, poolVal);

        syncPool(serverPlayer);
        return poolVal;
    }

    public void syncPool(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new PlayerDataPayload(StateSaverAndLoader.getPlayerState(player).getPersistentData()));
    }

    public void tickPool(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            assert player != null; //TODO MAYBE?
            PlayerData dataPlayer = StateSaverAndLoader.getPlayerState(player);

            if (getRegenRate(dataPlayer) < 1) {
                setRegenRate(dataPlayer, 1);
            }

            add(player, dataPlayer, getRegenRate(dataPlayer));

            if (getMax(dataPlayer) < 1000) {
                setMax(dataPlayer, 1000);
            }
            if (new Random().nextFloat() <= 0.005f) { // TODO replace temporary pool stat increase function with Cultivation system
                setMax(dataPlayer, getMax(dataPlayer)+100);
                player.getEntityWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_STEP, SoundCategory.PLAYERS, 4.0f, 4.0f);
            }
        }
    }
}

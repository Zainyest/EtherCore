package zainyest.ethercore.util;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.etherstat.PlayerEtherStats;
import zainyest.ethercore.networking.packet.PlayerDataPayload;

public record EtherPool(String poolName, String volumeStat, String regenStat, double volumeStatConversionRate, double regenStatConversionRate) {
    public void setRegenRate(PlayerData playerData, int amount) {
        NbtCompound nbt = playerData.getPersistentData();
        nbt.putInt(poolName + "_regen_rate", amount);
    }

    public int getRegenRate(PlayerData playerData) {
        NbtCompound nbt = playerData.getPersistentData();
        return nbt.getInt(poolName + "_regen_rate").orElse(0);
    }

    public void setMax(PlayerData playerData, int amount) {
        NbtCompound nbt = playerData.getPersistentData();
        nbt.putInt(poolName + "_max", amount);
    }

    public int getMax(PlayerData playerData) {
        NbtCompound nbt = playerData.getPersistentData();
        return nbt.getInt(poolName + "_max").orElse(0);
    }

    public int getMax(NbtCompound nbt) {
        return nbt.getInt(poolName + "_max").orElse(0);
    }

    public int getVal(NbtCompound nbt) {
        return nbt.getInt(poolName).orElse(0);
    }

    public int add(ServerPlayerEntity serverPlayer, PlayerData playerData, int amount) {
        NbtCompound nbt = playerData.getPersistentData();
        int poolVal = nbt.getInt(poolName).orElse(0);
        if (poolVal + amount >= nbt.getInt(poolName + "_max").orElse(0)) {
            poolVal = nbt.getInt(poolName + "_max").orElse(0);
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
            if (player == null) {
                EtherCore.LOGGER.info("Null Player, skipping tickPool");
                return;
            }
            PlayerData dataPlayer = StateSaverAndLoader.getPlayerState(player);

            setRegenRate(dataPlayer, (int) Math.round(PlayerEtherStats.fromPlayerData(dataPlayer).statViewList().get(regenStat).getStatTotal() * regenStatConversionRate));

            add(player, dataPlayer, getRegenRate(dataPlayer));

            setMax(dataPlayer, (int) Math.round(PlayerEtherStats.fromPlayerData(dataPlayer).statViewList().get(volumeStat).getStatTotal() * volumeStatConversionRate));
        }
    }
}

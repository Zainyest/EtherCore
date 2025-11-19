package zainyest.ethercore.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.etherstat.PlayerEtherStats;

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

    public int add(int regenRate, int max, int val) {
        int poolVal = val;
        if (poolVal + regenRate >= max) {
            poolVal = max;
        } else {
            poolVal += regenRate;
        }

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

    public NbtCompound instantiateNbt() {
        return toNbt(new EtherPoolView(this.poolName, 0, 0, 0));
    }
    public EtherPoolView instantiateView() {
        return  new EtherPoolView(this.poolName, 0, 0, 0);
    }

    public NbtCompound toNbt(EtherPoolView view) {
        NbtCompound out = new NbtCompound();

        out.putString("name", this.poolName);
        out.putInt("regen_rate", view.regenRate());
        out.putInt("max", view.max());
        out.putInt(this.poolName, view.val());

        return out;
    }

    public EtherPoolView fromNbt(NbtCompound nbtIn) {
        NbtCompound nbt = nbtIn.getCompoundOrEmpty(this.poolName);
        return new EtherPoolView(this.poolName,
                nbt.getInt("regen_rate").orElse(0),
                nbt.getInt("max").orElse(0),
                nbt.getInt(this.poolName).orElse(0));
    }

    public EtherPoolView fromPlayerData(PlayerData playerData) {
        NbtCompound nbt = playerData.getPersistentData();
        return fromNbt(nbt);
    }

    public NbtCompound getOrCreateNbt(PlayerData playerData) {
        if (playerData.getPersistentData().getCompoundOrEmpty(this.poolName).isEmpty()) {
            return instantiateNbt();
        }
        return toNbt(fromPlayerData(playerData));
    }

    public EtherPoolView getOrCreateView(PlayerData playerData) {
        if (playerData.getPersistentData().getCompoundOrEmpty(this.poolName).isEmpty()) {
            return instantiateView();
        }
        return fromPlayerData(playerData);
    }

    public void syncPool(ServerPlayerEntity player) {
        //ServerPlayNetworking.send(player, new PlayerDataPayload(StateSaverAndLoader.getPlayerState(player).getPersistentData()));

        StateSaverAndLoader.getPlayerState(player).markDirty(this.poolName);
    }

    public void tickPool(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player == null) {
                EtherCore.LOGGER.info("Null Player, skipping tickPool");
                return;
            }
            PlayerData dataPlayer = StateSaverAndLoader.getPlayerState(player);

            EtherPoolView pre = getOrCreateView(dataPlayer);

            int regenRate = (int) Math.round(PlayerEtherStats.fromPlayerData(dataPlayer).statViewList().get(regenStat).getStatTotal() * regenStatConversionRate);
            int max = (int) Math.round(PlayerEtherStats.fromPlayerData(dataPlayer).statViewList().get(volumeStat).getStatTotal() * volumeStatConversionRate);

            EtherPoolView cur = new EtherPoolView(this.poolName(),
                    regenRate,
                    max,
                    add(regenRate, max, pre.val()));

            dataPlayer.persistentData.put(this.poolName, toNbt(cur));
            syncPool(player);
        }
    }
}

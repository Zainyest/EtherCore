package zainyest.ethercore.etherpool;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.etherstat.PlayerEtherStats;
import zainyest.ethercore.etherstat.PlayerEtherStatsView;
import zainyest.ethercore.util.PlayerData;
import zainyest.ethercore.util.StateSaverAndLoader;

public record EtherPool(String poolName, String volumeStat, String regenStat, double volumeStatConversionRate, double regenStatConversionRate) {

    public int add(int regenRate, int max, int val) {
        int poolVal = val;
        if (poolVal + regenRate >= max) {
            poolVal = max;
        } else {
            poolVal += regenRate;
        }

        return poolVal;
    }

    public int remove(int cost, int val) {
        int poolVal = val;
        if (poolVal - cost < 0) {
            poolVal = 0;
        } else {
            poolVal -= cost;
        }

        return poolVal;
    }

    public CompoundTag instantiateNbt() {
        return toNbt(instantiateView());
    }
    public EtherPoolView instantiateView() {
        return  new EtherPoolView(this.poolName, 0, 0, 0);
    }

    public CompoundTag toNbt(EtherPoolView view) {
        CompoundTag out = new CompoundTag();

        out.putString("name", this.poolName);
        out.putInt("regen_rate", view.regenRate());
        out.putInt("max", view.max());
        out.putInt(this.poolName, view.val());

        return out;
    }

    public EtherPoolView fromNbt(CompoundTag nbtIn) {
        CompoundTag nbt = nbtIn.getCompoundOrEmpty(this.poolName);
        return new EtherPoolView(this.poolName,
                nbt.getInt("regen_rate").orElse(0),
                nbt.getInt("max").orElse(0),
                nbt.getInt(this.poolName).orElse(0));
    }

    public EtherPoolView fromPlayerData(PlayerData playerData) {
        CompoundTag nbt = playerData.getPersistentData();
        return fromNbt(nbt);
    }

    public CompoundTag getOrCreateNbt(PlayerData playerData) {
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

    public void syncPool(ServerPlayer player) {
        //ServerPlayNetworking.send(player, new PlayerDataPayload(StateSaverAndLoader.getPlayerState(player).getPersistentData()));

        StateSaverAndLoader.getPlayerState(player).markDirty(this.poolName);
    }

    public void tickPool(MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (player == null) {
                EtherCore.LOGGER.info("Null Player, skipping tickPool");
                return;
            }
            PlayerData dataPlayer = StateSaverAndLoader.getPlayerState(player);

            EtherPoolView pre = getOrCreateView(dataPlayer);

            PlayerEtherStatsView playerEtherStatsView = PlayerEtherStats.fromPlayerData(dataPlayer);

            int regenRate = (int) Math.round(playerEtherStatsView.statViewList().get(regenStat).getStatTotal() * regenStatConversionRate);
            int max = (int) Math.round(playerEtherStatsView.statViewList().get(volumeStat).getStatTotal() * volumeStatConversionRate);

            EtherPoolView cur = new EtherPoolView(this.poolName(),
                    regenRate,
                    max,
                    add(regenRate, max, pre.val()));

            dataPlayer.persistentData.put(this.poolName, toNbt(cur));
            syncPool(player);
        }
    }
}

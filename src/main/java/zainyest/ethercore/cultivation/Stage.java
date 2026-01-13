package zainyest.ethercore.cultivation;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.etherstat.EtherStat;
import zainyest.ethercore.etherstat.EtherStatView;
import zainyest.ethercore.etherstat.PlayerEtherStats;
import zainyest.ethercore.etherstat.PlayerEtherStatsView;
import zainyest.ethercore.init.EtherRegistries;
import zainyest.ethercore.util.PlayerData;
import zainyest.ethercore.util.StateSaverAndLoader;

import java.util.LinkedHashMap;

import static zainyest.ethercore.etherstat.PlayerEtherStats.PLAYER_ETHER_STATS_KEY;

/// A step on the path, bundled with benefits. Includes advancement and loss functionality
public abstract class Stage {
    private final String name;
    private final Identifier[] parents;
    private final Identifier[] children;
    private final Identifier icon;

    private final int requiredAura;

    public Stage(String name, Identifier[] parents, Identifier[] children, Identifier icon, int requiredAura) {
        this.name = name;
        this.parents = parents;
        this.children = children;
        this.icon = icon;
        this.requiredAura = requiredAura;
    }

    public String getName() {
        return name;
    }
    public Identifier[] getParents() {
        return this.parents;
    }
    public Identifier[] getChildren() {
        return this.children;
    }
    public String getDescription() {
        return "ethercore.text." + this.name + ".description";
    }
    public Identifier getIcon() {
        return icon;
    }
    public String getTranslatableName() {
        return EtherCore.MOD_ID + ".text." + this.name + ".name";
    }

    public int getRequiredAura() {
        return this.requiredAura;
    }

    public abstract void apply(MinecraftServer server, ServerPlayer serverPlayer);

    public abstract void remove(MinecraftServer server, ServerPlayer serverPlayer);

    public void applyStatBonuses(ServerPlayer serverPlayer, TagKey<EtherStat> statCategory, int bonus) {
        PlayerData playerData = StateSaverAndLoader.getPlayerState(serverPlayer);
        PlayerEtherStatsView playerEtherStatsView = PlayerEtherStats.fromPlayerData(playerData);
        PlayerEtherStatsView statViewOut = new PlayerEtherStatsView(new LinkedHashMap<>());

        for (Holder<EtherStat> etherStat : EtherRegistries.ETHER_STATS.asHolderIdMap()) {
            if (etherStat.is(statCategory)) {
                java.util.LinkedHashMap<String, Integer> modifiers = playerEtherStatsView.statViewList().get(etherStat.value().name()).statModifiers();
                modifiers.put(this.getName(), bonus);
                statViewOut.statViewList().put(etherStat.value().name(), new EtherStatView(etherStat.value().name(), etherStat.value().base(), modifiers));
            } else {
                statViewOut.statViewList().put(etherStat.value().name(), playerEtherStatsView.statViewList().get(etherStat.value().name()));
            }
        }

        playerData.persistentData.put(PLAYER_ETHER_STATS_KEY, PlayerEtherStats.toNbt(statViewOut));
        playerData.markDirty(PLAYER_ETHER_STATS_KEY);
    }

    public void removeStatBonuses(ServerPlayer serverPlayer, TagKey<EtherStat> statCategory) {
        PlayerData playerData = StateSaverAndLoader.getPlayerState(serverPlayer);
        PlayerEtherStatsView playerEtherStatsView = PlayerEtherStats.fromPlayerData(playerData);
        PlayerEtherStatsView statViewOut = new PlayerEtherStatsView(new LinkedHashMap<>());

        for (Holder<EtherStat> etherStat : EtherRegistries.ETHER_STATS.asHolderIdMap()) {
            if (etherStat.is(statCategory)) {
                java.util.LinkedHashMap<String, Integer> modifiers = playerEtherStatsView.statViewList().get(etherStat.value().name()).statModifiers();
                modifiers.remove(this.getName());
                statViewOut.statViewList().put(etherStat.value().name(), new EtherStatView(etherStat.value().name(), etherStat.value().base(), modifiers));
            } else {
                statViewOut.statViewList().put(etherStat.value().name(), playerEtherStatsView.statViewList().get(etherStat.value().name()));
            }
        }

        playerData.persistentData.put(PLAYER_ETHER_STATS_KEY, PlayerEtherStats.toNbt(statViewOut));
        playerData.markDirty(PLAYER_ETHER_STATS_KEY);
    }

}

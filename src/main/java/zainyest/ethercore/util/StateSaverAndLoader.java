package zainyest.ethercore.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.Level;
import zainyest.ethercore.EtherCore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/// Created with great swaths of code from [fabricmc.net/tutorial:persistent_states](https://wiki.fabricmc.net/tutorial:persistent_states)<br>
/// and [docs.fabricmc.net/develop/codecs](https://docs.fabricmc.net/develop/codecs)<br>
/// and [docs.fabricmc.net/develop/saved-data](https://docs.fabricmc.net/develop/saved-data)
public class StateSaverAndLoader extends SavedData {

    private Map<UUID, PlayerData> players = new HashMap<>();
    private CompoundTag worldlyData = new CompoundTag();

    private StateSaverAndLoader() {}

    private void setPlayers(Map<UUID, CompoundTag> players) {
        players.forEach((uuid, nbtCompound) -> this.players.put(uuid, new PlayerData(nbtCompound)));
    }

    private Map<UUID, CompoundTag> getPlayers() {
        Map<UUID, CompoundTag> players = new HashMap<>();
        this.players.forEach((uuid, playerData) -> players.put(uuid, playerData.persistentData));
        return players;
    }

    private void setWorldlyData(CompoundTag nbtCompound) {
        this.worldlyData = nbtCompound;
    }

    private CompoundTag getWorldlyData() {
        CompoundTag out;
        out = this.worldlyData.copy();
        return out;
    }

    private StateSaverAndLoader(CompoundTag worldlyData, Map<UUID, CompoundTag> players) {
        setWorldlyData(worldlyData);
        setPlayers(players);
    }

    public static StateSaverAndLoader createNew() {
        StateSaverAndLoader state = new StateSaverAndLoader();
        // Any Worldly Variables Go Here
        state.worldlyData = new CompoundTag();
        state.players = new HashMap<>();
        return state;
    }

    private static final RecordCodecBuilder<StateSaverAndLoader, Map<UUID, CompoundTag>> PLAYER_CODEC = Codec.unboundedMap(UUIDUtil.AUTHLIB_CODEC, CompoundTag.CODEC).fieldOf("ethercore.players_data").forGetter(StateSaverAndLoader::getPlayers);
    private static final RecordCodecBuilder<StateSaverAndLoader, CompoundTag> WORLDLY_DATA_CODEC = CompoundTag.CODEC.fieldOf(EtherCore.MOD_ID + "worldly_data").forGetter(StateSaverAndLoader::getWorldlyData);

    private static final Codec<StateSaverAndLoader> CODEC = RecordCodecBuilder.create(stateSaverAndLoaderInstance -> stateSaverAndLoaderInstance.group(
            WORLDLY_DATA_CODEC,
            PLAYER_CODEC
    ).apply(stateSaverAndLoaderInstance, StateSaverAndLoader::new));

    private static final SavedDataType<StateSaverAndLoader> type = new SavedDataType<>(
            EtherCore.MOD_ID,
            StateSaverAndLoader::createNew,
            CODEC,
            null
    );

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        DimensionDataStorage persistentStateManager = Objects.requireNonNull(server.getLevel(Level.OVERWORLD)).getDataStorage();

        StateSaverAndLoader state = persistentStateManager.computeIfAbsent(type);

        state.setDirty();

        return state;
    }

    public static PlayerData getPlayerState(LivingEntity player) {
        StateSaverAndLoader serverState = getServerState(Objects.requireNonNull(player.level().getServer()));

        return serverState.players.computeIfAbsent(player.getUUID(), uuid -> new PlayerData());
    }

    public static CompoundTag getWorldlyData(MinecraftServer server) {
        StateSaverAndLoader serverState = getServerState(server);

        return serverState.worldlyData;
    }
}

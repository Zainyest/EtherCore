package zainyest.ethercore.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;
import zainyest.ethercore.EtherCore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/// Created with great swaths of code from [fabricmc.net/tutorial:persistent_states](https://wiki.fabricmc.net/tutorial:persistent_states)<br>
/// and [docs.fabricmc.net/develop/codecs](https://docs.fabricmc.net/develop/codecs)<br>
/// and [docs.fabricmc.net/develop/saved-data](https://docs.fabricmc.net/develop/saved-data)
public class StateSaverAndLoader extends PersistentState {

    private Map<UUID, PlayerData> players = new HashMap<>();
    private NbtCompound worldlyData = new NbtCompound();

    private StateSaverAndLoader() {}

    private void setPlayers(Map<UUID, NbtCompound> players) {
        players.forEach((uuid, nbtCompound) -> this.players.put(uuid, new PlayerData(nbtCompound)));
    }

    private Map<UUID, NbtCompound> getPlayers() {
        Map<UUID, NbtCompound> players = new HashMap<>();
        this.players.forEach((uuid, playerData) -> players.put(uuid, playerData.persistentData));
        return players;
    }

    private void setWorldlyData(NbtCompound nbtCompound) {
        this.worldlyData = nbtCompound;
    }

    private NbtCompound getWorldlyData() {
        NbtCompound out;
        out = this.worldlyData.copy();
        return out;
    }

    private StateSaverAndLoader(NbtCompound worldlyData, Map<UUID, NbtCompound> players) {
        setWorldlyData(worldlyData);
        setPlayers(players);
    }

    public static StateSaverAndLoader createNew() {
        StateSaverAndLoader state = new StateSaverAndLoader();
        // Any Worldly Variables Go Here
        state.worldlyData = new NbtCompound();
        state.players = new HashMap<>();
        return state;
    }

    private static final RecordCodecBuilder<StateSaverAndLoader, Map<UUID, NbtCompound>> PLAYER_CODEC = Codec.unboundedMap(Uuids.CODEC, NbtCompound.CODEC).fieldOf("ethercore.players_data").forGetter(StateSaverAndLoader::getPlayers);
    private static final RecordCodecBuilder<StateSaverAndLoader, NbtCompound> WORLDLY_DATA_CODEC = NbtCompound.CODEC.fieldOf(EtherCore.MOD_ID + "worldly_data").forGetter(StateSaverAndLoader::getWorldlyData);

    private static final Codec<StateSaverAndLoader> CODEC = RecordCodecBuilder.create(stateSaverAndLoaderInstance -> stateSaverAndLoaderInstance.group(
            WORLDLY_DATA_CODEC,
            PLAYER_CODEC
    ).apply(stateSaverAndLoaderInstance, StateSaverAndLoader::new));

    private static final PersistentStateType<StateSaverAndLoader> type = new PersistentStateType<>(
            EtherCore.MOD_ID,
            StateSaverAndLoader::createNew,
            CODEC,
            null
    );

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = Objects.requireNonNull(server.getWorld(World.OVERWORLD)).getPersistentStateManager();

        StateSaverAndLoader state = persistentStateManager.getOrCreate(type);

        state.markDirty();

        return state;
    }

    public static PlayerData getPlayerState(LivingEntity player) {
        StateSaverAndLoader serverState = getServerState(Objects.requireNonNull(player.getEntityWorld().getServer()));

        return serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());
    }

    public static NbtCompound getWorldlyData(MinecraftServer server) {
        StateSaverAndLoader serverState = getServerState(server);

        return serverState.worldlyData;
    }
}

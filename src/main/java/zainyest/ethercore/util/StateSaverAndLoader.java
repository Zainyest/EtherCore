package zainyest.ethercore.util;

import com.mojang.serialization.Codec;
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
import java.util.UUID;

/// Created with great swaths of code from [fabricmc.net/tutorial:persistent_states](https://wiki.fabricmc.net/tutorial:persistent_states)
public class StateSaverAndLoader extends PersistentState {

    public Map<UUID, PlayerData> players = new HashMap<>();

    private StateSaverAndLoader() {

    }

    private Map<UUID, NbtCompound> setPlayers(Map<UUID, NbtCompound> players) {
        players.forEach((uuid, nbtCompound) -> {
            this.players.put(uuid, new PlayerData(nbtCompound));
        });
        return players;
    }

    private Map<UUID, NbtCompound> getPlayers() {
        Map<UUID, NbtCompound> players = new HashMap<>();
        this.players.forEach((uuid, playerData) -> {
            players.put(uuid, playerData.persistentData);
        });
        return players;
    }

    private StateSaverAndLoader(Map<UUID, NbtCompound> players) {
        setPlayers(players);
    }

    public static StateSaverAndLoader createNew() {
        StateSaverAndLoader state = new StateSaverAndLoader();
        // Any Worldly Variables Go Here
        state.players = new HashMap<>();
        return state;
    }

    private static final Codec<StateSaverAndLoader> CODEC = Codec.unboundedMap(Uuids.CODEC, NbtCompound.CODEC).fieldOf("ethercore.players_data").codec().xmap(
            StateSaverAndLoader::new,
            StateSaverAndLoader::getPlayers
    );

    private static final PersistentStateType<StateSaverAndLoader> type = new PersistentStateType<>(
            EtherCore.MOD_ID,
            StateSaverAndLoader::createNew,
            CODEC,
            null
    );

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();

        StateSaverAndLoader state = persistentStateManager.getOrCreate(type);

        state.markDirty();

        return state;
    }

    public static PlayerData getPlayerState(LivingEntity player) {
        StateSaverAndLoader serverState = getServerState(player.getEntityWorld().getServer());

        return serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());
    }
}

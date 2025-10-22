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

// Created with great swaths of code from https://wiki.fabricmc.net/tutorial:persistent_states
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

    // TODO Is this needed?? its in the tutorial but the lower half seems to be for a different version of the game;
    // TODO: NOPE Literally never called, I think I implemented this with the above setPlayers and getPlayers functions and the new Codec System
    //@Override
//    public NbtCompound writeNbt(NbtCompound nbt) {
//        // Write per-player data
//        NbtCompound playersNbt = new NbtCompound();
//        players.forEach(((uuid, playerData) -> {
//            NbtCompound playerNbt = new NbtCompound();
//
//            playerNbt.put("ethercore.player_data", playerData.getPersistentData());
//
//            playersNbt.put(uuid.toString(), playerNbt);
//        }));
//        nbt.put("players", playersNbt);
//
//        return nbt;
//    }

//    public static StateSaverAndLoader createFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
//        StateSaverAndLoader state = new StateSaverAndLoader();
//
//        NbtCompound playersNbt = tag.getCompound("players").orElse(null);
//        assert playersNbt != null;
//        playersNbt.getKeys().forEach(key -> {
//            PlayerData playerData = new PlayerData();
//
//            playerData.persistentData = playersNbt.getCompound(key).orElse(null);
//
//            UUID uuid = UUID.fromString(key);
//            state.players.put(uuid, playerData);
//        });
//
//        return state;
//    }

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

        PlayerData playerState = serverState.players.computeIfAbsent(player.getUuid(), uuid -> new PlayerData());

        return playerState;
    }
}

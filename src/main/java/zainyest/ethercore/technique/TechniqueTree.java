package zainyest.ethercore.technique;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.util.PlayerData;
import zainyest.ethercore.util.StateSaverAndLoader;
import zainyest.ethercore.init.EtherRegistries;

public record TechniqueTree(String treeName, Technique rootTechnique) {
    public void setTechnique(ServerPlayerEntity serverPlayer, PlayerData playerData, Technique technique) {
        NbtCompound treeData = playerData.persistentData.getCompoundOrEmpty(treeName);
        treeData.put(technique.getName(), technique.toNbt());
        playerData.persistentData.put(treeName, treeData);
        syncTree(serverPlayer);
    }

    public void syncTree(ServerPlayerEntity player) {
        //ServerPlayNetworking.send(player, new PlayerDataPayload(StateSaverAndLoader.getPlayerState(player).getPersistentData()));
        StateSaverAndLoader.getPlayerState(player).markDirty(treeName);
    }

    public void recursiveUpdateTree(ServerPlayerEntity player, PlayerData dataPlayer, Technique current) {
        setTechnique(player, dataPlayer, current);
        for (Identifier id : current.getChildren()) {
            recursiveUpdateTree(player, dataPlayer, EtherRegistries.TECHNIQUES.get(id));
        }
    }

    /// TEMPORARY TEST, SHOULD ONLY UPDATE WHEN CHANGE DETECTED
    public void tickTree(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player == null) {
                EtherCore.LOGGER.info("Null Player, skipping tickPool");
                return;
            }
            PlayerData dataPlayer = StateSaverAndLoader.getPlayerState(player);

            recursiveUpdateTree(player, dataPlayer, this.rootTechnique);
        }
    }
}

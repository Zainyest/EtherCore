package zainyest.ethercore.technique;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.util.PlayerData;
import zainyest.ethercore.util.StateSaverAndLoader;
import zainyest.ethercore.init.EtherRegistries;

public record TechniqueTree(String treeName, Technique rootTechnique) {
    public void setTechnique(ServerPlayer serverPlayer, PlayerData playerData, Technique technique) {
        CompoundTag treeData = playerData.persistentData.getCompoundOrEmpty(treeName);
        treeData.put(technique.getName(), technique.toNbt());
        playerData.persistentData.put(treeName, treeData);
        syncTree(serverPlayer);
    }

    public void syncTree(ServerPlayer player) {
        StateSaverAndLoader.getPlayerState(player).markDirty(treeName);
    }

    public void recursiveUpdateTree(ServerPlayer player, PlayerData dataPlayer, Technique current) {
        setTechnique(player, dataPlayer, current);
        for (Identifier id : current.getChildren()) {
            recursiveUpdateTree(player, dataPlayer, EtherRegistries.TECHNIQUES.getValue(id));
        }
    }

    /// TEMPORARY TEST, SHOULD ONLY UPDATE WHEN CHANGE DETECTED
    public void tickTree(MinecraftServer server) {
        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            PlayerData dataPlayer = StateSaverAndLoader.getPlayerState(player);

            recursiveUpdateTree(player, dataPlayer, this.rootTechnique);
        }
    }
}

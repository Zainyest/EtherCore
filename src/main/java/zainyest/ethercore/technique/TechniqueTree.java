package zainyest.ethercore.technique;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.networking.packet.PlayerDataPayload;
import zainyest.ethercore.util.PlayerData;
import zainyest.ethercore.util.StateSaverAndLoader;
import zainyest.ethercore.util.init.EtherRegistries;

public class TechniqueTree {
    private String treeName;
    private Technique rootTechnique;

    public TechniqueTree(String treeName, Technique rootTechnique) {
        this.treeName = treeName;
        this.rootTechnique = rootTechnique;
    }

    public void setName(String name) {
        this.treeName = name;
    }
    public String getName() {
        return treeName;
    }
    public void setRootTechnique(Technique rootTechnique) {
        this.rootTechnique = rootTechnique;
    }
    public Technique getRootTechnique() {
        return this.rootTechnique;
    }

    public void setTechnique(ServerPlayerEntity serverPlayer, PlayerData playerData, Technique technique) {
        NbtCompound treeData = playerData.persistentData.getCompoundOrEmpty(treeName);
        treeData.put(technique.getName(), technique.toNbt());
        playerData.persistentData.put(treeName, treeData);
        syncTree(serverPlayer);
    }

    public void syncTree(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new PlayerDataPayload(StateSaverAndLoader.getPlayerState(player).getPersistentData()));
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

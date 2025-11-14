package zainyest.ethercore.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import zainyest.ethercore.util.EtherData;

public class PlayerTickHandler implements ServerTickEvents.StartTick{
    @Override
    public void onStartTick(MinecraftServer server) {
        //Modify PlayerData
        EtherData.updatePlayerStats(server);
        EtherData.updateTrees(server);
        EtherData.tickPools(server);
        //Send packets to dirty players
        EtherData.sendPlayerDataPayloads(server);
    }
}

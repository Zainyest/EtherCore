package zainyest.ethercore.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import zainyest.ethercore.init.EtherRegistries;
import zainyest.ethercore.technique.CausesWorldEvent;
import zainyest.ethercore.technique.Technique;
import zainyest.ethercore.technique.TickingTechniquesManager;
import zainyest.ethercore.util.EtherData;
import zainyest.ethercore.util.StateSaverAndLoader;

public class TickHandler implements ServerTickEvents.StartTick {
    @Override
    public void onStartTick(MinecraftServer server) {
        handlePlayers(server);
        handleTickingTechniques(server);
        decrementWorldlyTimers(server);
    }

    private void handlePlayers(MinecraftServer server) {
        //Modify PlayerData
        EtherData.updatePlayerStats(server);
        EtherData.updateTrees(server);
        EtherData.tickPools(server);
        //Send packets to dirty players
        EtherData.sendPlayerDataPayloads(server);
    }

    private void handleTickingTechniques(MinecraftServer server) {
        TickingTechniquesManager.tickSingletonTechniques(server);
    }

    private void decrementWorldlyTimers(MinecraftServer server) {
        NbtCompound worldlyData = StateSaverAndLoader.getWorldlyData(server);

        for (Technique technique : EtherRegistries.TECHNIQUES) {
            if (!(technique instanceof CausesWorldEvent)) {
                continue;
            }
            if (worldlyData.getInt(((CausesWorldEvent) technique).getTimeRemainingKey()).isEmpty()) {
                continue;
            }
            String key = ((CausesWorldEvent) technique).getTimeRemainingKey();

            worldlyData.putInt(key, worldlyData.getInt(key).orElse(0) - 1);
        }
    }
}

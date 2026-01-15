package zainyest.ethercore.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jspecify.annotations.NonNull;
import zainyest.ethercore.init.EtherRegistries;
import zainyest.ethercore.technique.CausesWorldEvent;
import zainyest.ethercore.technique.Technique;
import zainyest.ethercore.technique.TickingTechniquesManager;
import zainyest.ethercore.util.EtherData;
import zainyest.ethercore.util.StateSaverAndLoader;

public class TickHandler implements ServerTickEvents.StartTick {
    @Override
    public void onStartTick(@NonNull MinecraftServer server) {
        ProfilerFiller profilerFiller = Profiler.get();
        profilerFiller.push("ethercore:playerTicks");
        handlePlayers(server);
        profilerFiller.popPush("ethercore:techniqueTicks");
        handleTickingTechniques(server);
        profilerFiller.popPush("ethercore:worldlyTimerTicks");
        decrementWorldlyTimers(server);
        profilerFiller.pop();
    }

    private void handlePlayers(MinecraftServer server) {
        //Modify PlayerData
        EtherData.updateTrees(server);
        EtherData.tickPools(server);
        //Send packets to dirty players
        EtherData.sendPlayerDataPayloads(server);
    }

    private void handleTickingTechniques(MinecraftServer server) {
        TickingTechniquesManager.tickSingletonTechniques(server);
    }

    private void decrementWorldlyTimers(MinecraftServer server) {
        CompoundTag worldlyData = StateSaverAndLoader.getWorldlyData(server);

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

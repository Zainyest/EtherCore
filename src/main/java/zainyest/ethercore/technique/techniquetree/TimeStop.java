package zainyest.ethercore.technique.techniquetree;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.init.Sounds;
import zainyest.ethercore.init.StatusEffects;
import zainyest.ethercore.technique.ActivatedTechnique;
import zainyest.ethercore.technique.CausesWorldEvent;
import zainyest.ethercore.technique.TickingTechniquesManager;
import zainyest.ethercore.util.StateSaverAndLoader;

import java.util.List;

public class TimeStop extends ActivatedTechnique implements CausesWorldEvent {

    public final String TIME_REMAINING = this.getName() + ".time_remaining";

    public TimeStop(String name, Identifier[] parents, Identifier[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    public TimeStop() {
        super("time_stop",
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "mental_acuity")},
                new Identifier[0],
                "active",
                "ethercore.text.time_stop.description",
                Identifier.of(EtherCore.MOD_ID, "icon.png"));

    }

    private void begin(MinecraftServer server, ServerPlayerEntity serverPlayer) {
        //PlayerEtherStatsView playerStats =  PlayerEtherStats.fromPlayerData(StateSaverAndLoader.getPlayerState(serverPlayer));
        //int duration = playerStats.statViewList().get(EtherStats.POWER.name()).getStatTotal() * 20; // duration in ticks
        int duration = 30 * 20; // duration in ticks
        NbtCompound worldlyData = StateSaverAndLoader.getWorldlyData(server);

        if (!worldlyData.contains(TIME_REMAINING)) {
            worldlyData.putInt(TIME_REMAINING, duration);
        } else if (worldlyData.getInt(TIME_REMAINING).orElse(0) < duration) {
            worldlyData.putInt(TIME_REMAINING, duration);
        }

        serverPlayer.getEntityWorld().playSound(null, serverPlayer.getBlockPos(), Sounds.TIME_STOP, SoundCategory.PLAYERS, 1f, 1f);
        serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.TEMPORAL_IMMUNITY_EFFECT, duration, 0, false, false));
        server.getTickManager().setFrozen(true);
    }

    private void tryEnd(MinecraftServer server, ServerPlayerEntity serverPlayer, List<String> slatedForRemoval) {
        NbtCompound worldlyData = StateSaverAndLoader.getWorldlyData(server);

        if (worldlyData.contains(TIME_REMAINING)) {
            if (worldlyData.getInt(TIME_REMAINING).orElse(0) <= 0) {
                worldlyData.remove(TIME_REMAINING);
                server.getTickManager().setFrozen(false);
                serverPlayer.getEntityWorld().playSound(null, serverPlayer.getBlockPos(), Sounds.TIME_RESUME, SoundCategory.PLAYERS, 1f, 1f);
                slatedForRemoval.add(this.getName() + "." + serverPlayer.getUuidAsString());
            }
        } else {
            server.getTickManager().setFrozen(false);
            serverPlayer.getEntityWorld().playSound(null, serverPlayer.getBlockPos(), Sounds.TIME_RESUME, SoundCategory.PLAYERS, 1f, 1f);
            slatedForRemoval.add(this.getName() + "." + serverPlayer.getUuidAsString());
        }
    }

    @Override
    public void manifest(MinecraftServer server, ServerPlayerEntity serverPlayer) {
        //get the cast time, once cast time completes do:
        begin(server, serverPlayer);
        TickingTechniquesManager.addTechniqueInstance(server, serverPlayer, this);
    }

    /// Should only be called if this.isActive(serverPlayer) == true
    @Override
    public void tick(MinecraftServer server, ServerPlayerEntity serverPlayer, List<String> slatedForRemoval) {
        tryEnd(server, serverPlayer, slatedForRemoval);
    }

    @Override
    public String getTimeRemainingKey() {
        return this.TIME_REMAINING;
    }
}

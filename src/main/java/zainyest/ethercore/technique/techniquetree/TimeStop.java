package zainyest.ethercore.technique.techniquetree;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.init.Sounds;
import zainyest.ethercore.init.StatusEffects;
import zainyest.ethercore.technique.ActivatedTechnique;
import zainyest.ethercore.technique.CausesWorldEvent;
import zainyest.ethercore.technique.TickingTechnique;
import zainyest.ethercore.technique.TickingTechniquesManager;
import zainyest.ethercore.util.StateSaverAndLoader;

import java.util.List;

public class TimeStop extends ActivatedTechnique implements CausesWorldEvent, TickingTechnique {

    public final String TIME_REMAINING = this.getName() + ".time_remaining";

    public TimeStop(String name, Identifier[] parents, Identifier[] children, Identifier icon) {
        super(name, parents, children, icon);
    }

    public TimeStop() {
        super("time_stop",
                new Identifier[]{Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "temporal_immunity")},
                new Identifier[0],
                Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "icon.png"));

    }

    private void begin(MinecraftServer server, ServerPlayer serverPlayer) {
        //PlayerEtherStatsView playerStats =  PlayerEtherStats.fromPlayerData(StateSaverAndLoader.getPlayerState(serverPlayer));
        //int duration = playerStats.statViewList().get(EtherStats.POWER.name()).getStatTotal() * 20; // duration in ticks
        int duration = 30 * 20; // duration in ticks
        CompoundTag worldlyData = StateSaverAndLoader.getWorldlyData(server);

        if (!worldlyData.contains(TIME_REMAINING)) {
            worldlyData.putInt(TIME_REMAINING, duration);
        } else if (worldlyData.getInt(TIME_REMAINING).orElse(0) < duration) {
            worldlyData.putInt(TIME_REMAINING, duration);
        }

        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), Sounds.TIME_STOP, SoundSource.PLAYERS, 1f, 1f);
        serverPlayer.addEffect(new MobEffectInstance(StatusEffects.TEMPORAL_IMMUNITY_EFFECT, duration, 0, false, false));
        server.tickRateManager().setFrozen(true);
    }

    private void tryEnd(MinecraftServer server, ServerPlayer serverPlayer, List<String> slatedForRemoval) {
        CompoundTag worldlyData = StateSaverAndLoader.getWorldlyData(server);

        if (worldlyData.contains(TIME_REMAINING)) {
            if (worldlyData.getInt(TIME_REMAINING).orElse(0) <= 0) {
                worldlyData.remove(TIME_REMAINING);
                server.tickRateManager().setFrozen(false);
                serverPlayer.level().playSound(null, serverPlayer.blockPosition(), Sounds.TIME_RESUME, SoundSource.PLAYERS, 1f, 1f);
                slatedForRemoval.add(this.getName() + "." + serverPlayer.getStringUUID());
            }
        } else {
            server.tickRateManager().setFrozen(false);
            serverPlayer.level().playSound(null, serverPlayer.blockPosition(), Sounds.TIME_RESUME, SoundSource.PLAYERS, 1f, 1f);
            slatedForRemoval.add(this.getName() + "." + serverPlayer.getStringUUID());
        }
    }

    @Override
    public void manifest(MinecraftServer server, ServerPlayer serverPlayer) {
        //get the cast time, once cast time completes do:
        begin(server, serverPlayer);
        TickingTechniquesManager.addSingletonTechniqueInstance(server, serverPlayer, this);
    }

    /// Should only be called if this.isActive(serverPlayer) == true
    @Override
    public void tick(MinecraftServer server, ServerPlayer serverPlayer, List<String> slatedForRemoval) {
        tryEnd(server, serverPlayer, slatedForRemoval);
    }

    @Override
    public String getTimeRemainingKey() {
        return this.TIME_REMAINING;
    }
}

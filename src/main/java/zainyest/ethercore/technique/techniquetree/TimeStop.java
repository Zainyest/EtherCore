package zainyest.ethercore.technique.techniquetree;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.init.StatusEffects;
import zainyest.ethercore.technique.ActivatedTechnique;

public class TimeStop extends ActivatedTechnique {
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

    @Override
    public void manifest(MinecraftServer server, ServerPlayerEntity serverPlayer) {

        //get the cast time, once cast time completes do:
        serverPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.TEMPORAL_IMMUNITY_EFFECT, 5 * 20, 0, false, false));
        server.getTickManager().setFrozen(true);
        // TODO get duration and wait for duration, then unfreeze
        // store duration on caster or world, decrement on tick, when <= 0 unfreeze
        // TODO implement "Temporal Immunity"; effect, dataComponent, or player attribute
        // /particle ethercore:time_stop_particle ~ ~1 ~ 5 5 5 10 100 normal @s
    }

    /// Should only be called if this.isActive(serverPlayer) == true
    @Override
    public void tick(MinecraftServer server, ServerPlayerEntity serverPlayer) {

    }
}

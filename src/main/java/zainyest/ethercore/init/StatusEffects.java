package zainyest.ethercore.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.effect.TemporalImmunityEffect;

public class StatusEffects {
    public static final Holder<MobEffect> TEMPORAL_IMMUNITY_EFFECT = registerStatusEffect("temporal_immunity_effect", new TemporalImmunityEffect());

    private static <T extends MobEffect> Holder.Reference<T> registerStatusEffect(String name, T statusEffect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, name), statusEffect);
    }

    public static void init() {

    }
}

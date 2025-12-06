package zainyest.ethercore.init;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.effect.TemporalImmunityEffect;

public class StatusEffects {
    public static final RegistryEntry<StatusEffect> TEMPORAL_IMMUNITY_EFFECT = registerStatusEffect("temporal_immunity_effect", new TemporalImmunityEffect());

    private static <T extends StatusEffect> RegistryEntry.Reference<T> registerStatusEffect(String name, T statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(EtherCore.MOD_ID, name), statusEffect);
    }

    public static void init() {

    }
}

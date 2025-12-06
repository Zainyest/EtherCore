package zainyest.ethercore.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class TemporalImmunityEffect extends StatusEffect {
    protected TemporalImmunityEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public TemporalImmunityEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xfffbf236);
    }
}

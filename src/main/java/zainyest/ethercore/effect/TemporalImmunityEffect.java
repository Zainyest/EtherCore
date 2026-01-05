package zainyest.ethercore.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class TemporalImmunityEffect extends MobEffect {
    protected TemporalImmunityEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public TemporalImmunityEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xfffbf236);
    }
}

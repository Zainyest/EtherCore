package zainyest.ethercore.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.NonNull;
import zainyest.ethercore.init.DataAttachments;

public class TemporalImmunityEffect extends MobEffect {
    public TemporalImmunityEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xfffbf236);
    }

    @Override
    public void onEffectAdded(@NonNull LivingEntity livingEntity, int i) {
        super.onEffectAdded(livingEntity, i);
        livingEntity.setAttached(DataAttachments.IS_TEMPORALLY_IMMUNE, true);
    }

    public void onEffectRemoved(@NonNull LivingEntity livingEntity) {
        livingEntity.setAttached(DataAttachments.IS_TEMPORALLY_IMMUNE, false);
    }
}

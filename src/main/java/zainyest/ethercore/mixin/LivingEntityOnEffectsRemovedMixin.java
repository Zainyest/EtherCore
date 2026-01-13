package zainyest.ethercore.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zainyest.ethercore.effect.TemporalImmunityEffect;
import zainyest.ethercore.init.StatusEffects;

import java.util.Collection;

@Mixin(LivingEntity.class)
public class LivingEntityOnEffectsRemovedMixin {
    @Inject(at = @At(value = "HEAD"), method = "onEffectsRemoved")
    public void onEffectsRemoved(Collection<MobEffectInstance> collection, CallbackInfo ci) {
        if (!((LivingEntity) (Object) this).level().isClientSide()) {
            for (MobEffectInstance mobEffectInstance : collection) {
                if (mobEffectInstance.getEffect() == StatusEffects.TEMPORAL_IMMUNITY_EFFECT) {
                    ((TemporalImmunityEffect) mobEffectInstance.getEffect().value()).onEffectRemoved((LivingEntity) (Object) this);
                }
            }
        }
    }
}

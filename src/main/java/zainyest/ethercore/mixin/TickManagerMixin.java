package zainyest.ethercore.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import net.minecraft.world.tick.TickManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zainyest.ethercore.init.StatusEffects;

import java.util.Objects;

@Mixin(TickManager.class)
public abstract class TickManagerMixin {
    @Shadow
    public abstract boolean isFrozen();

    @Inject(at = @At("HEAD"), method = "shouldSkipTick", cancellable = true)
    private void shouldSkipTick(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof PlayerEntity && this.isFrozen()) {
            if (((PlayerEntity) entity).getGameMode() == GameMode.SURVIVAL || ((PlayerEntity) entity).getGameMode() == GameMode.ADVENTURE) {
                cir.setReturnValue(true);
            }
        }

        if (entity instanceof LivingEntity && this.isFrozen()) {
            if (Objects.requireNonNull(entity.getEntity()).hasStatusEffect(StatusEffects.TEMPORAL_IMMUNITY_EFFECT)) {
                cir.setReturnValue(false);
            }
        }
    }
}

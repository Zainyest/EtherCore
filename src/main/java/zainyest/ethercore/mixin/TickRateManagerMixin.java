package zainyest.ethercore.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.TickRateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zainyest.ethercore.init.StatusEffects;

import java.util.Objects;

@Mixin(TickRateManager.class)
public abstract class TickRateManagerMixin {
    @Shadow
    public abstract boolean isFrozen();

    @Inject(at = @At("HEAD"), method = "isEntityFrozen", cancellable = true)
    private void shouldSkipTick(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        boolean dirty = false;
        boolean returnVal = false;
        if (entity instanceof Player && this.isFrozen()) {
            if (((Player) entity).gameMode() == GameType.SURVIVAL || ((Player) entity).gameMode() == GameType.ADVENTURE) {
                dirty = true;
                returnVal = true;
            }
        }

        if (entity instanceof LivingEntity && this.isFrozen() && entity.asLivingEntity() != null) {
            if (Objects.requireNonNull(entity.asLivingEntity()).hasEffect(StatusEffects.TEMPORAL_IMMUNITY_EFFECT)) {
                dirty = true;
                returnVal = false;
            }
        }

        if (entity.level().isClientSide()) { // Unfreeze client-side entities
            dirty = true;
            returnVal = false;
        }

        if (dirty) {
            cir.setReturnValue(returnVal);
        }
    }
}

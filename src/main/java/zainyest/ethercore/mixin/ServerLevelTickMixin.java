package zainyest.ethercore.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import zainyest.ethercore.init.StatusEffects;

import java.util.Objects;

@Mixin(net.minecraft.server.level.ServerLevel.class)
public class ServerLevelTickMixin {

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/TickRateManager;isEntityFrozen(Lnet/minecraft/world/entity/Entity;)Z"), method = "method_31420(Lnet/minecraft/world/TickRateManager;Lnet/minecraft/util/profiling/ProfilerFiller;Lnet/minecraft/world/entity/Entity;)V")
    private boolean shouldSkipTick(TickRateManager instance, Entity entity, Operation<Boolean> original) {
        boolean dirty = false;
        boolean returnVal = false;
        if (entity instanceof Player && instance.isFrozen()) {
            if (((Player) entity).gameMode() == GameType.SURVIVAL || ((Player) entity).gameMode() == GameType.ADVENTURE) {
                dirty = true;
                returnVal = true;
            }
        }

        if (entity instanceof LivingEntity && instance.isFrozen() && entity.asLivingEntity() != null) {
            if (Objects.requireNonNull(entity.asLivingEntity()).hasEffect(StatusEffects.TEMPORAL_IMMUNITY_EFFECT)) {
                dirty = true;
                returnVal = false;
            }
        }

        if (dirty) {
            return original.call(instance, entity) && returnVal;
        }

        return original.call(instance, entity);
    }
}

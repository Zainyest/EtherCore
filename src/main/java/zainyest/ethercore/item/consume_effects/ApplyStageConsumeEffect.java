package zainyest.ethercore.item.consume_effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import zainyest.ethercore.init.ConsumeEffectTypes;
import zainyest.ethercore.init.EtherRegistries;
import zainyest.ethercore.init.Stages;

public record ApplyStageConsumeEffect(Identifier stageKey) implements ConsumeEffect {
    public static final MapCodec<ApplyStageConsumeEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance
                    .group(Identifier.CODEC.optionalFieldOf("stage_key", EtherRegistries.STAGES.getKey(Stages.ENERGY_GATHERING)).forGetter(ApplyStageConsumeEffect::stageKey))
                    .apply(instance, ApplyStageConsumeEffect::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ApplyStageConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            Identifier.STREAM_CODEC, ApplyStageConsumeEffect::stageKey, ApplyStageConsumeEffect::new);


    @Override
    public @NonNull Type<? extends ConsumeEffect> getType() {
        return ConsumeEffectTypes.APPLY_STAGE;
    }

    @Override
    public boolean apply(@NonNull Level level, @NonNull ItemStack itemStack, @NonNull LivingEntity livingEntity) {
        if (level.isClientSide()) {
            return false;
        }
        if (!(livingEntity instanceof ServerPlayer)) {
            return false;
        }
        if (EtherRegistries.STAGES.getValue(this.stageKey()) == null) {
            return false;
        }
        EtherRegistries.STAGES.getValue(this.stageKey()).apply(level.getServer(), (ServerPlayer) livingEntity);
        return true;
    }
}

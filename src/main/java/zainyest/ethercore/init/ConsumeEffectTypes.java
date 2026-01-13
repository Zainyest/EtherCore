package zainyest.ethercore.init;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.item.consume_effects.ApplyStageConsumeEffect;

public class ConsumeEffectTypes {

    public static final ConsumeEffect.Type<ApplyStageConsumeEffect> APPLY_STAGE = register("apply_stage", ApplyStageConsumeEffect.CODEC, ApplyStageConsumeEffect.STREAM_CODEC);


    private static <T extends ConsumeEffect> ConsumeEffect.Type<T> register(String name, MapCodec<T> mapCodec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
        return Registry.register(BuiltInRegistries.CONSUME_EFFECT_TYPE, EtherCore.id(name), new ConsumeEffect.Type<>(mapCodec, streamCodec));
    }

    public static void init() {}
}

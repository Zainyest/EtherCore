package zainyest.ethercore.networking.payload;

import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.Identifier;

import static zainyest.ethercore.EtherCore.MOD_ID;


public record LearnTechniquePayload(Tag technique) implements CustomPacketPayload {
    public static final Identifier LEARN_TECHNIQUE_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MOD_ID, "learn_technique_payload_id");
    public static final CustomPacketPayload.Type<LearnTechniquePayload> ID = new CustomPacketPayload.Type<>(LEARN_TECHNIQUE_PAYLOAD_ID);
    public static final StreamCodec<FriendlyByteBuf, LearnTechniquePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.TAG,
            LearnTechniquePayload::technique,
            LearnTechniquePayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

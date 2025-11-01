package zainyest.ethercore.networking.packet;

import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static zainyest.ethercore.EtherCore.MOD_ID;


public record LearnTechniquePayload(NbtElement technique) implements CustomPayload {
    public static final Identifier LEARN_TECHNIQUE_PAYLOAD_ID = Identifier.of(MOD_ID, "learn_technique_payload_id");
    public static final CustomPayload.Id<LearnTechniquePayload> ID = new CustomPayload.Id<>(LEARN_TECHNIQUE_PAYLOAD_ID);
    public static final PacketCodec<PacketByteBuf, LearnTechniquePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.NBT_ELEMENT,
            LearnTechniquePayload::technique,
            LearnTechniquePayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

package zainyest.ethercore.networking.packet;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

import java.util.UUID;

import static zainyest.ethercore.EtherCore.MOD_ID;

public record MeridianMenuPayload(UUID uuid) implements CustomPayload {
    public static final Identifier MERIDIAN_PAYLOAD_ID = Identifier.of(MOD_ID, "meridian_payload_id");
    public static final CustomPayload.Id<MeridianMenuPayload> ID = new CustomPayload.Id<>(MERIDIAN_PAYLOAD_ID);
    public static final PacketCodec<PacketByteBuf, MeridianMenuPayload> CODEC = PacketCodec.tuple(
            Uuids.PACKET_CODEC,
            MeridianMenuPayload::uuid,
            MeridianMenuPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

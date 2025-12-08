package zainyest.ethercore.networking.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;

public record TimeStopTestPayload(int duration) implements CustomPayload {
    public static final Identifier TIME_STOP_TEST_PAYLOAD_ID = Identifier.of(EtherCore.MOD_ID, "time_stop_test_payload_id");
    public static CustomPayload.Id<TimeStopTestPayload> ID = new CustomPayload.Id<>(TIME_STOP_TEST_PAYLOAD_ID);
    public static final PacketCodec<PacketByteBuf, TimeStopTestPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            TimeStopTestPayload::duration,
            TimeStopTestPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

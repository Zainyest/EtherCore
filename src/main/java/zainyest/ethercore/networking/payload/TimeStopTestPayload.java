package zainyest.ethercore.networking.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;

public record TimeStopTestPayload(int duration) implements CustomPacketPayload {
    public static final Identifier TIME_STOP_TEST_PAYLOAD_ID = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "time_stop_test_payload_id");
    public static CustomPacketPayload.Type<TimeStopTestPayload> ID = new CustomPacketPayload.Type<>(TIME_STOP_TEST_PAYLOAD_ID);
    public static final StreamCodec<FriendlyByteBuf, TimeStopTestPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            TimeStopTestPayload::duration,
            TimeStopTestPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

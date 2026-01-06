package zainyest.ethercore.networking.payload;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

import static zainyest.ethercore.EtherCore.MOD_ID;

public record PlayerDataPayload(CompoundTag persistentData) implements CustomPacketPayload {
    public static final Identifier PLAYER_DATA_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MOD_ID, "player_data_payload_id");
    public static final CustomPacketPayload.Type<PlayerDataPayload> ID = new CustomPacketPayload.Type<>(PLAYER_DATA_PAYLOAD_ID);
    public static final StreamCodec<FriendlyByteBuf, PlayerDataPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG,
            PlayerDataPayload::persistentData,
            PlayerDataPayload::new);

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}

package zainyest.ethercore.networking.payload;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static zainyest.ethercore.EtherCore.MOD_ID;

public record PlayerDataPayload(NbtCompound persistentData) implements CustomPayload {
    public static final Identifier PLAYER_DATA_PAYLOAD_ID = Identifier.of(MOD_ID, "player_data_payload_id");
    public static final CustomPayload.Id<PlayerDataPayload> ID = new CustomPayload.Id<>(PLAYER_DATA_PAYLOAD_ID);
    public static final PacketCodec<PacketByteBuf, PlayerDataPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.NBT_COMPOUND,
            PlayerDataPayload::persistentData,
            PlayerDataPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

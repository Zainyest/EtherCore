package zainyest.ethercore.networking.packet;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static zainyest.ethercore.EtherCore.MOD_ID;

public record PlayerDataPayload(NbtCompound persistentData) implements CustomPayload {
    public static final Identifier ETHER_POOL_ID = Identifier.of(MOD_ID, "ether_pool");
    public static final CustomPayload.Id<PlayerDataPayload> ID = new CustomPayload.Id<>(ETHER_POOL_ID);
    public static final PacketCodec<PacketByteBuf, PlayerDataPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.NBT_COMPOUND,
            PlayerDataPayload::persistentData,
            PlayerDataPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

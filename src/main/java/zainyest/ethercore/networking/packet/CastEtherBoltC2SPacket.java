package zainyest.ethercore.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import zainyest.ethercore.entity.EtherBoltEntity;
import zainyest.ethercore.util.EtherData;
import zainyest.ethercore.util.IEntityDataSaver;

public class CastEtherBoltC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //Only happens on the server
        if (EtherData.ETHER.getVal((IEntityDataSaver) player) > 50) {
            player.sendMessageToClient(Text.literal("ETHER BOLT used, Ether: " + EtherData.ETHER.remove((IEntityDataSaver) player, 50)).fillStyle(Style.EMPTY.withColor(Formatting.GOLD)), true);
            player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.PLAYERS);

            EtherBoltEntity etherBoltEntity = new EtherBoltEntity(player.getWorld(), player);
            etherBoltEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.5F, 0F);

            player.getWorld().spawnEntity(etherBoltEntity); // spawns entity
        } else {
            player.sendMessageToClient(Text.literal("Out of Ether").fillStyle(Style.EMPTY.withColor(Formatting.RED)), true);
        }
    }
}

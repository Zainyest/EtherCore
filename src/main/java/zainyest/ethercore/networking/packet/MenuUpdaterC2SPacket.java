package zainyest.ethercore.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import zainyest.ethercore.util.EtherData;
import zainyest.ethercore.util.IEntityDataSaver;

public class MenuUpdaterC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //Only happens on the server
        player.sendMessageToClient(Text.literal(EtherData.getEther((IEntityDataSaver) player)+ " / " + EtherData.getMaxEther((IEntityDataSaver) player)).fillStyle(Style.EMPTY.withColor(Formatting.AQUA)), true);
    }
}

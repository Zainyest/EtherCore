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
        player.sendMessageToClient(Text.literal(EtherData.ETHER.getVal((IEntityDataSaver) player)+ " / " + EtherData.ETHER.getMax((IEntityDataSaver) player)).fillStyle(Style.EMPTY.withColor(Formatting.AQUA)), false);
        player.sendMessageToClient(Text.literal(EtherData.STAMINA.getVal((IEntityDataSaver) player)+ " / " + EtherData.STAMINA.getMax((IEntityDataSaver) player)).fillStyle(Style.EMPTY.withColor(Formatting.GREEN)), false);
        player.sendMessageToClient(Text.literal(EtherData.MENTAL_ENERGY.getVal((IEntityDataSaver) player)+ " / " + EtherData.MENTAL_ENERGY.getMax((IEntityDataSaver) player)).fillStyle(Style.EMPTY.withColor(Formatting.GOLD)), false);
    }
}

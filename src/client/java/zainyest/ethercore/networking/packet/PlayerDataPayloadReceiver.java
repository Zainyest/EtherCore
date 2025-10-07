package zainyest.ethercore.networking.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import zainyest.ethercore.util.EtherData;

public class PlayerDataPayloadReceiver implements ClientPlayNetworking.PlayPayloadHandler<PlayerDataPayload> {
    @Override
    public void receive(PlayerDataPayload payload, ClientPlayNetworking.Context context) {
        NbtCompound data = payload.persistentData();
        context.player().sendMessage(Text.literal(EtherData.ETHER.getVal(data)+ " / " + EtherData.ETHER.getMax(data)).fillStyle(Style.EMPTY.withColor(Formatting.AQUA)), false);
        context.player().sendMessage(Text.literal(EtherData.STAMINA.getVal(data)+ " / " + EtherData.STAMINA.getMax(data)).fillStyle(Style.EMPTY.withColor(Formatting.GREEN)), false);
        context.player().sendMessage(Text.literal(EtherData.MENTAL_ENERGY.getVal(data)+ " / " + EtherData.MENTAL_ENERGY.getMax(data)).fillStyle(Style.EMPTY.withColor(Formatting.GOLD)), false);
    }
}

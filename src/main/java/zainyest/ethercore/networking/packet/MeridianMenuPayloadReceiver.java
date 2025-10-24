package zainyest.ethercore.networking.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import zainyest.ethercore.screenhandler.MeridianScreenHandler;

public class MeridianMenuPayloadReceiver implements ServerPlayNetworking.PlayPayloadHandler<MeridianMenuPayload> {
    @Override
    public void receive(MeridianMenuPayload meridianMenuPayload, ServerPlayNetworking.Context context) {
        context.player().openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player) -> new MeridianScreenHandler(syncId, playerInventory, player, ScreenHandlerContext.create(context.player().getEntityWorld(), context.player().getBlockPos())), Text.translatable("key.ethercore.meridian_screen")));
    }
}

package zainyest.ethercore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import zainyest.ethercore.event.KeyInputHandler;
import zainyest.ethercore.hud.EtherHudOverlay;
import zainyest.ethercore.networking.ModPacketsClient;

public class EtherCoreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		KeyInputHandler.register();
		ModPacketsClient.registerS2CPackets();
		EntityRendererRegistry.register(EtherCore.EtherBoltEntityType, (context) -> new FlyingItemEntityRenderer(context));

		HudRenderCallback.EVENT.register(new EtherHudOverlay());
	}
}

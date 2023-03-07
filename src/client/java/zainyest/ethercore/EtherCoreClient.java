package zainyest.ethercore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import zainyest.ethercore.event.KeyInputHandler;
import zainyest.ethercore.networking.ModPackets;

public class EtherCoreClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		KeyInputHandler.register();
		ModPackets.registerS2CPackets();
		EntityRendererRegistry.register(EtherCore.EtherBoltEntityType, (context) -> new FlyingItemEntityRenderer(context));
	}
}
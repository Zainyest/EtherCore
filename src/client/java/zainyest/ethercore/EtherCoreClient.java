package zainyest.ethercore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.util.Identifier;
import zainyest.ethercore.event.KeyInputHandler;
import zainyest.ethercore.hud.EtherHudOverlay;
import zainyest.ethercore.networking.ModPacketsClient;
import zainyest.ethercore.util.ClientPlayerData;

public class EtherCoreClient implements ClientModInitializer {
    public static ClientPlayerData clientPlayerData = new ClientPlayerData();
	@Override
	public void onInitializeClient() {
		KeyInputHandler.register();
		ModPacketsClient.registerS2CPackets();
        HudElementRegistry.attachElementBefore(VanillaHudElements.HEALTH_BAR, Identifier.of(EtherCore.MOD_ID, "before_health"), EtherHudOverlay::render);
	}
}

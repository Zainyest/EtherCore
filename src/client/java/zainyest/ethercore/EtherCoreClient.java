package zainyest.ethercore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.util.Identifier;
import zainyest.ethercore.event.KeyInputHandler;
import zainyest.ethercore.hud.EtherHudOverlay;
import zainyest.ethercore.init.ParticleFactories;
import zainyest.ethercore.networking.ModPayloadsClient;
import zainyest.ethercore.util.ClientPlayerData;

import static zainyest.ethercore.EtherCore.LOGGER;

public class EtherCoreClient implements ClientModInitializer {
    public static ClientPlayerData clientPlayerData = new ClientPlayerData();
	@Override
	public void onInitializeClient() {
        LOGGER.info("Loading EtherCore Client...");

        initRegistries();

        LOGGER.info("EtherCore Client Loaded!");
	}

    private void initRegistries() {
        KeyInputHandler.register();
        ModPayloadsClient.registerS2CPayloadReceivers();
        HudElementRegistry.attachElementBefore(VanillaHudElements.HEALTH_BAR, Identifier.of(EtherCore.MOD_ID, "before_health"), EtherHudOverlay::render);
        ParticleFactories.init();
    }
}

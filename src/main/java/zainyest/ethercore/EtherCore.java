package zainyest.ethercore;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zainyest.ethercore.event.PlayerTickHandler;
import zainyest.ethercore.init.*;
import zainyest.ethercore.networking.ModPayloads;

public class EtherCore implements ModInitializer {
	public static final String MOD_ID = "ethercore";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Loading EtherCore...");

        // register mod objects
		ModPayloads.registerC2SPayloads();
        ModPayloads.registerS2CPayloads();
		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
        initRegistries();

        LOGGER.info("EtherCore Loaded!");
	}

    private void initRegistries() {
        EtherRegistries.init();
        EtherStats.init();
        Techniques.init();
        EtherPools.init();
        TechniqueTrees.init();
        Particles.init();
    }
}

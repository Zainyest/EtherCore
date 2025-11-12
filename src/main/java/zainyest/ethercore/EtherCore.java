package zainyest.ethercore;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zainyest.ethercore.event.PlayerTickHandler;
import zainyest.ethercore.networking.ModPackets;
import zainyest.ethercore.util.init.*;

public class EtherCore implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "ethercore";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Loading EtherCore...");

        // register mod objects
		ModPackets.registerC2SPackets();
		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
        initRegistries();

        LOGGER.info("EtherCore Loaded!");
	}

    private void initRegistries() {
        EtherRegistries.init();
        Techniques.init();
        EtherStats.init();
        EtherPools.init();
        TechniqueTrees.init();
    }
}

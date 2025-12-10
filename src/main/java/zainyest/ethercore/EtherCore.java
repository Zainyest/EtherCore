package zainyest.ethercore;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zainyest.ethercore.event.TickHandler;
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
        ModPayloads.registerC2SReceivers();
		ServerTickEvents.START_SERVER_TICK.register(new TickHandler());
        initRegistries();

        LOGGER.info("EtherCore Loaded!");
	}

    private void initRegistries() {
        //Custom
        EtherRegistries.init();
        EtherStats.init();
        Techniques.init();
        EtherPools.init();
        TechniqueTrees.init();
        //Vanilla
        Particles.init();
        StatusEffects.init();
        Sounds.init();
        Items.init();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}

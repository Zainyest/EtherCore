package zainyest.ethercore.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;

public class Sounds {
    public static final SoundEvent TIME_STOP = registerSound("time_stop");
    public static final SoundEvent TIME_RESUME = registerSound("time_resume");

    private static SoundEvent registerSound(String sound) {
        Identifier id = Identifier.of(EtherCore.MOD_ID, sound);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void init() {

    }
}

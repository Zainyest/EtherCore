package zainyest.ethercore.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;

public class Sounds {
    public static final SoundEvent TIME_STOP = registerSound("time_stop");
    public static final SoundEvent TIME_RESUME = registerSound("time_resume");

    private static SoundEvent registerSound(String sound) {
        Identifier id = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, sound);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    private static Holder.Reference<SoundEvent> registerForHolder(String name) {
        Identifier id = EtherCore.id(name);
        return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void init() {

    }
}

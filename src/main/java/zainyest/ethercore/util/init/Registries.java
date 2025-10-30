package zainyest.ethercore.util.init;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.Technique;


public class Registries {
    public static final RegistryKey<Registry<Technique>> TECHNIQUE_KEY = RegistryKey.ofRegistry(Identifier.of(EtherCore.MOD_ID, "technique"));
    public static final Registry<Technique> TECHNIQUE = FabricRegistryBuilder.createSimple(TECHNIQUE_KEY).buildAndRegister();

}

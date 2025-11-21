package zainyest.ethercore.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;

public class Particles {
    public static final SimpleParticleType TIME_STOP_PARTICLE = registerParticle("time_stop_particle", FabricParticleTypes.simple());

    private static <T extends ParticleType<?>> T registerParticle(String name, T particle) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(EtherCore.MOD_ID, name), particle);
    }

    public static void init() {

    }

}

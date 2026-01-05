package zainyest.ethercore.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;

public class Particles {
    public static final SimpleParticleType TIME_STOP_PARTICLE = registerParticle("time_stop_particle", FabricParticleTypes.simple());

    private static <T extends ParticleType<?>> T registerParticle(String name, T particle) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, name), particle);
    }

    public static void init() {

    }

}

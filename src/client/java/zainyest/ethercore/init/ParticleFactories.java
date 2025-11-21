package zainyest.ethercore.init;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import zainyest.ethercore.particle.TimeParticle;

public class ParticleFactories {
    public static void init() {
        ParticleFactoryRegistry.getInstance().register(Particles.TIME_STOP_PARTICLE, TimeParticle.Factory::new);
    }
}

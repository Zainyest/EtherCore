package zainyest.ethercore.particle;

import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.random.Random;

public class TimeParticle extends BillboardParticle {
    private final SpriteProvider spriteProvider;

    protected TimeParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider.getFirst());
        this.spriteProvider = spriteProvider;
        updateSprite(spriteProvider);
        setBoundingBoxSpacing(0.01F, 0.01F);
        this.maxAge = 65;
        this.velocityMultiplier = 0.99F;
    }

    @Override
    protected RenderType getRenderType() {
        return RenderType.PARTICLE_ATLAS_OPAQUE;
    }

    @Override
    public void tick() {
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {

            this.move(this.velocityX, this.velocityY, this.velocityZ);

//            this.velocityX = this.velocityX * this.velocityMultiplier * random.nextGaussian();
//            this.velocityY = this.velocityY * this.velocityMultiplier * random.nextGaussian();
//            this.velocityZ = this.velocityZ * this.velocityMultiplier * random.nextGaussian();
            this.velocityX = this.velocityX * random.nextGaussian();
            this.velocityY = this.velocityY * random.nextGaussian();
            this.velocityZ = this.velocityZ * random.nextGaussian();
            if (this.onGround) {
                markDead();
            }
        }

        setSprite(this.spriteProvider.getSprite(this.age, this.maxAge));

    }

    public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
            return new TimeParticle(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider());
        }
    }
}

package zainyest.ethercore.particle;

import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle.Layer;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class TimeParticle extends SingleQuadParticle {
    private final SpriteSet spriteProvider;

    protected TimeParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteSet spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider.first());
        this.spriteProvider = spriteProvider;
        setSpriteFromAge(spriteProvider);
        setSize(0.01F, 0.01F);
        this.lifetime = 65;
        this.friction = 0.99F;
    }

    @Override
    protected Layer getLayer() {
        return Layer.OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {

            this.move(this.xd, this.yd, this.zd);

//            this.velocityX = this.velocityX * this.velocityMultiplier * random.nextGaussian();
//            this.velocityY = this.velocityY * this.velocityMultiplier * random.nextGaussian();
//            this.velocityZ = this.velocityZ * this.velocityMultiplier * random.nextGaussian();
            this.xd = this.xd * random.nextGaussian();
            this.yd = this.yd * random.nextGaussian();
            this.zd = this.zd * random.nextGaussian();
            if (this.onGround) {
                remove();
            }
        }

        setSprite(this.spriteProvider.get(this.age, this.lifetime));

    }

    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new TimeParticle(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider());
        }
    }
}

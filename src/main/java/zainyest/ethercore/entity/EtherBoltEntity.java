package zainyest.ethercore.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class EtherBoltEntity extends ArrowEntity {
    public EtherBoltEntity(EntityType<? extends ArrowEntity> entityType, World world) {
        super(entityType, world);
    }
    public EtherBoltEntity(World world, LivingEntity owner) {
        super(world, owner); // null will be changed later
    }

    public EtherBoltEntity(World world, double x, double y, double z) {
        super(world, x, y, z); // null will be changed later
    }

    protected void onEntityHit(EntityHitResult entityHitResult) { // called on entity hit.
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity(); // sets a new Entity instance as the EntityHitResult (victim)
        entity.damage(DamageSource.magic(this, this.getOwner()), 5.0f); // deals damage

        if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            livingEntity.playSound(SoundEvents.BLOCK_ANVIL_HIT, 2F, 1F); // plays a sound for the entity hit only
        }
    }
}

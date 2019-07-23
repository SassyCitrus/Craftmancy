package sassycitrus.craftmancy.entity;

import java.util.stream.IntStream;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityFireball extends EntityThrowable implements IEntityAdditionalSpawnData
{
    public EntityFireball(World world)
    {
        super(world);
    }

    public EntityFireball(World world, EntityLivingBase thrower)
    {
        super(world, thrower);
        this.ignoreEntity = thrower;
        this.shoot(thrower, thrower.rotationPitch, thrower.rotationYaw, 0.0f, 1.5f, 1.0f);
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        Entity entityHit = result.entityHit;

        if (entityHit != null && !entityHit.isImmuneToFire())
        {
            boolean flag = entityHit.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this.thrower), 5.0F);
            
            if (flag)
            {
                entityHit.setFire(5);
            }
        }

        this.playSound(SoundEvents.BLOCK_LAVA_POP, 2, 0.8f + rand.nextFloat() * 0.3f);

        if (this.world.isRemote)
        {
            IntStream.range(0, 8).forEach(n -> 
            {
                world.spawnParticle(EnumParticleTypes.LAVA,
                        this.posX + rand.nextFloat() - 0.5,
                        this.posY + this.height / 2 + rand.nextFloat() - 0.5,
                        this.posZ+ rand.nextFloat() - 0.5, 0, 0, 0
                );
            });
        }

        this.setDead();
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (this.world.isRemote)
        {
            this.world.spawnParticle(EnumParticleTypes.FLAME,
                    this.posX + rand.nextFloat() * 0.2 - 0.1,
                    this.posY + this.height / 2 + rand.nextFloat() * 0.2 - 0.1,
                    this.posZ + rand.nextFloat() * 0.2 - 0.1, 0, 0, 0
            );
        }

        if (this.ticksExisted > 60)
        {
            this.setDead();
        }
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0.0F;
    }

    @Override
    public boolean canRenderOnFire()
    {
        return false;
    }

    @Override
    public void writeSpawnData(ByteBuf data)
    {
        if (this.getThrower() != null)
        {
            data.writeInt(this.getThrower().getEntityId());
        }
    }

    @Override
    public void readSpawnData(ByteBuf data)
    {
        if (data.isReadable())
        {
            Entity entity = this.world.getEntityByID(data.readInt());

            if (entity instanceof EntityLivingBase)
            {
                this.thrower = (EntityLivingBase) entity;
                this.ignoreEntity = this.thrower;
            }
        }
    }
}
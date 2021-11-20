package com.infinity.entity;

import java.util.Collection;

import javax.annotation.Nullable;

import com.infinity.goals.SwellGoal;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IChargeableMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.CreeperSwellGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Cripper extends MonsterEntity implements IChargeableMob {
	   private static final DataParameter<Integer> DATA_SWELL_DIR = EntityDataManager.defineId(Cripper.class, DataSerializers.INT);
	   private static final DataParameter<Boolean> DATA_IS_POWERED = EntityDataManager.defineId(Cripper.class, DataSerializers.BOOLEAN);
	   private static final DataParameter<Boolean> DATA_IS_IGNITED = EntityDataManager.defineId(Cripper.class, DataSerializers.BOOLEAN);
	   private int oldSwell;
	   private int swell;
	   private int maxSwell = 30;
	   private int explosionRadius = 6;
	   private int droppedSkulls;

	   public Cripper(EntityType<? extends Cripper> p_32278_, World p_32279_) {
	      super(p_32278_, p_32279_);
	   }

	   protected void registerGoals() {
		   this.goalSelector.addGoal(1, new SwimGoal(this));
		   this.goalSelector.addGoal(2, new SwellGoal(this));
		   this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0D, 1.2D));
		   this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, CatEntity.class, 6.0F, 1.0D, 1.2D));
		   this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
		   this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
		   this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		   this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
		   this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
		   this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
	   }

	   public static AttributeModifierMap.MutableAttribute createAttributes() {
	      return MonsterEntity.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.MAX_HEALTH, 2005.0D);
	   }

	   public int getMaxFallDistance() {
	      return this.getTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
	   }

	   public boolean causeFallDamage(float p_149687_, float p_149688_) {
	      boolean flag = super.causeFallDamage(p_149687_, p_149688_);
	      this.swell = (int)((float)this.swell + p_149687_ * 1.5F);
	      if (this.swell > this.maxSwell - 5) {
	         this.swell = this.maxSwell - 5;
	      }

	      return flag;
	   }

	   protected void defineSynchedData() {
	      super.defineSynchedData();
	      this.entityData.define(DATA_SWELL_DIR, -1);
	      this.entityData.define(DATA_IS_POWERED, false);
	      this.entityData.define(DATA_IS_IGNITED, false);
	   }

	   public void addAdditionalSaveData(CompoundNBT p_32304_) {
	      super.addAdditionalSaveData(p_32304_);
	      if (this.entityData.get(DATA_IS_POWERED)) {
	         p_32304_.putBoolean("powered", true);
	      }

	      p_32304_.putShort("Fuse", (short)this.maxSwell);
	      p_32304_.putByte("ExplosionRadius", (byte)this.explosionRadius);
	      p_32304_.putBoolean("ignited", this.isIgnited());
	   }

	   public void readAdditionalSaveData(CompoundNBT p_32296_) {
	      super.readAdditionalSaveData(p_32296_);
	      this.entityData.set(DATA_IS_POWERED, p_32296_.getBoolean("powered"));
	      if (p_32296_.contains("Fuse", 99)) {
	         this.maxSwell = p_32296_.getShort("Fuse");
	      }

	      if (p_32296_.contains("ExplosionRadius", 99)) {
	         this.explosionRadius = p_32296_.getByte("ExplosionRadius");
	      }

	      if (p_32296_.getBoolean("ignited")) {
	         this.ignite();
	      }

	   }

	   public void tick() {
		      if (this.isAlive()) {
		         this.oldSwell = this.swell;
		         if (this.isIgnited()) {
		            this.setSwellDir(1);
		         }

		         int i = this.getSwellDir();
		         if (i > 0 && this.swell == 0) {
		            this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
		         }

		         this.swell += i;
		         if (this.swell < 0) {
		            this.swell = 0;
		         }

		         if (this.swell >= this.maxSwell) {
		            this.swell = this.maxSwell;
		            this.explodeCreeper();
		         }
		      
	      }

	      super.tick();
	   }


	   protected SoundEvent getHurtSound(DamageSource p_32309_) {
	      return SoundEvents.PIG_HURT;
	   }

	   protected SoundEvent getDeathSound() {
	      return SoundEvents.PIG_DEATH;
	   }

	   protected void dropCustomDeathLoot(DamageSource p_32292_, int p_32293_, boolean p_32294_) {
	      super.dropCustomDeathLoot(p_32292_, p_32293_, p_32294_);
	      Entity entity = p_32292_.getEntity();
	      if (entity != this && entity instanceof Cripper) {
	         Cripper creeper = (Cripper)entity;
	         if (creeper.canDropMobsSkull()) {
	            creeper.increaseDroppedSkulls();
	            this.spawnAtLocation(Items.CREEPER_HEAD);
	         }
	      }

	   }

	   public boolean doHurtTarget(Entity p_32281_) {
	      return true;
	   }

	   public boolean isPowered() {
	      return this.entityData.get(DATA_IS_POWERED);
	   }
	   @OnlyIn(Dist.CLIENT)
	   public float getSwelling(float p_32321_) {
	      return MathHelper.lerp(p_32321_, (float)this.oldSwell, (float)this.swell) / (float)(this.maxSwell - 2);
	   }

	   public int getSwellDir() {
	      return this.entityData.get(DATA_SWELL_DIR);
	   }

	   public void setSwellDir(int p_32284_) {
	      this.entityData.set(DATA_SWELL_DIR, p_32284_);
	   }

	   public void thunderHit(ServerWorld p_32286_, LightningBoltEntity p_32287_) {
	      super.thunderHit(p_32286_, p_32287_);
	      this.entityData.set(DATA_IS_POWERED, true);
	   }

	   protected ActionResultType mobInteract(PlayerEntity p_32301_, Hand p_32302_) {
	      ItemStack itemstack = p_32301_.getItemInHand(p_32302_);
	      if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
	         this.level.playSound(p_32301_, this.getX(), this.getY(), this.getZ(), SoundEvents.FLINTANDSTEEL_USE, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
	         if (!this.level.isClientSide) {
	            this.ignite();
	            itemstack.hurtAndBreak(1, p_32301_, (p_32290_) -> {
	               p_32290_.broadcastBreakEvent(p_32302_);
	            });
	         }

	         return ActionResultType.sidedSuccess(this.level.isClientSide);
	      } else {
	         return super.mobInteract(p_32301_, p_32302_);
	      }
	   }

	   private void explodeCreeper() {
	      if (!this.level.isClientSide) {
	         Explosion.Mode explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
	         float f = this.isPowered() ? 2.0F : 1.0F;
	         this.dead = true;
	         this.level.explode(this, this.getX(), this.getY(), this.getZ(), (float)this.explosionRadius * f, explosion$blockinteraction);
	         this.remove();
	         this.spawnLingeringCloud();
	      }

	   }

	   private void spawnLingeringCloud() {
	      Collection<EffectInstance> collection = this.getActiveEffects();
	      if (!collection.isEmpty()) {
	    	 AreaEffectCloudEntity areaeffectcloud = new AreaEffectCloudEntity(this.level, this.getX(), this.getY(), this.getZ());
	         areaeffectcloud.setRadius(2.5F);
	         areaeffectcloud.setRadiusOnUse(-0.5F);
	         areaeffectcloud.setWaitTime(10);
	         areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
	         areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());

	         for(EffectInstance mobeffectinstance : collection) {
	            areaeffectcloud.addEffect(new EffectInstance(mobeffectinstance));
	         }

	         this.level.addFreshEntity(areaeffectcloud);
	      }

	   }

	   public boolean isIgnited() {
	      return this.entityData.get(DATA_IS_IGNITED);
	   }

	   public void ignite() {
	      this.entityData.set(DATA_IS_IGNITED, true);
	   }

	   public boolean canDropMobsSkull() {
	      return this.isPowered() && this.droppedSkulls < 1;
	   }

	   public void increaseDroppedSkulls() {
	      ++this.droppedSkulls;
	   }
}

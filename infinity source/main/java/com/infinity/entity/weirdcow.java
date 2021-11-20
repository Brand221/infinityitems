package com.infinity.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class weirdcow extends AnimalEntity {

	public weirdcow(EntityType<? extends weirdcow> p_27557_, World p_27558_) {
		super(p_27557_, p_27558_);
		// TODO Auto-generated constructor stub
	}
	   protected void registerGoals() {
		     this.goalSelector.addGoal(0, new SwimGoal(this));
		      this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
		      this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		      this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.WHEAT), false));
		      this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
		      this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		      this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		      this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		   }

		   public static AttributeModifierMap.MutableAttribute createAttributes() {
		      return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 2000.0D).add(Attributes.MOVEMENT_SPEED, (0.2F));
		   }

		   protected SoundEvent getAmbientSound() {
		      return SoundEvents.COW_AMBIENT;
		   }

		   protected SoundEvent getHurtSound(DamageSource p_28306_) {
		      return SoundEvents.COW_HURT;
		   }

		   protected SoundEvent getDeathSound() {
		      return SoundEvents.COW_DEATH;
		   }

		   protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
		      this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
		   }

		   protected float getSoundVolume() {
		      return 0.4F;
		   }

		   public ActionResultType mobInteract(PlayerEntity p_28298_, Hand p_28299_) {
		      ItemStack itemstack = p_28298_.getItemInHand(p_28299_);
		      if (itemstack.getItem() == Items.BUCKET && !this.isBaby()) {
		         p_28298_.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
		         ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, p_28298_, Items.MILK_BUCKET.getDefaultInstance());
		         p_28298_.setItemInHand(p_28299_, itemstack1);
		         return ActionResultType.sidedSuccess(this.level.isClientSide);
		      } else {
		         return super.mobInteract(p_28298_, p_28299_);
		      }
		   }

		   public weirdcow getBreedOffspring(ServerWorld p_148890_, AgeableEntity p_148891_) {
		      return ModTypes.INFCOW.get().create(p_148890_);
		   }

		   protected float getStandingEyeHeight(Pose p_28295_, EntitySize p_28296_) {
		      return this.isBaby() ? p_28296_.height * 0.95F : 1.3F;
		   }
}

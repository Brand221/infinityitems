package com.infinity.goals;

import java.util.EnumSet;

import com.infinity.entity.Cripper;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class SwellGoal extends Goal 
{
	   private final Cripper creeper;
	   private LivingEntity target;

	   public SwellGoal(Cripper p_i1655_1_) {
	      this.creeper = p_i1655_1_;
	      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	   }

	   public boolean canUse() {
	      LivingEntity livingentity = this.creeper.getTarget();
	      return this.creeper.getSwellDir() > 0 || livingentity != null && this.creeper.distanceToSqr(livingentity) < 9.0D;
	   }

	   public void start() {
	      this.creeper.getNavigation().stop();
	      this.target = this.creeper.getTarget();
	   }

	   public void stop() {
	      this.target = null;
	   }

	   public void tick() {
	      if (this.target == null) {
	         this.creeper.setSwellDir(-1);
	      } else if (this.creeper.distanceToSqr(this.target) > 49.0D) {
	         this.creeper.setSwellDir(-1);
	      } else if (!this.creeper.getSensing().canSee(this.target)) {
	         this.creeper.setSwellDir(-1);
	      } else {
	         this.creeper.setSwellDir(1);
	      }
	   }
}

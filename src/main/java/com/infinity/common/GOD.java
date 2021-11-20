package com.infinity.common;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
public enum GOD implements IItemTier {
	GOD(10, 500000, 900.0F, 2000.0F, 15, ()->{
		return Ingredient.of(Items.NETHER_STAR);
});
	private final int level;
	   private final int uses;
	   private final float speed;
	   private final float damage;
	   private final int enchantmentValue;
	   private final LazyValue<Ingredient> repairIngredient;

	private GOD(int p_43332_, int p_43333_, float p_43334_, float p_43335_, int p_43336_, Supplier<Ingredient> p_43337_) {
		      this.level = p_43332_;
		      this.uses = p_43333_;
		      this.speed = p_43334_;
		      this.damage = p_43335_;
		      this.enchantmentValue = p_43336_;
		      this.repairIngredient = new LazyValue<>(p_43337_);}
	   public int getUses() {
		      return this.uses;
		   }

		   public float getSpeed() {
		      return this.speed;
		   }

		   public float getAttackDamageBonus() {
		      return this.damage;
		   }

		   public int getLevel() {
		      return this.level;
		   }

		   public int getEnchantmentValue() {
		      return this.enchantmentValue;
		   }

		   public Ingredient getRepairIngredient() {
		      return this.repairIngredient.get();
		   }



}

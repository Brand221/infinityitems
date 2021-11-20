package com.infinity.common;

import java.util.function.Supplier;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.LazyValue;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

public enum GODARMOR implements IArmorMaterial  {
	GOD("god", 1000, new int[] {1000, 1000, 1000, 1000}, 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 500.0F, 1.0F, () -> {
		return Ingredient.of(Items.NETHER_STAR);
	});
	 private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
	   private final String name;
	   private final int durabilityMultiplier;
	   private final int[] slotProtections;
	   private final int enchantmentValue;
	   private final SoundEvent sound;
	   private final float toughness;
	   private final float knockbackResistance;
	   private final LazyValue<Ingredient> repairIngredient;

	   private GODARMOR(String p_40474_, int p_40475_, int[] p_40476_, int p_40477_, SoundEvent p_40478_, float p_40479_, float p_40480_, Supplier<Ingredient> p_40481_) {
	      this.name = p_40474_;
	      this.durabilityMultiplier = p_40475_;
	      this.slotProtections = p_40476_;
	      this.enchantmentValue = p_40477_;
	      this.sound = p_40478_;
	      this.toughness = p_40479_;
	      this.knockbackResistance = p_40480_;
	      this.repairIngredient = new LazyValue<>(p_40481_);
	   }

	   public int getDurabilityForSlot(EquipmentSlotType p_40484_) {
	      return HEALTH_PER_SLOT[p_40484_.getIndex()] * this.durabilityMultiplier;
	   }

	   public int getDefenseForSlot(EquipmentSlotType p_40487_) {
	      return this.slotProtections[p_40487_.getIndex()];
	   }

	   public int getEnchantmentValue() {
	      return this.enchantmentValue;
	   }

	   public SoundEvent getEquipSound() {
	      return this.sound;
	   }

	   public Ingredient getRepairIngredient() {
	      return this.repairIngredient.get();
	   }

	   public String getName() {
	      return Main.MOD_ID + ":" + this.name;
	   }

	   public float getToughness() {
	      return this.toughness;
	   }

	   public float getKnockbackResistance() {
	      return this.knockbackResistance;
	   }

}

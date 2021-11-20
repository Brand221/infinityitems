package com.infinity.client.render;

import com.infinity.client.model.CripperModel;
import com.infinity.entity.Cripper;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CripperRenderer extends MobRenderer<Cripper, CripperModel<Cripper>>
{
	   private static final ResourceLocation CREEPER_LOCATION = new ResourceLocation("infinityitems","textures/entity/cripper.png");

	   public CripperRenderer(EntityRendererManager p_173958_) {
	      super(p_173958_, new CripperModel<>(), 0.5F);
	   }

	   protected void scale(Cripper p_114046_, MatrixStack p_114047_, float p_114048_) {
	      float f = p_114046_.getSwelling(p_114048_);
	      float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
	      f = MathHelper.clamp(f, 0.0F, 1.0F);
	      f = f * f;
	      f = f * f;
	      float f2 = (1.0F + f * 0.4F) * f1;
	      float f3 = (1.0F + f * 0.1F) / f1;
	      p_114047_.scale(f2, f3, f2);
	   }

	   protected float getWhiteOverlayProgress(Cripper p_114043_, float p_114044_) {
	      float f = p_114043_.getSwelling(p_114044_);
	      return (int)(f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
	   }

	   public ResourceLocation getTextureLocation(Cripper p_114041_) {
	      return CREEPER_LOCATION;
	   }
}

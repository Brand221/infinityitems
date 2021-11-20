package com.infinity.client.render;

import com.infinity.entity.weirdcow;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;

public class CowRender extends MobRenderer<weirdcow, CowModel<weirdcow>> {
	
	   private static final ResourceLocation COW_LOCATION = new ResourceLocation("infinityitems","textures/entity/weird_cow.png");

	public CowRender(EntityRendererManager context) {
		super(context, new CowModel<>(), 0.7F);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResourceLocation getTextureLocation(weirdcow p_114482_) {
		// TODO Auto-generated method stub
		return COW_LOCATION;
	}

}

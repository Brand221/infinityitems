package com.infinity.common.events;

import com.infinity.client.render.CowRender;
import com.infinity.client.render.CripperRenderer;
import com.infinity.common.Main;
import com.infinity.entity.ModTypes;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientSetup 
{
	@SubscribeEvent
	public static final void entityClientRenderer(FMLClientSetupEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(ModTypes.INFCOW.get(), CowRender::new);
		RenderingRegistry.registerEntityRenderingHandler(ModTypes.BOOM.get(), CripperRenderer::new);
	}
}

package com.infinity.entity;

import com.infinity.common.Main;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public class WeirdAttributes 
{
	@SubscribeEvent
	public static final void entityAttributCreation(EntityAttributeCreationEvent event) {
		event.put(ModTypes.INFCOW.get(), weirdcow.createAttributes().build());
		event.put(ModTypes.BOOM.get(), Cripper.createAttributes().build());
	}
}

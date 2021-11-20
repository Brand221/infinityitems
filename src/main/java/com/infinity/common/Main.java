package com.infinity.common;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

import com.infinity.common.Level.Biomes;
import com.infinity.common.events.ClientSetup;
import com.infinity.common.lists.BlockList;
import com.infinity.entity.ModTypes;
import com.infinity.world.surface.Surfaceinit;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(Main.MOD_ID)
public class Main {
	public static final String MOD_ID = "infinityitems";
	public static Main instance;
	public static final ItemGroup INFINITY_TAB = new Main.Infinity_tab("infinityitems");
	public static final Logger LOGGER = LogManager.getLogger();

	
	public Main()
	{
		instance = this;
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientsetup);
		
		Biomes.registerBiomes();
		Biomes.BIOMES.register(bus);
		Surfaceinit.SURFACE_BUILDERS.register(bus);
		ModTypes.ENTITY_TYPES.register(bus);
	}
	private void setup(final FMLCommonSetupEvent event)
	{
		RenderTypeLookup.setRenderLayer(BlockList.INFSAPLING, RenderType.cutout());
	}
	
	private void clientsetup(final FMLClientSetupEvent event)
	{
		
	}
	public void onServerStarting(FMLServerStartingEvent event)
	{
		
	}
	
	public static class Infinity_tab extends ItemGroup
	{
		public Infinity_tab(String name)
		{
			super(name);
		}

		@Override
		public ItemStack makeIcon() {
			// TODO Auto-generated method stub
			return new ItemStack(Items.NETHER_STAR);
		}
	}


}

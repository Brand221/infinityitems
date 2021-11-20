package com.infinity.world.surface;

import com.infinity.common.Main;

import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Surfaceinit {
	public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, Main.MOD_ID);
	
	public static final RegistryObject<WeirdSurfaceBuilder> WEIRDSURFACE = SURFACE_BUILDERS.register("weirdsurface", () -> new WeirdSurfaceBuilder(SurfaceBuilderConfig.CODEC));

}

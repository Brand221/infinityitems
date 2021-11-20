package com.infinity.common.Level;

import com.infinity.common.Main;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class Dimension {
	public static final RegistryKey<World> WEIRD_DIMENSION_WORLD = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Main.MOD_ID, "weird_dimension"));

}

package com.infinity.common.Level;

import java.util.function.Supplier;


import com.infinity.common.Main;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Biomes {
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Main.MOD_ID);
	
	static {
		createBiome("weird_biome", BiomeMaker::theVoidBiome);
	}
	
	public static RegistryKey<Biome> WEIRD_BIOME = registerBiome("weird_biome");
	public static RegistryKey<Biome> registerBiome(String biomeName) {
	      return RegistryKey.create(Registry.BIOME_REGISTRY , new ResourceLocation(Main.MOD_ID, biomeName));
	   }
	public static RegistryObject<Biome> createBiome(String biomeName, Supplier<Biome> biome) {
		return BIOMES.register(biomeName, biome);
	}
	public static void registerBiomes() {
		BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(WEIRD_BIOME, 10));
	}
	

}

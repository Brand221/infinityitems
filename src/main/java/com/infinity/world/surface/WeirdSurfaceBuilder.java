package com.infinity.world.surface;

import java.util.Random;

import com.infinity.common.lists.BlockList;
import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class WeirdSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
	
	public WeirdSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
		super(codec);
		// TODO Auto-generated constructor stub
	}
	
	public static final BlockState INFGRASS = BlockList.INFGRASS.defaultBlockState();
	public static final BlockState STONE = Blocks.GRAVEL.defaultBlockState();
	public static final BlockState DIRT = Blocks.DIRT.defaultBlockState();
	public static final SurfaceBuilderConfig INFGRASS_CONFIG = new SurfaceBuilderConfig(INFGRASS, DIRT, STONE);

	public void apply(Random random, IChunk chunkin, Biome biome, int x, int z,
			int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel,
			long seed, SurfaceBuilderConfig config) {
		// TODO Auto-generated method stub
		SurfaceBuilder.DEFAULT.apply(random, chunkin, biome, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, INFGRASS_CONFIG);
		
	}
	

}
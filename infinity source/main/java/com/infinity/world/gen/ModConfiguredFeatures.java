package com.infinity.world.gen;

import com.infinity.common.lists.BlockList;

import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class ModConfiguredFeatures {
	 public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> WEIRD = 
			 register("weird_tree", 
					 Feature.TREE.configured((
							 new BaseTreeFeatureConfig.Builder(
							 new SimpleBlockStateProvider(BlockList.INFLOG.defaultBlockState()), 
							 new SimpleBlockStateProvider(BlockList.INFLEAVES.defaultBlockState()), 
							 new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), 
							 new StraightTrunkPlacer(4, 2, 0), 
							 new TwoLayerFeature(1, 0, 1))).ignoreVines().build()));
	 
	 private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String p_127056_, ConfiguredFeature<FC, ?> p_127057_) {
	      return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, p_127056_, p_127057_);
     }
	 
	 public static final ConfiguredFeature<?, ?> OP_Diamond = register("of_diamond", 
			 Feature.ORE.configured(
			 new OreFeatureConfig
			 (OreFeatureConfig.FillerBlockType.NATURAL_STONE, 
					 Blocks.DIAMOND_ORE.defaultBlockState(), 8)).range(16).squared());
	 
	 
	 public static void addDefaultOres(BiomeGenerationSettings.Builder p_243750_0_) {
	      p_243750_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, OP_Diamond);
	   }
}

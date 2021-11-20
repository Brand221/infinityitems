package com.infinity.common.grower.tree;

import java.util.Random;

import javax.annotation.Nullable;

import com.infinity.world.gen.ModConfiguredFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class WeirdTree extends Tree {

	@Nullable
	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random p_60014_, boolean p_60015_) {
		// TODO Auto-generated method stub
		return ModConfiguredFeatures.WEIRD;
	}

}

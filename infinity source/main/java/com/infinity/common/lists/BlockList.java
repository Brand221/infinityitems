package com.infinity.common.lists;

import com.infinity.common.Main;
import com.infinity.world.portal.WeirdPortalBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockList {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
	
	public static Block INFGRASS;
	public static Block INFLOG;
	public static Block INFLEAVES;
	public static Block INFSAPLING;
	public static final RegistryObject<WeirdPortalBlock> INFPORTAL = BLOCKS.register("aether_portal", () -> new WeirdPortalBlock(Block.Properties.copy(Blocks.NETHER_PORTAL)));
	public static Block INFPLANKS;
}

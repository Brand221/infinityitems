package com.infinity.common.events;

import org.apache.logging.log4j.Logger;

import com.infinity.common.GOD;
import com.infinity.common.GODARMOR;
import com.infinity.common.Iinfarmor;
import com.infinity.common.Main;
import com.infinity.common.grower.tree.WeirdTree;
import com.infinity.common.lists.BlockList;
import com.infinity.common.lists.ItemList;
import com.infinity.world.portal.WeirdPortalBlock;

import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SimpleFoiledItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvent 
{
	public static final Logger LOGGER = Main.LOGGER;
	public static final String MOD_ID = Main.MOD_ID;
	public static final ItemGroup INFINITY_TAB = Main.INFINITY_TAB;
	@SubscribeEvent
	public static void registerItems(final net.minecraftforge.event.RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll
		(
			ItemList.INFSTAR = new SimpleFoiledItem(new Item.Properties().tab(Main.INFINITY_TAB).rarity(Rarity.EPIC)).setRegistryName(location("infinity_star")),
			ItemList.ENDSTAR = new SimpleFoiledItem(new Item.Properties().tab(Main.INFINITY_TAB).rarity(Rarity.RARE)).setRegistryName(location("end_star")),
			ItemList.INFINITY_SWORD = new SwordItem(GOD.GOD, 3, -2.4F, (new Item.Properties().tab(Main.INFINITY_TAB).fireResistant())).setRegistryName(location("infinity_sword")),
		    ItemList.INFINITY_PICKAXE = new PickaxeItem(GOD.GOD, -1995, -2.8F,(new Item.Properties().tab(Main.INFINITY_TAB).fireResistant())).setRegistryName(location("infinity_pickaxe")),
		    ItemList.INFINITY_SHOVEL = new ShovelItem(GOD.GOD, -1994.5F, -3.0F,(new Item.Properties().tab(Main.INFINITY_TAB).fireResistant())).setRegistryName(location("infinity_shovel")),
		    ItemList.INFINITY_AXE = new AxeItem(GOD.GOD, 5.0F, -3.0F,(new Item.Properties().tab(Main.INFINITY_TAB).fireResistant())).setRegistryName(location("infinity_axe")),
		    ItemList.INFINITY_HOE = new HoeItem(GOD.GOD, -2000, 0.0F,(new Item.Properties().tab(Main.INFINITY_TAB).fireResistant())).setRegistryName(location("infinity_hoe")),
		    ItemList.INFINITY_HELMET = new Iinfarmor(EquipmentSlotType.HEAD, (new Item.Properties().tab(Main.INFINITY_TAB).fireResistant())).setRegistryName(location("infinity_helmet")),
		    ItemList.INFINITY_CHESTPLATE = new Iinfarmor(EquipmentSlotType.CHEST, (new Item.Properties().tab(Main.INFINITY_TAB).fireResistant())).setRegistryName(location("infinity_chestplate")),
		    ItemList.INFINITY_LEGGINGS = new Iinfarmor(EquipmentSlotType.LEGS, (new Item.Properties().tab(Main.INFINITY_TAB).fireResistant())).setRegistryName(location("infinity_leggings")),
		    ItemList.INFINITY_BOOTS = new Iinfarmor(EquipmentSlotType.FEET, (new Item.Properties().tab(Main.INFINITY_TAB).fireResistant())).setRegistryName("infinity_boots"),
		    ItemList.INFGRASS = new BlockItem(BlockList.INFGRASS, new Item.Properties().tab(Main.INFINITY_TAB)).setRegistryName(BlockList.INFGRASS.getRegistryName()),
		    ItemList.INFLOG = new BlockItem(BlockList.INFLOG, new Item.Properties().tab(Main.INFINITY_TAB)).setRegistryName(BlockList.INFLOG.getRegistryName()),
		    ItemList.INFLEAVES = new BlockItem(BlockList.INFLEAVES, new Item.Properties().tab(Main.INFINITY_TAB)).setRegistryName(BlockList.INFLEAVES.getRegistryName()),
		    ItemList.INFSAPLING = new BlockItem(BlockList.INFSAPLING, new Item.Properties().tab(Main.INFINITY_TAB)).setRegistryName(BlockList.INFSAPLING.getRegistryName()),
		    ItemList.INFPLANKS = new BlockItem(BlockList.INFPLANKS, new Item.Properties().tab(Main.INFINITY_TAB)).setRegistryName(BlockList.INFPLANKS.getRegistryName())
				);
	}
	@SubscribeEvent
	public static void registerBlocks(final net.minecraftforge.event.RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll
		(
				BlockList.INFGRASS = new Block(Block.Properties.of(Material.GRASS).randomTicks().strength(6.0F).sound(SoundType.GRASS)).setRegistryName(location("weird_grass")),
				BlockList.INFLOG = loc(MaterialColor.WARPED_STEM, MaterialColor.WARPED_WART_BLOCK).setRegistryName(location("weird_log")),
				BlockList.INFLEAVES = new LeavesBlock(Block.Properties.of(Material.LEAVES).strength(2.0F).sound(SoundType.GRASS).noOcclusion().randomTicks()).setRegistryName(location("weird_leaves")),
				BlockList.INFSAPLING = new SaplingBlock(new WeirdTree(), Block.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)).setRegistryName(location("weird_sapling")),
				BlockList.INFPLANKS = new Block(Block.Properties.of(Material.WOOD).strength(20.0F, 30.0F).sound(SoundType.WOOD)).setRegistryName(location("weird_planks"))
				);}

	public static ResourceLocation location(String name) {
		return new ResourceLocation(MOD_ID, name);
	}
	private static RotatedPillarBlock loc(MaterialColor p_50789_, MaterialColor p_50790_) {
	      return new RotatedPillarBlock(Block.Properties.of(Material.WOOD, (p_152624_) -> {
	         return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_50789_ : p_50790_;
	      }).strength(20.0F).sound(SoundType.WOOD));}
	

}

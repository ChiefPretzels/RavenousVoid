package com.ravvoid.core;



import com.ravvoid.blocks.PileBlock;
import com.ravvoid.blocks.PureShardBlock;
import com.ravvoid.blocks.ShardBlock;
import com.ravvoid.blocks.VoidAltar;
import com.ravvoid.blocks.VoidOre;
import com.ravvoid.blocks.VoidRend;
import com.ravvoid.blocks.Crystalizer;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class VoidBlocks {
	
	public static Block VOIDORE;
	public static Block	PILEBLOCK;
	public static Block	PILEBLOCKACTIVE;
	public static Block	SHARDBLOCK;
	public static Block	VOIDALTAR;
	public static Block	VOIDREND;
	public static Block	CRYSTALIZER;
	public static Block	PURESHARDBLOCK;
	
	public static void init() {
		
		VOIDORE = new VoidOre(Material.ROCK).setRegistryName("voidore").setUnlocalizedName("voidore").setCreativeTab(VoidTabs.tabVoid).setHardness(5.0F).setResistance(5.0F);
		VOIDALTAR = new VoidAltar(Material.PISTON).setRegistryName("voidaltar").setLightLevel(0.3f).setUnlocalizedName("voidaltar").setCreativeTab(VoidTabs.tabVoid).setHardness(0.5F).setResistance(0.5F);
		PILEBLOCK = new PileBlock(Material.GROUND).setRegistryName("pileblock").setUnlocalizedName("pileblock");
		PILEBLOCKACTIVE = new PileBlock(Material.GROUND).setRegistryName("pileblockactive").setUnlocalizedName("pileblockactive").setLightLevel(0.3f);
		SHARDBLOCK = new ShardBlock(Material.GLASS).setRegistryName("shardblock").setUnlocalizedName("shardblock").setCreativeTab(VoidTabs.tabVoid).setLightLevel(0.3f).setHardness(0.3F);
		VOIDREND = new VoidRend(Material.GROUND).setRegistryName("voidrend").setUnlocalizedName("voidrend").setLightLevel(0.5f).setHardness(0.3F);
		CRYSTALIZER = new Crystalizer(Material.PISTON).setRegistryName("crystalizer").setLightLevel(0.3f).setUnlocalizedName("crystalizer").setCreativeTab(VoidTabs.tabVoid).setHardness(0.5F).setResistance(0.5F);
		PURESHARDBLOCK = new PureShardBlock(Material.GLASS).setRegistryName("pureshardblock").setUnlocalizedName("pureshardblock").setCreativeTab(VoidTabs.tabVoid).setLightLevel(0.5f).setHardness(0.3F);
		
		VOIDORE.setHarvestLevel("pickaxe", 2);
	}
	
	public static void register() {
		
		registerBlock(VOIDORE);
		registerBlock(PILEBLOCK);
		registerBlock(PILEBLOCKACTIVE);
		registerBlock(SHARDBLOCK);
		registerBlock(VOIDALTAR);
		registerBlock(VOIDREND);
		registerBlock(CRYSTALIZER);
		registerBlock(PURESHARDBLOCK);
	}
	
	public static void registerRenders() {

		registerRender(VOIDORE);
		registerRender(PILEBLOCK);
		registerRender(PILEBLOCKACTIVE);
		registerRender(SHARDBLOCK);
		registerRender(VOIDALTAR);
		registerRender(VOIDREND);
		registerRender(CRYSTALIZER);
		registerRender(PURESHARDBLOCK);
	}
	
	public static void registerBlock(Block block) {
		
		GameRegistry.register(block);
		ItemBlock item = new ItemBlock(block);
		item.setRegistryName(block.getRegistryName());
		GameRegistry.register(item);
	}
	
	public static void registerRender(Block block) {
	
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
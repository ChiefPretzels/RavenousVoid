package com.ravvoid.core;



import com.ravvoid.blocks.PileBlock;
import com.ravvoid.blocks.PureShardBlock;
import com.ravvoid.blocks.ShardBlock;
import com.ravvoid.blocks.VoidAltar;
import com.ravvoid.blocks.VoidOre;
import com.ravvoid.blocks.VoidRend;
import com.ravvoid.blocks.VoidRift;
import com.ravvoid.blocks.Conduit;
import com.ravvoid.blocks.Crystallizer;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class VoidBlocks {
	
	public static Block VOIDORE;
	public static Block PILEBLOCK;
	public static Block PILEBLOCKACTIVE;
	public static Block SHARDBLOCK;
	public static Block VOIDALTAR;
	public static Block VOIDREND;
	public static Block CRYSTALLIZER;
	public static Block PURESHARDBLOCK;
	public static Block CONDUIT;
	public static Block VOIDRIFT;
	public static Block ASH;
	public static Block VOIDSTONE;
	
	public static void init() {
		
		VOIDORE = new VoidOre(Material.ROCK).setRegistryName("voidore").setUnlocalizedName("voidore").setCreativeTab(VoidTabs.tabVoid).setHardness(5.0F).setResistance(5.0F);
		VOIDALTAR = new VoidAltar(Material.PISTON).setRegistryName("voidaltar").setLightLevel(0.3f).setUnlocalizedName("voidaltar").setCreativeTab(VoidTabs.tabVoid).setHardness(0.5F).setResistance(0.5F);
		PILEBLOCK = new PileBlock(Material.GROUND).setRegistryName("pileblock").setUnlocalizedName("pileblock");
		PILEBLOCKACTIVE = new PileBlock(Material.GROUND).setRegistryName("pileblockactive").setUnlocalizedName("pileblockactive").setLightLevel(0.3f);
		SHARDBLOCK = new ShardBlock(Material.GLASS).setRegistryName("shardblock").setUnlocalizedName("shardblock").setLightLevel(0.3f).setHardness(0.3F);
		VOIDREND = new VoidRend(Material.GROUND).setRegistryName("voidrend").setUnlocalizedName("voidrend").setLightLevel(0.5f).setHardness(0.3F);
		CRYSTALLIZER = new Crystallizer(Material.PISTON).setRegistryName("crystallizer").setLightLevel(0.3f).setUnlocalizedName("crystallizer").setCreativeTab(VoidTabs.tabVoid).setHardness(0.5F).setResistance(0.5F);
		PURESHARDBLOCK = new PureShardBlock(Material.GLASS).setRegistryName("pureshardblock").setUnlocalizedName("pureshardblock").setLightLevel(0.5f).setHardness(0.3F);
		CONDUIT = new Conduit(Material.PISTON).setRegistryName("conduit").setLightLevel(0.3f).setUnlocalizedName("conduit").setCreativeTab(VoidTabs.tabVoid).setHardness(0.5F).setResistance(0.5F);
		VOIDRIFT = new VoidRift(Material.GROUND).setRegistryName("voidrift").setUnlocalizedName("voidrift").setLightLevel(0.5f).setHardness(0.3F);
		ASH = new Block(Material.GROUND).setRegistryName("ash").setUnlocalizedName("ash").setCreativeTab(VoidTabs.tabVoid).setHardness(0.5F);
		VOIDSTONE = new Block(Material.ROCK).setRegistryName("voidstone").setUnlocalizedName("voidstone").setCreativeTab(VoidTabs.tabVoid).setHardness(1.5F).setResistance(10.0F);
		
		VOIDORE.setHarvestLevel("pickaxe", 2);
	}
	
	public static void register() {
		
		registerBlock(VOIDORE);
		registerBlock(PILEBLOCK);
		registerBlock(PILEBLOCKACTIVE);
		registerBlock(SHARDBLOCK);
		registerBlock(VOIDALTAR);
		registerBlock(VOIDREND);
		registerBlock(CRYSTALLIZER);
		registerBlock(PURESHARDBLOCK);
		registerBlock(CONDUIT);
		registerBlock(VOIDRIFT);
		registerBlock(ASH);
		registerBlock(VOIDSTONE);
	}
	
	public static void registerRenders() {

		registerRender(VOIDORE);
		registerRender(PILEBLOCK);
		registerRender(PILEBLOCKACTIVE);
		registerRender(SHARDBLOCK);
		registerRender(VOIDALTAR);
		registerRender(VOIDREND);
		registerRender(CRYSTALLIZER);
		registerRender(PURESHARDBLOCK);
		registerRender(CONDUIT);
		registerRender(VOIDRIFT);
		registerRender(ASH);
		registerRender(VOIDSTONE);
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
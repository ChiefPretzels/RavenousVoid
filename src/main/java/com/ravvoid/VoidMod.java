package com.ravvoid;

import org.apache.logging.log4j.Logger;

import com.ravvoid.blocks.tileentity.TileEntityAltar;
import com.ravvoid.blocks.tileentity.TileEntityCrystalizer;
import com.ravvoid.blocks.tileentity.TileEntityPile;
import com.ravvoid.client.render.RenderVoidBeast;
import com.ravvoid.core.CraftingManager;
import com.ravvoid.core.Ref;
import com.ravvoid.core.VoidEntities;
import com.ravvoid.entity.mob.EntityVoidBeast;
import com.ravvoid.proxy.CommonProxy;
import com.ravvoid.world.OreGen;
import com.ravvoid.proxy.CommonProxy; 

import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(modid = Ref.MODID, name = Ref.NAME, version = Ref.Version)
public class VoidMod {

	@SidedProxy(clientSide = Ref.CLIENTPROXY, serverSide = Ref.COMMONPROXY)
	public static CommonProxy proxy;
	
	@Instance
	public static VoidMod instance;
	
	public static Logger logger;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);
	}
	
	@EventHandler
		public void init(FMLInitializationEvent event) {

		GameRegistry.registerTileEntity(TileEntityPile.class, "tileentitypile");
		GameRegistry.registerTileEntity(TileEntityAltar.class, "tileentityaltar");
		GameRegistry.registerTileEntity(TileEntityCrystalizer.class, "tileentitycrystalizer");
		GameRegistry.registerWorldGenerator(new OreGen(), 0);
		VoidEntities.registerEntities();
		CraftingManager.mainRegistery();
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		proxy.postInit(event);
	}
}

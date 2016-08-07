package com.ravvoid.proxy;

import org.lwjgl.opengl.GL11;

import com.ravvoid.blocks.tileentity.TileEntityCrystallizer;
import com.ravvoid.client.render.RenderShade;
import com.ravvoid.client.render.RenderVoidBeast;
import com.ravvoid.core.VoidBlocks;
import com.ravvoid.core.VoidItems;
import com.ravvoid.entity.mob.EntityShade;
import com.ravvoid.entity.mob.EntityVoidBeast;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {

		super.preInit(event);
		VoidItems.init();
		RenderingRegistry.registerEntityRenderingHandler(EntityVoidBeast.class, RenderVoidBeast::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityShade.class, RenderShade::new);
	}
	
	@Override
	public void init(FMLInitializationEvent event) {

		super.init(event);
		VoidItems.registerRenders();
		VoidBlocks.registerRenders();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {

		super.postInit(event);
	}
}

package com.ravvoid.core;

import com.ravvoid.VoidMod;
import com.ravvoid.items.AwakenedVoidOrb;
import com.ravvoid.items.PureVoidShard;
import com.ravvoid.items.VoidPile;
import com.ravvoid.items.VoidShard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class VoidItems {

	public static Item VOIDSHARD;
	public static Item VOIDORB;
	public static Item VOIDFRAGMENTS;
	public static Item FRAGMENTPILE;
	public static Item BEASTHIDE;
	public static Item SPIRIT;
	public static Item VOIDESSENCE;
	public static Item PUREVOIDSHARD;
	public static Item AWAKENEDVOIDORB;
	
	public static void init() {
		VOIDSHARD = registerItem(new VoidShard(), "voidshard").setUnlocalizedName("voidshard");
		VOIDORB = registerItem(new Item(), "voidorb").setUnlocalizedName("voidorb").setMaxStackSize(1);
		VOIDFRAGMENTS = registerItem(new Item(), "voidfragments").setUnlocalizedName("voidfragments");
		FRAGMENTPILE = registerItem(new VoidPile(), "fragmentpile").setUnlocalizedName("fragmentpile");
		BEASTHIDE = registerItem(new Item(), "beasthide").setUnlocalizedName("beasthide");
		SPIRIT = registerItem(new Item(), "spirit").setUnlocalizedName("spirit");
		VOIDESSENCE = registerItem(new Item(), "voidessence").setUnlocalizedName("voidessence");
		PUREVOIDSHARD = registerItem(new PureVoidShard(), "purevoidshard").setUnlocalizedName("purevoidshard");
		AWAKENEDVOIDORB = registerItem(new AwakenedVoidOrb(), "awakenedvoidorb").setUnlocalizedName("awakenedvoidorb").setMaxStackSize(1);
	}
	
	public static void registerRenders() {
		
		registerRender(VOIDSHARD);
		registerRender(VOIDORB);
		registerRender(VOIDFRAGMENTS);
		registerRender(FRAGMENTPILE);
		registerRender(BEASTHIDE);
		registerRender(SPIRIT);
		registerRender(VOIDESSENCE);
		registerRender(PUREVOIDSHARD);
		registerRender(AWAKENEDVOIDORB);
		
	}
	
	public static void registerRender(Item item) {
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Ref.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
	
	// reg
	public static Item registerItem(Item item, String name) {
		return registerItem(item, name, null);
		
	}
	
	private static Item registerItem(Item item, String name, CreativeTabs tab) {

		GameRegistry.register(item, new ResourceLocation(Ref.MODID, name)).setCreativeTab(VoidTabs.tabVoid);
		return item;
	}
	//reg end
}

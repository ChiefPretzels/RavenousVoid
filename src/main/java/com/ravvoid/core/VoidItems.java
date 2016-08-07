package com.ravvoid.core;

import com.ravvoid.VoidMod;
import com.ravvoid.items.AwakenedVoidOrb;
import com.ravvoid.items.PureVoidShard;
import com.ravvoid.items.VoidPile;
import com.ravvoid.items.VoidShard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;
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
	public static ItemArmor HIDEHELM;
	public static ItemArmor HIDECHEST;
	public static ItemArmor HIDELEGS;
	public static ItemArmor HIDEFEET;
	
	public static ArmorMaterial HIDE = EnumHelper.addArmorMaterial ("HIDE", "rv:hide", 18, new int[] {3,6,5,2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	
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
		
		HIDEHELM = (ItemArmor) registerItem(new VoidArmor(HIDE, 1, EntityEquipmentSlot.HEAD), "hidehelm").setUnlocalizedName("hidehelm");
		HIDECHEST = (ItemArmor) registerItem(new VoidArmor(HIDE, 1, EntityEquipmentSlot.CHEST), "hidechest").setUnlocalizedName("hidechest");
		HIDELEGS = (ItemArmor) registerItem(new VoidArmor(HIDE, 2, EntityEquipmentSlot.LEGS), "hidelegs").setUnlocalizedName("hidelegs");
		HIDEFEET = (ItemArmor) registerItem(new VoidArmor(HIDE, 1, EntityEquipmentSlot.FEET), "hidefeet").setUnlocalizedName("hidefeet");
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
		registerRender(HIDEHELM);
		registerRender(HIDECHEST);
		registerRender(HIDELEGS);
		registerRender(HIDEFEET);
		
	}

	
	// reg
	public static void registerRender(Item item) {
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation("rv:" + item.getUnlocalizedName().substring(5), "inventory"));
	}
	public static Item registerItem(Item item, String name) {
		return registerItem(item, name, null);
		
	}
	
	private static Item registerItem(Item item, String name, CreativeTabs tab) {

		GameRegistry.register(item, new ResourceLocation(Ref.MODID, name)).setCreativeTab(VoidTabs.tabVoid);
		return item;
	}
	//reg end
}

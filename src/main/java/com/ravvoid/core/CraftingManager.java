package com.ravvoid.core;

import com.ravvoid.core.VoidItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class CraftingManager {
	public static void mainRegistery(){
		addCraftingRec();
		addSmeltingRec();
	}
	public static void addCraftingRec(){
		//Shaped
		GameRegistry.addRecipe(new ItemStack(VoidItems.FRAGMENTPILE), new Object[]{" F ","FDF"," F ", 'F', VoidItems.VOIDFRAGMENTS, 'D', Items.DIAMOND});
		GameRegistry.addRecipe(new ItemStack(VoidBlocks.VOIDALTAR), new Object[]{"FPF","SWS","W W", 'F', VoidItems.VOIDFRAGMENTS, 'P', VoidItems.FRAGMENTPILE, 'S', VoidItems.VOIDSHARD, 'W', new ItemStack(Blocks.PLANKS, 1, OreDictionary.WILDCARD_VALUE)});
		GameRegistry.addRecipe(new ItemStack(VoidBlocks.CRYSTALIZER), new Object[]{"STS","T T","TWT", 'T', new ItemStack(Blocks.STONE, 1, OreDictionary.WILDCARD_VALUE), 'S', VoidItems.VOIDSHARD, 'W', new ItemStack(Blocks.PLANKS, 1, OreDictionary.WILDCARD_VALUE)});
		//Shapeless
	}
	public static void addSmeltingRec(){
		
	}
}

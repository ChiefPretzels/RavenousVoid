package com.ravvoid.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class VoidTabs {
	
	public static final CreativeTabs tabVoid = new CreativeTabs("tabVoid") {
		
		@Override
		public Item getTabIconItem() {
			
			return VoidItems.VOIDORB;
		}
	};
}

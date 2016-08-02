package com.ravvoid.core;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.ravvoid.entity.mob.EntityShade;
import com.ravvoid.entity.mob.EntityVoidBeast;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.LootTableList;

public class Ref {
		
	public static final String MODID = "rv";
	public static final String NAME = "Ravenous Void";
	public static final String Version = "1.10.2";
	
	public static final String CLIENTPROXY = "com.ravvoid.proxy.ClientProxy";
	public static final String COMMONPROXY = "com.ravvoid.proxy.CommonProxy";
    public static final ResourceLocation VOIDBEASTLOOT = register("voidbeast");
    public static final ResourceLocation SHADE = register("shade");
	
	public static Entity altarListTier1(ItemStack item, World world) {
			
        if (ItemStack.areItemsEqual(item, new ItemStack(Items.ROTTEN_FLESH))){return new EntityVoidBeast(world);}
        else if (ItemStack.areItemsEqual(item, new ItemStack(Items.BONE)))  {return new EntityShade(world);}
        else {
        	return null;
        }
	}
	
	private static ResourceLocation register(String id)
    {
        return LootTableList.register(new ResourceLocation(MODID, id));
    }
}

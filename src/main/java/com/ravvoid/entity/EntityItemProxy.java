package com.ravvoid.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemProxy extends EntityItem {

	public EntityItemProxy(World worldIn, double x, double y, double z, ItemStack stack) {
		super(worldIn, x, y, z, stack);
	}
	
	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		
	}
}

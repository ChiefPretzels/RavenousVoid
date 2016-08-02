package com.ravvoid.blocks;

import java.util.Random;

import com.ravvoid.core.VoidItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class VoidOre extends Block {

	public VoidOre(Material materialIn) {
		super(materialIn);
		
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return VoidItems.VOIDSHARD;
		
	}

}

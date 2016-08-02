package com.ravvoid.blocks.tileentity;

import java.util.Collection;

import com.ravvoid.blocks.PileBlock;
import com.ravvoid.core.VoidBlocks;
import com.ravvoid.core.VoidItems;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityPile extends TileEntity implements ITickable  {
public int t = 0;
	
	
	
	
	public void update() {
		if (!worldObj.isRemote){
			pos = this.getPos();
			World worldIn = worldObj;
			
			int i = worldIn.getBlockState(pos).getValue(PileBlock.active);
			if (0.1f >= worldIn.getLightBrightness(pos) && worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
	
				if (worldIn.getBlockState(pos.up()).getBlock() == VoidBlocks.PILEBLOCK) {
					worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(PileBlock.active, Integer.valueOf(0)));
	    		}
				
				if (t <= 1000) {
					++t;
				}
				else {
					t=0;
		
		    		if (i <= 9 && worldIn.getBlockState(pos.up()).getBlock() == VoidBlocks.PILEBLOCKACTIVE) {
			    		worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(PileBlock.active, Integer.valueOf(i)));
		    		}
		    		else {
		    			worldIn.getBlockState(pos).getBlock().spawnAsEntity(worldIn, pos, new ItemStack(VoidItems.VOIDORB));
		    			worldIn.destroyBlock(pos, false);
		    		}
	    		}
	    	}
	    	else {
	    		if (worldIn.getBlockState(pos.up()).getBlock() == VoidBlocks.PILEBLOCKACTIVE) {
	    		worldIn.setBlockState(pos, VoidBlocks.PILEBLOCK.getDefaultState());
				worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(PileBlock.active, Integer.valueOf(0)));
				}
			}
			if(worldIn.getBlockState(pos.down()).isFullyOpaque() == false || worldIn.getBlockState(pos.up()).getBlock() != Blocks.AIR) {
	
				worldIn.destroyBlock(pos, true);
			}
		}
		
    }
}

package com.ravvoid.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.ravvoid.blocks.tileentity.TileEntityConduit;
import com.ravvoid.blocks.tileentity.TileEntityConduit;
import com.ravvoid.core.Ref;
import com.ravvoid.core.VoidBlocks;
import com.ravvoid.core.VoidItems;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Conduit extends Block implements ITileEntityProvider {

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(00.28125d, 0.0d, 0.28125d, 0.71875d, 0.9375d, 0.71875d);
	
	public Conduit(Material materialIn) {
		super(materialIn);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileEntityConduit();
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return bounds;
    }
	
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
	    return bounds;
	}
	
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).isFullyOpaque();
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	TileEntity te = worldIn.getTileEntity(pos);
    	
        if (((TileEntityConduit) te).display == null)
        {
            if (ItemStack.areItemsEqualIgnoreDurability(heldItem, new ItemStack(VoidItems.AWAKENEDVOIDORB))) {
            	if (ItemStack.areItemsEqualIgnoreDurability(heldItem, new ItemStack(VoidItems.AWAKENEDVOIDORB))) heldItem.getTagCompound().setBoolean("active", false);
          	((TileEntityConduit) te).display = heldItem.copy();
            	((TileEntityConduit) te).display.stackSize = 1;
        		--heldItem.stackSize;
                	((TileEntityConduit) te).setDisplayItem(worldIn, pos, state, playerIn, heldItem);
                	((TileEntityConduit) te).change();
                	return true;
            }
        }
        else {
        	((TileEntityConduit) te).removeItem(worldIn, pos, state, playerIn);
        	((TileEntityConduit) te).change();
        	return true;
        }
        
        return false;
    }
    
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
	    
        TileEntity te = worldIn.getTileEntity(pos);
        if(((TileEntityConduit) te).display != null) spawnAsEntity(worldIn, pos, ((TileEntityConduit) te).display);
        if(((TileEntityConduit) te).entity != null)((TileEntityConduit) te).entity.setDead();
        super.breakBlock(worldIn, pos, state);
    }
  //TODO display update tick with sprite from stored, add right click stores item in hand, only if it is on a list,\
    
}

package com.ravvoid.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.ravvoid.blocks.tileentity.TileEntityAltar;
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

public class VoidAltar extends Block implements ITileEntityProvider {

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(0.125d, 0.0d, 0.125d, 0.875d, 0.875d, 0.875d);
	
	public VoidAltar(Material materialIn) {
		super(materialIn);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileEntityAltar();
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

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	TileEntity te = worldIn.getTileEntity(pos);
    	
        if (((TileEntityAltar) te).display == null)
        {
            if (Ref.altarListTier1(heldItem, worldIn) != null || ItemStack.areItemsEqual(heldItem, new ItemStack(VoidItems.VOIDORB)) || ItemStack.areItemsEqualIgnoreDurability(heldItem, new ItemStack(VoidItems.AWAKENEDVOIDORB))) {
            	((TileEntityAltar) te).display = heldItem.copy();
            	((TileEntityAltar) te).display.stackSize = 1;
        		--heldItem.stackSize;
                	((TileEntityAltar) te).setDisplayItem(worldIn, pos, state, playerIn, heldItem);
                	((TileEntityAltar) te).change();
                	return true;
            }
        }
        else {
        	((TileEntityAltar) te).removeItem(worldIn, pos, state, playerIn);
        	((TileEntityAltar) te).change();
        	return true;
        }
        
        return false;
    }
	    
	    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	    {
		    if (worldIn.getBlockState(pos.add(0, 2, 0)).getBlock() == VoidBlocks.VOIDREND) {worldIn.destroyBlock(pos.add(0, 2, 0), false);}
		    TileEntity te = worldIn.getTileEntity(pos);
		    if(((TileEntityAltar) te).rift)((TileEntityAltar) te).riftBreak();
		    if(((TileEntityAltar) te).display != null) spawnAsEntity(worldIn, pos, ((TileEntityAltar) te).display);
		    if(((TileEntityAltar) te).entity != null)((TileEntityAltar) te).entity.setDead();
		    super.breakBlock(worldIn, pos, state);
	    }
    
}

package com.ravvoid.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.ravvoid.core.VoidItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class VoidRift extends Block  {

	

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(2D, 2D, 2D, 2D, 2D, 2D);
	public VoidRift(Material materialIn) {
		super(materialIn);
		this.setDefaultState(getDefaultState().withProperty(SPOT, 5));
	}

	public static final IProperty SPOT = PropertyInteger.create("spot", 1, 9);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	//Block State setup
		@Override protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, new IProperty[] {SPOT, FACING}); }
		
		@Override
		public IBlockState getStateFromMeta(int meta)
		{
			if (meta ==0) meta = 5;
		    return getDefaultState().withProperty(SPOT, Integer.valueOf(meta));
		}
		
		@Override
		public int getMetaFromState(IBlockState state)
		{
		    return ((Integer)state.getValue(SPOT)).intValue();
		}
		//Setup Finished
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return null;
		
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return bounds;
    }

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
	    return NULL_AABB;
	}
	
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

}

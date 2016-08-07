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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class VoidRift extends Block  {

	

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(2D, 2D, 2D, 2D, 2D, 2D);
	public VoidRift(Material materialIn) {
		super(materialIn);
	}

	public static final IProperty SPOT = PropertyInteger.create("spot", 1, 9);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	//Block State setup
	@Override protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, new IProperty[] {SPOT, FACING}); }
		
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(SPOT, Integer.valueOf((meta)));
	}
		
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | ((Integer)state.getValue(SPOT)).intValue();
		return i;
	}
	//Setup Finished
	
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		
		return  this.getDefaultState().withProperty(SPOT, 5);
	}
		
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return null;
		
	}
	
	@Nullable
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return null;
	}
	
	/**
	 * Called When an Entity Collided with the Block
	 */
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if (!entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss())
		{
		            entityIn.changeDimension(16);
		}
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return bounds;
	}
	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random random)
	{
		return 0;
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

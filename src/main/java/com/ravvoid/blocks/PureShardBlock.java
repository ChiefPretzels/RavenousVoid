package com.ravvoid.blocks;

import java.util.Random;

import com.ibm.icu.text.DecimalFormat;
import com.ravvoid.core.VoidItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PureShardBlock extends Block {

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(0.3125d, 0.0d, 0.3125d, 0.6875d, 0.6875d, 0.6875d);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public PureShardBlock(Material materialIn) {
		super(materialIn);
	}

	@Override protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, new IProperty[] {FACING}); }
	
	public IBlockState getStateFromMeta(int meta)
	    {
	        EnumFacing enumfacing = EnumFacing.getFront(meta);

	        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
	        {
	            enumfacing = EnumFacing.NORTH;
	        }

	        return this.getDefaultState().withProperty(FACING, enumfacing);
	    }

	    /**
	     * Convert the BlockState into the correct metadata value
	     */
	    public int getMetaFromState(IBlockState state)
	    {
	        return ((EnumFacing)state.getValue(FACING)).getIndex();
	    }
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return bounds;
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return VoidItems.PUREVOIDSHARD;
		
	}
	
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
	    return bounds;
	}
	
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
		return worldIn.isSideSolid(pos.down(), EnumFacing.UP, true);
    }
	
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}

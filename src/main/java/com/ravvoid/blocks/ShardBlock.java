package com.ravvoid.blocks;

import java.util.Random;

import com.ibm.icu.text.DecimalFormat;
import com.ravvoid.core.VoidItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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

public class ShardBlock extends Block {

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(0.3125d, 0.0d, 0.3125d, 0.6875d, 0.6875d, 0.6875d);
	
	public ShardBlock(Material materialIn) {
		super(materialIn);

	}
		
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return bounds;
    }
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return VoidItems.VOIDFRAGMENTS;
		
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
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
    	
                double d0 = (double)pos.getX() + (double)rand.nextFloat();
                double d1 = (double)((float)pos.getY() + (0.25d + (double)(rand.nextFloat())));
                double d2 = (double)pos.getZ() + (double)rand.nextFloat();
                float f = 15.0F;
                float f1 = 0.0f;
                float f2 = f;
                float f3 = 0.0f;
                worldIn.spawnParticle(EnumParticleTypes.DRIP_LAVA, d0 - 0, d1, d2 - 0, (double)f1, (double)f2, (double)f3);
                Random random = new Random();
                
    }

}

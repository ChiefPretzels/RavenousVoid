package com.ravvoid.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.ravvoid.blocks.tileentity.TileEntityPile;
import com.ravvoid.core.VoidBlocks;
import com.ravvoid.core.VoidItems;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PileBlock extends Block implements ITileEntityProvider {

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.125D, 0.625D);
	public static final PropertyInteger active = PropertyInteger.create("active", 0, 10);
	
	public PileBlock(Material materialIn) {
		super(materialIn);
	}
	
	//Block State setup
	@Override protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, new IProperty[] {active}); } 
		
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(active, Integer.valueOf(meta));
	}
	

	@Override
	public int getMetaFromState(IBlockState state)
	{
	    return ((Integer)state.getValue(active)).intValue();
	}
	//Setup Finished
	
	
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPile();
    }
	
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		
		return VoidItems.FRAGMENTPILE;
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
    
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.isSideSolid(pos.down(), EnumFacing.UP, true && worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR);
    }

    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
    	
    	if (0.1f >= worldIn.getLightBrightness(pos) && worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
    		
    			
    			
                double d0 = (double)pos.getX() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.4D;
                double d1 = (double)((float)pos.getY() + 0.25F);
                double d2 = (double)pos.getZ() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.4D;
                float f = 15.0F;
                float f1 = f * 0.6F + 0.4F;
                float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
                float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
                worldIn.spawnParticle(EnumParticleTypes.DRIP_LAVA, d0, d1, d2, (double)f1, (double)f2, (double)f3, new int[0]);
                worldIn.setBlockState(pos, VoidBlocks.PILEBLOCKACTIVE.getDefaultState());
                Random random = new Random();
                float fl = random.nextFloat()/3;
                
                worldIn.spawnParticle(EnumParticleTypes.DRIP_LAVA, d0, fl + d1, d2, (double)fl + f1, (double)fl + f2, (double)fl + f3, new int[0]);
                worldIn.setBlockState(pos, VoidBlocks.PILEBLOCKACTIVE.getDefaultState());
    	}
    	else {
          	worldIn.setBlockState(pos, VoidBlocks.PILEBLOCK.getDefaultState());
    	}
    }

}

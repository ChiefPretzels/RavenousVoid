package com.ravvoid.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.ravvoid.blocks.tileentity.TileEntityAltar;
import com.ravvoid.blocks.tileentity.TileEntityCrystallizer;
import com.ravvoid.blocks.tileentity.TileEntityPile;
import com.ravvoid.core.VoidBlocks;
import com.ravvoid.entity.EntityItemProxy;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


public class Crystallizer extends Block implements ITileEntityProvider {

	protected static final AxisAlignedBB bounds = new AxisAlignedBB(0.1875d, 0.0d, 0.1875d, 0.8125d, 0.75d, 0.8125d);
	protected static final AxisAlignedBB stand = new AxisAlignedBB(0.1875d, 0.0d, 0.1875d, 0.8125d, 0.4375d, 0.8125d);
	protected static final AxisAlignedBB west = new AxisAlignedBB(0.21875d, 0.4375d, 0.1875d, 0.25d, 0.75d, 0.8125d);
	protected static final AxisAlignedBB north = new AxisAlignedBB(0.1875d, 0.4375d, 0.75d, 0.8125d, 0.75d, 0.78125d);
	protected static final AxisAlignedBB east = new AxisAlignedBB(0.75d, 0.4375d, 0.1875d, 0.78125d, 0.75d, 0.8125d);
	protected static final AxisAlignedBB south = new AxisAlignedBB(0.1875d, 0.4375d, 0.21875d, 0.8125d, 0.75d, 0.25);
	public static final IProperty liquid = PropertyInteger.create("liquid", 0, 2);
	
	public Crystallizer(Material materialIn) {
		super(materialIn);
	}
	
	//Block State setup
	@Override protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, new IProperty[] {liquid}); }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
	    return getDefaultState().withProperty(liquid, Integer.valueOf(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
	    return ((Integer)state.getValue(liquid)).intValue();
	}
	//Setup Finished
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		
		return new TileEntityCrystallizer();
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return bounds;
    }
	
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, stand);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, west);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, north);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, east);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, south);
    }
	
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity ent) {

		TileEntityCrystallizer tile = (TileEntityCrystallizer) world.getTileEntity(pos);
		if(ent instanceof EntityItem && !tile.active && ((Integer) world.getBlockState(pos).getValue(liquid)).intValue() > 0) {
			tile.crystalyzerList((EntityItem) ent);
		}
	}
    
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	Integer fill = ((Integer) state.getValue(liquid)).intValue();
    	
    	if (ItemStack.areItemsEqual(heldItem, new ItemStack(Items.WATER_BUCKET))  && fill == 0)
        {
            if (!worldIn.isRemote)
            {
                if (!playerIn.capabilities.isCreativeMode)
                {
                    playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                }
            }
			worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(Crystallizer.liquid, Integer.valueOf(1)));
            return true;
        }
        else if (ItemStack.areItemsEqual(heldItem, new ItemStack(Items.BUCKET)) && fill == 1)
        {
            if (!worldIn.isRemote)
            {
                if (!playerIn.capabilities.isCreativeMode)
                {
                    --heldItem.stackSize;

                    if (heldItem.stackSize == 0)
                    {
                        playerIn.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
                    }
                    else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET)))
                    {
                        playerIn.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                    }
                }
            }
			worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(Crystallizer.liquid, Integer.valueOf(0)));
            return true;
        }
        else return false;
    }
    
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if(((TileEntityCrystallizer) te).input != null) spawnAsEntity(worldIn, pos, ((TileEntityCrystallizer) te).input);
        if(((TileEntityCrystallizer) te).display != null)((TileEntityCrystallizer) te).display.setDead();
        super.breakBlock(worldIn, pos, state);
    }    
}

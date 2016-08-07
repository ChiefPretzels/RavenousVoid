package com.ravvoid.items;

import java.util.Random;

import com.ravvoid.blocks.PureShardBlock;
import com.ravvoid.core.VoidBlocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PureVoidShard extends Item {

	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
        BlockPos blockpos = flag ? pos : pos.offset(facing);

        if (playerIn.canPlayerEdit(blockpos, facing, stack) && worldIn.canBlockBePlaced(worldIn.getBlockState(blockpos).getBlock(), blockpos, false, facing, (Entity)null, stack) && VoidBlocks.PURESHARDBLOCK.canPlaceBlockAt(worldIn, blockpos))
        {
            --stack.stackSize;
            Random rand = new Random();
		int x = rand.nextInt(3);
		if (x == 0) {
			worldIn.setBlockState(blockpos, VoidBlocks.PURESHARDBLOCK.getDefaultState().withProperty(PureShardBlock.FACING, EnumFacing.NORTH));
		} else if (x == 1) {
			worldIn.setBlockState(blockpos, VoidBlocks.PURESHARDBLOCK.getDefaultState().withProperty(PureShardBlock.FACING, EnumFacing.EAST));
		}else if (x == 2) {
			worldIn.setBlockState(blockpos, VoidBlocks.PURESHARDBLOCK.getDefaultState().withProperty(PureShardBlock.FACING, EnumFacing.WEST));
		}else if (x == 3) {
			worldIn.setBlockState(blockpos, VoidBlocks.PURESHARDBLOCK.getDefaultState().withProperty(PureShardBlock.FACING, EnumFacing.SOUTH));
		}
            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }
}

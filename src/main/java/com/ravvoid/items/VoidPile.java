package com.ravvoid.items;

import com.ravvoid.core.VoidBlocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VoidPile extends Item {
	
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        boolean flag = worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
        BlockPos blockpos = flag ? pos : pos.offset(facing);

        if (playerIn.canPlayerEdit(blockpos, facing, stack) && worldIn.canBlockBePlaced(worldIn.getBlockState(blockpos).getBlock(), blockpos, false, facing, (Entity)null, stack) && VoidBlocks.PILEBLOCK.canPlaceBlockAt(worldIn, blockpos))
        {
            --stack.stackSize;
            worldIn.setBlockState(blockpos, VoidBlocks.PILEBLOCK.getDefaultState());
            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }

}

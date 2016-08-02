package com.ravvoid.items;

import java.awt.geom.Arc2D.Float;
import java.util.List;

import com.ravvoid.core.VoidBlocks;
import com.ravvoid.core.VoidItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AwakenedVoidOrb extends Item {


	public Integer DELAY =0;
	
	public AwakenedVoidOrb () {
		this.maxStackSize = 1;
		this.setMaxDamage(2001);
		
	}
	
	    public void onCreated(ItemStack item, World worldIn, EntityPlayer playerIn)
	    {
		    item.setTagCompound(new NBTTagCompound());
		    this.setDamage(item, 2000);
		    item.getTagCompound().setInteger("power", 2000);
		    item.getTagCompound().setBoolean("active", false);
	    }
	    
	    public boolean showDurabilityBar(ItemStack stack)
	    {
	        return true;
	    }
	    
	    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
	    {
	        return !ItemStack.areItemsEqualIgnoreDurability(oldStack.copy(), newStack.copy());
	    }
	    
	    public double getLight(World world, BlockPos pos) {
		return (world.getLight(pos, true) * .625);
	    }

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		    tooltip.add(String.valueOf(this.getLight(player.getEntityWorld(), player.getPosition())));
		    tooltip.add(String.valueOf(2000 - this.getDamage(stack)+1));
		    
	}
	
	public void powerHelper(ItemStack item, double power) {
		if (this.getDamage(item) - power <= 2000) {
			this.setDamage(item, (int) (this.getDamage(item) - power));
			item.getTagCompound().setShort("power", (short) this.getDamage(item));
		} else {
			this.setDamage(item, 2000);
			item.getTagCompound().setShort("power", (short) this.getDamage(item));
		}
	}
	
	    /**
	     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	     * update it's contents.
	     */
	public void onUpdate(ItemStack item, World world, Entity player, int itemSlot, boolean isSelected) {
		
		if (this.DELAY >= 100 && this.getDamage(item) != 0) {
			if (this.getLight(world, player.getPosition()) <= 5) this.powerHelper(item, 6-this.getLight(world, player.getPosition()));
					
			if (item.getTagCompound().getBoolean("active") && itemSlot <= 10) {
				if (this.getDamage(item) + 3 < 2000) {
					this.setDamage(item, (int) (this.getDamage(item) +(4)));
					item.getTagCompound().setShort("power", (short) this.getDamage(item));
					((EntityLivingBase) player).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 100));
				}
				else {
					item.getTagCompound().setBoolean("active", true);
					((EntityLivingBase) player).removePotionEffect(MobEffects.NIGHT_VISION);
				}
			}			
			this.getDurabilityForDisplay(item);
			this.DELAY = 1;
			((EntityPlayer)player).inventory.markDirty();
		}
	else if (this.DELAY < 100) this.DELAY++;
	}
	

	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player)
	{
		
		if (item.getTagCompound().getBoolean("active")) {
			((EntityLivingBase) player).removePotionEffect(MobEffects.NIGHT_VISION);
			item.getTagCompound().setBoolean("active", false);
			return true;
		}
		return true;
	}
	
	public ActionResult<ItemStack> onItemRightClick(ItemStack item, World world, EntityPlayer player, EnumHand hand)
	{
		
		if (item.getTagCompound().getInteger("power") <= 1900 && !item.getTagCompound().getBoolean("active")) {
			

			item.getTagCompound().setBoolean("active", false);
			this.setDamage(item, (int) (this.getDamage(item) +(100)));
			item.getTagCompound().setShort("power", (short) this.getDamage(item));
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 100));
			return new ActionResult(EnumActionResult.SUCCESS, item);
		} else if (!item.getTagCompound().getBoolean("active")) {
			item.getTagCompound().setBoolean("active", false);
			player.removePotionEffect(MobEffects.NIGHT_VISION);
			return new ActionResult(EnumActionResult.SUCCESS, item);
		}
		return new ActionResult(EnumActionResult.FAIL, item);
	}
	
	protected RayTraceResult rayTrace(World worldIn, EntityPlayer playerIn, boolean useLiquids)
	{
		float f = playerIn.rotationPitch;
		float f1 = playerIn.rotationYaw;
		double d0 = playerIn.posX;
		double d1 = playerIn.posY + (double)playerIn.getEyeHeight();
		double d2 = playerIn.posZ;
		Vec3d vec3d = new Vec3d(d0, d1, d2);
		        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
		        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
		        float f4 = -MathHelper.cos(-f * 0.017453292F);
		        float f5 = MathHelper.sin(-f * 0.017453292F);
		        float f6 = f3 * f4;
		        float f7 = f2 * f4;
		        double d3 = 5.0D;
		        if (playerIn instanceof net.minecraft.entity.player.EntityPlayerMP)
		        {
			        d3 = ((net.minecraft.entity.player.EntityPlayerMP)playerIn).interactionManager.getBlockReachDistance();
		        }
		        Vec3d vec3d1 = vec3d.addVector((double)f6 * d3, (double)f5 * d3, (double)f7 * d3);
		        return worldIn.rayTraceBlocks(vec3d, vec3d1, useLiquids, !useLiquids, false);
	    }
}

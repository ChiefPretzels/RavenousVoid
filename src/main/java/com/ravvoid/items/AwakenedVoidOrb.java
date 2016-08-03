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
		this.setMaxDamage(1001);
		
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
		    if ((world.getLight(pos, true) * .625) !=2.5)
			    return world.getLight(pos, true) * .625;
		    else return 10;
	    }

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
	{
		    tooltip.add(String.valueOf(this.getLight(player.getEntityWorld(), player.getPosition())));
		    tooltip.add(String.valueOf(1000 - this.getDamage(stack)+1));
		    
	}
	
	public void powerHelper(ItemStack item, double power) {
		if (this.getDamage(item) - power <= 1000) {
			this.setDamage(item, (int) (this.getDamage(item) - power));
			item.getTagCompound().setShort("power", (short) this.getDamage(item));
		} else {
			this.setDamage(item, 1000);
			item.getTagCompound().setShort("power", (short) this.getDamage(item));
		}
	}
	
	    /**
	     * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
	     * update it's contents.
	     */
	public void onUpdate(ItemStack item, World world, Entity player, int itemSlot, boolean isSelected) {
		if (!item.hasTagCompound()) {
			item.setTagCompound(new NBTTagCompound());
			    this.setDamage(item, 1000);
			    item.getTagCompound().setInteger("power", 1000);
			    item.getTagCompound().setBoolean("active", false);
		}
		
		if (this.DELAY >= 100 && this.getDamage(item) != 0) {

			if (this.getLight(world, player.getPosition()) <= 5) this.powerHelper(item, 5-this.getLight(world, player.getPosition()));
					

			if (item.getTagCompound().getBoolean("active") && itemSlot <= 10 && this.getDamage(item) + 3 < 1000) {

				this.powerHelper(item, -3);
				((EntityLivingBase) player).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 400, -1));
			}
			else {
				item.getTagCompound().setBoolean("active", false);
				((EntityLivingBase) player).removePotionEffect(MobEffects.NIGHT_VISION);
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
		
		if (item.getTagCompound().getInteger("power") <= 900 && !item.getTagCompound().getBoolean("active")) {
			

			item.getTagCompound().setBoolean("active", true);
			this.powerHelper(item, -100);
			((EntityLivingBase) player).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 400, -1));
			return new ActionResult(EnumActionResult.SUCCESS, item);
		} else if (item.getTagCompound().getBoolean("active")) {
			item.getTagCompound().setBoolean("active", false);
			player.removePotionEffect(MobEffects.NIGHT_VISION);
			return new ActionResult(EnumActionResult.SUCCESS, item);
		}
		return new ActionResult(EnumActionResult.FAIL, item);
	}
}

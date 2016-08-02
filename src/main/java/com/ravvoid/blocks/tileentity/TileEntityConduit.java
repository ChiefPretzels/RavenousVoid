package com.ravvoid.blocks.tileentity;

import java.util.Random;

import com.ravvoid.core.VoidBlocks;
import com.ravvoid.core.VoidItems;
import com.ravvoid.entity.EntityItemProxy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class TileEntityConduit extends TileEntity implements ITickable{

	public ItemStack display;
	public EntityItem entity;
	public int delay;
	public int renderdelay;
	public int upkeepdelay;

	//nbt setup
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(this.pos, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
        readFromNBT(packet.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.display = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("display"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		if (this.display != null)compound.setTag("display", this.display.serializeNBT());
		return compound;
	}
	
	public void change() {this.markDirty();}
	//nbt end
	
	public void setDisplayItem(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, ItemStack heldItem) {

		
		if (!this.worldObj.isRemote) {
			
			this.entity = new EntityItemProxy(this.worldObj, this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.display.copy());
			this.entity.setVelocity(0, 0, 0);
			this.entity.setNoDespawn();
			this.entity.setPositionAndRotation(this.pos.getX()+.5, this.pos.getY()+1d, this.pos.getZ()+.5, 0, 1);
			this.worldObj.spawnEntityInWorld(this.entity);
		}
	}	

	public void removeItem(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn) {
		
		if (!worldIn.isRemote)playerIn.entityDropItem(this.display, 1);
		if (this.entity != null) {this.entity.setDead();}
		this.entity = null;
		this.display = null;
		
	}
	
	public void update() {
		

		if (this.display != null && this.upkeepdelay >= 60 && display.getTagCompound().getShort("power") > 0 && ItemStack.areItemsEqualIgnoreDurability(display, new ItemStack(VoidItems.AWAKENEDVOIDORB))) {
			if (powered ()) {
				if (this.delay == 0) {this.delay = 58;}
				int power =  display.getTagCompound().getShort("power");
				if (power - 10 >= 0) {
					display.setItemDamage(power - 10);
					display.getTagCompound().setShort("power", (short) (power - 10));
				}
				else {
					display.setItemDamage(0);
					display.getTagCompound().setShort("power", (short) (0));
				}
			this.upkeepdelay = 0;
			} else {
				this.delay = 0;
			}
		} else if (this.display != null && display.getTagCompound().getShort("power") > 0 && ItemStack.areItemsEqualIgnoreDurability(display, new ItemStack(VoidItems.AWAKENEDVOIDORB))) {
			this.upkeepdelay++;
		} else {
			this.delay = 0;
			this.upkeepdelay = 0;
		}
		
		if (worldObj.isRemote && this.delay < 20 && this.delay != 0) {this.delay++;}
		else if (worldObj.isRemote && this.delay == 0) {}
		else if (worldObj.isRemote && this.delay >= 20) {
			
			Random rand = new Random();
			double d0 = (double)this.pos.getX() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.4D;
			double d1 = (double)((float)this.pos.getY() + 1.2F);
			double d2 = (double)this.pos.getZ() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.4D;
			float f = 15.0F;
			float f1 = f * 0.6F + 0.4F;
			float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
			float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
			worldObj.spawnParticle(EnumParticleTypes.DRIP_LAVA, d0, d1, d2, (double)f1, (double)f2, (double)f3, new int[0]);
			this.delay = 1;
		}
		
		if(this.entity != null && this.display != null && this.renderdelay == 0) {this.renderdelay++;}
		else if (this.renderdelay > 5000 && this.entity != null) {this.entity.setNoDespawn();this.renderdelay = 0;}
		else if (this.display != null && this.entity == null) {this.setDisplayItem(this.worldObj, this.pos, null, null, null);this.renderdelay = 0;}
		else {}
	}
	
	public boolean powered() {
		Block source = null;
		BlockPos spos = this.pos.add(-5, -5, -5);
		int x;
		int y;
		int z;
		int cnt = 0;
		int ammount = 4;

		
		for(x = 0; x<=11; x++) {
			for(y = 0; y<=11; y++) {
				for(z = 0; z<=11; z++) {
					if (this.worldObj.getBlockState(spos.add(x, y, z)).getBlock() == VoidBlocks.PURESHARDBLOCK) {cnt++;}
				}
			}
		}
		
		if (cnt >= ammount) {
			return true;
		}
		else {
			return false;
		}
	}
}

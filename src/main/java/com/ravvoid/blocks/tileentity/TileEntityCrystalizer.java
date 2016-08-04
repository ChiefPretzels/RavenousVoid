package com.ravvoid.blocks.tileentity;

import java.util.Random;

import com.ravvoid.blocks.Crystalizer;
import com.ravvoid.blocks.PileBlock;
import com.ravvoid.core.VoidItems;
import com.ravvoid.entity.EntityItemProxy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

public class TileEntityCrystalizer extends TileEntity implements ITickable {

	public Integer essence = 0;
	public boolean active;
	private Integer delay = 0;
	private Integer delaypart = 0;
	public ItemStack output;
	public ItemStack input;
	public Entity display;

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
				this.output = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("output"));
				this.input = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("input"));
				this.essence = compound.getInteger("essence");
				this.active = compound.getBoolean("active");
				
			}

			@Override
			public NBTTagCompound writeToNBT(NBTTagCompound compound) {
				compound = super.writeToNBT(compound);
				if (this.output != null)compound.setTag("output", this.output.serializeNBT());
				if (this.input != null)compound.setTag("input", this.input.serializeNBT());
		        compound.setInteger("essence", this.essence);
		        compound.setBoolean("active", this.active);
				
				return compound;
			}
			
			public void change() {this.markDirty();}
			//nbt end
	
	@Override
	public void update() {
		if (this.active) {
			this.change();
			if (this.delaypart == 0)this.delaypart = 1;
			if (this.display == null && this.input != null) {
				this.display = new EntityItemProxy(this.worldObj, this.pos.getX()+.5, this.pos.getY()+.5, this.pos.getZ()+.5, this.input.copy());
				this.display.setVelocity(0, 0, 0);
				if (!worldObj.isRemote)worldObj.spawnEntityInWorld(this.display);
			}	
			
			if (this.delay >= 600) {
				Entity spawn = new EntityItem(worldObj, this.pos.getX()+.5, this.pos.getY()+.5, this.pos.getZ()+.5, this.output.copy());
				spawn.setVelocity(0, 0, 0);
				this.active = false;
				this.output = null;
				this.input = null;
                if (this.display != null) {this.display.setDead(); this.display = null;}
    			worldObj.setBlockState(this.pos, worldObj.getBlockState(this.pos).withProperty(Crystalizer.liquid, Integer.valueOf(0)));
				this.delaypart = 0;
				this.change();
				if (!worldObj.isRemote) {worldObj.spawnEntityInWorld(spawn);}
			}
			
			else {this.delay++;}
		}
		
		if (worldObj.isRemote && this.delaypart < 20 && this.delaypart != 0) {this.delaypart++;}
		else if (worldObj.isRemote && this.delaypart == 0) {}
		else if (worldObj.isRemote && this.delaypart >= 20) {
				Random rand = new Random();
				double d0 = (double)this.pos.getX() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.4D;
				double d1 = (double)((float)this.pos.getY() + 1F);
				double d2 = (double)this.pos.getZ() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.4D;
				float f = 15.0F;
				float f1 = f * 0.6F + 0.4F;
				float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
				float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
                worldObj.spawnParticle(EnumParticleTypes.DRIP_LAVA, d0, d1, d2, (double)f1, (double)f2, (double)f3, new int[0]);
                this.delaypart = 1;
		}
	}

	public void crystalyzerList(EntityItem ent) {
		
		ItemStack stack = ent.getEntityItem().copy();

				if(ItemStack.areItemsEqual(stack, new ItemStack(VoidItems.VOIDESSENCE)) && this.input == null) {
					if (this.essence<4 && ((Integer) worldObj.getBlockState(this.pos).getValue(Crystalizer.liquid)).intValue() == 1) {
						ent.setDead();
						if (stack.stackSize >= (4 - this.essence)) {
							stack.stackSize = stack.stackSize - (4 - this.essence);
							Entity spawn = new EntityItem(worldObj, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
							spawn.setVelocity(0, 0, 0);
							if (!this.worldObj.isRemote)this.worldObj.spawnEntityInWorld(spawn);
							this.essence = 4;
						}
						else {
							this.essence += stack.stackSize;
						}
					}
				}
				else if (ItemStack.areItemsEqual(stack, new ItemStack(VoidItems.SPIRIT)) && this.input == null) {
					if (this.essence == 4) {
						ent.setDead();
						if (stack.stackSize > 1) {
							stack.stackSize--;
							Entity spawn = new EntityItem(this.worldObj, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
							spawn.setVelocity(0, 0, 0);
							if (!this.worldObj.isRemote)this.worldObj.spawnEntityInWorld(spawn);
						}
						this.worldObj.setBlockState(this.pos, this.worldObj.getBlockState(this.pos).withProperty(Crystalizer.liquid, Integer.valueOf(2)));
						this.essence = 0;
					}
				}
				else if (ItemStack.areItemsEqual(stack, new ItemStack(VoidItems.VOIDFRAGMENTS)) && this.input == null) {
					if (((Integer) this.worldObj.getBlockState(this.pos).getValue(Crystalizer.liquid)).intValue() == 2) {
						ent.setDead();
						if (stack.stackSize > 1) {
							stack.stackSize--;
							Entity spawn = new EntityItem(this.worldObj, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
							spawn.setVelocity(0, 0, 0);
							if (!this.worldObj.isRemote)this.worldObj.spawnEntityInWorld(spawn);
						}
						this.active=true;
						this.output= new ItemStack(VoidItems.VOIDSHARD);
						this.input = stack.copy();
					}
				}else if (ItemStack.areItemsEqual(stack, new ItemStack(VoidItems.VOIDSHARD)) && this.input == null) {
					if (((Integer) this.worldObj.getBlockState(this.pos).getValue(Crystalizer.liquid)).intValue() == 2) {
						ent.setDead();
						if (stack.stackSize > 1) {
							stack.stackSize--;
							Entity spawn = new EntityItem(this.worldObj, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
							spawn.setVelocity(0, 0, 0);
							if (!this.worldObj.isRemote)this.worldObj.spawnEntityInWorld(spawn);
						}
						this.active=true;
						this.output= new ItemStack(VoidItems.PUREVOIDSHARD);
						this.input = stack.copy();
					}
				}else if (ItemStack.areItemsEqual(stack, new ItemStack(VoidItems.VOIDORB)) && this.input == null) {
					if (((Integer) this.worldObj.getBlockState(this.pos).getValue(Crystalizer.liquid)).intValue() == 2) {
						ent.setDead();
						if (stack.stackSize > 1) {
							stack.stackSize--;
							Entity spawn = new EntityItem(this.worldObj, this.pos.getX()+.5, this.pos.getY()+1, this.pos.getZ()+.5, stack.copy());
							spawn.setVelocity(0, 0, 0);
							if (!this.worldObj.isRemote)this.worldObj.spawnEntityInWorld(spawn);
						}
						this.active=true;
						this.output= new ItemStack(VoidItems.AWAKENEDVOIDORB);
						this.input = stack.copy();
					}
				}
				else {}
				int inter = ((Integer) worldObj.getBlockState(this.pos).getValue(Crystalizer.liquid)).intValue();
	}
}

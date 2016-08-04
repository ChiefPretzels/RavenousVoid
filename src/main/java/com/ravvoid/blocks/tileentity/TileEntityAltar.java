package com.ravvoid.blocks.tileentity;

import java.util.Random;

import com.ravvoid.blocks.VoidRift;
import com.ravvoid.core.Ref;
import com.ravvoid.core.VoidBlocks;
import com.ravvoid.core.VoidItems;
import com.ravvoid.entity.EntityItemProxy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class TileEntityAltar extends TileEntity implements ITickable  {

		public ItemStack display;
		public EntityItem entity;
		public int counter;
		public int rcounter;
		public int mcounter;
		public int delay;
		public int renderdelay;
		public int upkeepdelay;
		public boolean rift;
		public EnumFacing direction;
		
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
			this.rift = compound.getBoolean("rift");
			
		}

		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound compound) {
			compound = super.writeToNBT(compound);
			if (this.display != null)compound.setTag("display", this.display.serializeNBT());
			compound.setBoolean("rift", rift);
			return compound;
		}
		
		public void change() {this.markDirty();}
		//nbt end
		
		public void update() {
			
			//Void Rend spawn
			if(ItemStack.areItemsEqual(this.display, new ItemStack(VoidItems.VOIDORB)) || ItemStack.areItemsEqualIgnoreDurability(this.display, new ItemStack(VoidItems.AWAKENEDVOIDORB)) &&  this.worldObj.getBlockState(this.pos.up()).getBlock() == Blocks.AIR && worldObj.getBlockState(pos.add(0, 2, 0)).getBlock() == Blocks.AIR) {

				if (powered(1)) {
					if (this.rcounter < 2000) {
						this.rcounter++;
						if (this.delay == 0) {this.delay = 1;}
					}
					else {this.worldObj.setBlockState(this.pos.add(0, 2, 0), VoidBlocks.VOIDREND.getDefaultState()); }
				}
			}
			
			else {

				if (this.rcounter != 0) {this.rcounter = 0; this.delay = 0;}
			}
			
			
			//Rend Upkeep
			if (this.worldObj.getBlockState(this.pos.add(0, 2, 0)).getBlock() == VoidBlocks.VOIDREND && this.upkeepdelay >= 60) {
				if(!powered(1)) {
					this.worldObj.destroyBlock(this.pos.add(0, 2, 0), false);
				}
				this.upkeepdelay = 0;
			}
			else {
				this.upkeepdelay++;
			}
			
			//Void Rift spawn
			if(!this.rift && ItemStack.areItemsEqualIgnoreDurability(this.display, new ItemStack(VoidItems.AWAKENEDVOIDORB)) && worldObj.getBlockState(pos.add(0, 2, 0)).getBlock() == VoidBlocks.VOIDREND) {

				if (this.display.getTagCompound().getShort("power") <= 800 && powered(2)) {
					int count = 0;
					BlockPos spos;
					BlockPos cpos;
					if (direction == EnumFacing.NORTH) {
						spos = pos.add(-1,0,-3);
						for(int y = 0; y<=2; y++) {
							for(int x = 0; x<=2; x++) {
								cpos = spos.add(x,y,0);
								if (worldObj.getBlockState(cpos).getBlock() == Blocks.AIR) {count++;}
							}
						}
					} else if (direction == EnumFacing.SOUTH) {
						
						spos = pos.add(-1,0,3);
						for(int y = 0; y<=2; y++) {
							for(int x = 0; x<=2; x++) {
								cpos = spos.add(x,y,0);
								if (worldObj.getBlockState(cpos).getBlock() == Blocks.AIR) {count++;}
							}
						}
					} else if (direction == EnumFacing.EAST) {

						spos = pos.add(3,0,-1);
						for(int y = 0; y<=2; y++) {
							for(int z = 0; z<=2; z++) {
								cpos = spos.add(0,y,z);
								if (worldObj.getBlockState(cpos).getBlock() == Blocks.AIR) {count++;}
							}
						}
					} else if (direction == EnumFacing.WEST) {
						spos = pos.add(-3,0,-1);
						for(int y = 0; y<=2; y++) {
							for(int z = 0; z<=2; z++) {
								cpos = spos.add(0,y,z);
								if (worldObj.getBlockState(cpos).getBlock() == Blocks.AIR) {count++;}
							}
						}
					}

					
					if(count == 9) {
						if (this.counter < 2000) {
							this.counter++;
							if (this.delay == 0) {this.delay = 1;}
						} else {
							
							this.display.setItemDamage((this.display.getTagCompound().getShort("power") + 200));
							this.display.getTagCompound().setShort("power", (short) (this.display.getTagCompound().getShort("power") + 200));
							this.rift = true;
							this.delay = 0;
							this.counter = 0;
							
							System.out.print(this.direction);
							int s = 0;
							if (direction == EnumFacing.NORTH) {
								spos = pos.add(-1,0,-3);
								for(int y = 0; y<=2; y++) {
									for(int x = 0; x<=2; x++) {
										cpos = spos.add(x,y,0);
										s++;
										worldObj.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, s).withProperty(VoidRift.FACING, direction.getOpposite()));
										
									}
								}
							} else if (direction == EnumFacing.SOUTH) {
								
								spos = pos.add(1,0,3);
								for(int y = 0; y<=2; y++) {
									for(int x = 0; x>=-2; x--) {
										cpos = spos.add(x,y,0);
										s++;
										worldObj.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, s).withProperty(VoidRift.FACING, direction.getOpposite()));
									}
								}
							} else if (direction == EnumFacing.EAST) {
								
								spos = pos.add(3,0,-1);
								for(int y = 0; y<=2; y++) {
									for(int z = 0; z<=2; z++) {
										cpos = spos.add(0,y,z);
										s++;
										worldObj.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, s).withProperty(VoidRift.FACING, direction.getOpposite()));
									}
								}
							} else if (direction == EnumFacing.WEST) {
								spos = pos.add(-3,0,1);
								for(int y = 0; y<=2; y++) {
									for(int z = 0; z>=-2; z--) {
										cpos = spos.add(0,y,z);
										s++;
										worldObj.setBlockState(cpos, VoidBlocks.VOIDRIFT.getDefaultState().withProperty(VoidRift.SPOT, s).withProperty(VoidRift.FACING, direction.getOpposite()));
									}
								}
								
							}
							count = 0;
						}
					}else {
					}
				} else {
					this.delay = 0;
					this.counter = 0;
				}
				
			}else if (this.rift && ItemStack.areItemsEqualIgnoreDurability(this.display, new ItemStack(VoidItems.AWAKENEDVOIDORB)) && worldObj.getBlockState(pos.add(0, 2, 0)).getBlock() == VoidBlocks.VOIDREND) {
				if (this.counter >= 300) {
					if (display.getTagCompound().getShort("power") + 1 <=2000) {
						this.display.setItemDamage((this.display.getTagCompound().getShort("power") + 1));
						this.display.getTagCompound().setShort("power", (short) (this.display.getTagCompound().getShort("power") + 1));
						this.counter = 0;
					} else this.riftBreak();						
				} else this.counter++;
			}else if (this.rift) this.riftBreak();
			
			
			//Mob spawn
			if(this.display !=null && Ref.altarListTier1(this.display, this.worldObj) != null && this.worldObj.getBlockState(this.pos.up()).getBlock() == Blocks.AIR && this.worldObj.getBlockState(this.pos.add(0, 2, 0)).getBlock() == VoidBlocks.VOIDREND) {
					
				if (this.mcounter < 200) {
						this.mcounter++;
						if (this.delay == 0) {this.delay = 1;}
				}	
				else {	
		
					if(!this.worldObj.isRemote) {
						
			            Entity spawn = null;
		
		                spawn = Ref.altarListTier1(this.display, this.worldObj);
		                spawn.setWorld(this.worldObj);
	
		                EntityLiving entityliving = spawn instanceof EntityLiving ? (EntityLiving)spawn : null;
		                spawn.setLocationAndAngles(this.pos.getX()+.5, this.pos.getY()+1f, this.pos.getZ()+.5, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);


	                        AnvilChunkLoader.spawnEntity(spawn, this.worldObj);
	                        this.worldObj.playEvent(2004, this.pos, 0);

	                        if (entityliving != null)
	                        {
	                            entityliving.spawnExplosionParticle();
	                        }
			        }
					this.display = null;
					this.delay = 0;
					this.mcounter = 0;
					if (this.entity != null) {this.entity.setDead();}
					this.entity = null;
				}
					 
			}
		                  

			else {
	
					if (this.mcounter != 0) {this.mcounter = 0; this.delay = 0;}
			}
			
			
			//Particle Spawn
			if (this.worldObj.isRemote && this.delay < 60 && this.delay != 0) {this.delay++;}
			else if (this.worldObj.isRemote && this.delay == 0) {}
			else if (this.worldObj.isRemote) {
				
				Random rand = new Random();
				double d0 = (double)this.pos.getX() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.4D;
				double d1 = (double)((float)this.pos.getY() + 1F);
				double d2 = (double)this.pos.getZ() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.4D;
				float f = 15.0F;
				float f1 = f * 0.6F + 0.4F;
		               	float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
		               	float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
		               	this.worldObj.spawnParticle(EnumParticleTypes.DRIP_LAVA, d0, d1, d2, (double)f1, (double)f2, (double)f3, new int[0]);
		               	this.delay = 1;
			}
			
			if(this.entity != null && this.display != null && this.renderdelay == 0) {this.renderdelay++;}
			else if (this.renderdelay > 5000 && this.entity != null) {this.entity.setNoDespawn();this.renderdelay = 0;}
			else if (this.display != null && this.entity == null) {this.setDisplayItem(this.worldObj, this.pos, null, null, null);this.renderdelay = 0;}
			else {}
		}
		
	public void riftBreak() {
			
			if (this.direction == null) {
				if (worldObj.getBlockState(pos.add(0,1,-3)).getBlock() == VoidBlocks.VOIDRIFT) {
					
					this.direction = EnumFacing.NORTH;
				} else if (worldObj.getBlockState(pos.add(0,1,3)).getBlock() == VoidBlocks.VOIDRIFT) {
					
					this.direction = EnumFacing.SOUTH;
				} else if (worldObj.getBlockState(pos.add(3,1,0)).getBlock() == VoidBlocks.VOIDRIFT) {
					
					this.direction = EnumFacing.WEST;
				} else if (worldObj.getBlockState(pos.add(-3,1,0)).getBlock() == VoidBlocks.VOIDRIFT) {
	
					this.direction = EnumFacing.EAST;
				}
			}
			
			if (direction == EnumFacing.NORTH) {
				BlockPos spos = pos.add(-1,0,-3);
				for(int y = 0; y<=2; y++) {
					for(int x = 0; x<=2; x++) {
						BlockPos cpos = spos.add(x,y,0);
						worldObj.destroyBlock(cpos, false);
					}
				}
			} else if (direction == EnumFacing.SOUTH) {
				
				BlockPos spos = pos.add(-1,0,3);
				for(int y = 0; y<=2; y++) {
					for(int x = 0; x<=2; x++) {
						BlockPos cpos = spos.add(x,y,0);
						worldObj.destroyBlock(cpos, false);
					}
				}
			} else if (direction == EnumFacing.EAST) {
				
				BlockPos spos = pos.add(3,0,-1);
				for(int y = 0; y<=2; y++) {
					for(int z = 0; z<=2; z++) {
						BlockPos cpos = spos.add(0,y,z);
						worldObj.destroyBlock(cpos, false);
					}
				}
			} else if (direction == EnumFacing.WEST) {
				BlockPos spos = pos.add(-3,0,-1);
				for(int y = 0; y<=2; y++) {
					for(int z = 0; z<=2; z++) {
						BlockPos cpos = spos.add(0,y,z);
						worldObj.destroyBlock(cpos, false);
					}
				}
				
			}
			this.rift = false;			
		}

	public boolean powered(Integer tier) {
		Block source = null;
		BlockPos spos = this.pos.add(-5, -5, -5);
		int x;
		int y;
		int z;
		int cnt = 0;
		int ammount = 0;

		if (tier == 1) {source = VoidBlocks.SHARDBLOCK;}
		if (tier == 2) {source = VoidBlocks.PURESHARDBLOCK;}
		
		for(x = 0; x<=11; x++) {
			for(y = 0; y<=11; y++) {
				for(z = 0; z<=11; z++) {
					if (this.worldObj.getBlockState(spos.add(x, y, z)).getBlock() == source) {cnt++;}
					else if (tier == 1 && this.worldObj.getBlockState(spos.add(x, y, z)).getBlock() == VoidBlocks.PURESHARDBLOCK) {cnt++;}
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

	public void setDisplayItem(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, ItemStack heldItem) {

		if (!this.worldObj.isRemote) {
			if (playerIn != null && ItemStack.areItemsEqualIgnoreDurability(this.display, new ItemStack(VoidItems.AWAKENEDVOIDORB)))this.direction = playerIn.getHorizontalFacing();
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
}

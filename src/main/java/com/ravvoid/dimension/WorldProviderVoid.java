package com.ravvoid.dimension;

import com.ravvoid.core.DimensionRegistry;
import com.ravvoid.core.VoidBiomeRegistery;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class WorldProviderVoid extends WorldProvider {

	@Override
	public void createBiomeProvider () {
		this.biomeProvider = new BiomeProviderSingle(VoidBiomeRegistery.VOIDBIOME);
		this.hasNoSky = true;
	}
	
	@Override
	public IChunkGenerator createChunkGenerator () {
		return new VoidChunkProvider(worldObj, true, worldObj.getSeed());
	}
	
	@Override
	public DimensionType getDimensionType() {
		return DimensionRegistry.VOID;
	}
	
	@Override
	public boolean isSurfaceWorld() {
		
		return false;
	}
	
	/**
	 * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
	 */
	public float calculateCelestialAngle(long worldTime, float partialTicks)
	{
		return 0.0F;
	}
	    
	/**
	* Returns array with sunrise/sunset colors
	*/
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks)
	{
		return null;
	}
	
	@Override
	public boolean canCoordinateBeSpawn(int x, int z) {
		
		return false;
	}
	
	@Override
	public boolean canRespawnHere() {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int x, int z) {
		return false;
	}
	
	
	@SideOnly(Side.CLIENT)
	public boolean isSkyColored()
	{
		return false;
	}
	
	    /**
	     * the y level at which clouds are rendered.
	     */
	    @SideOnly(Side.CLIENT)
	    public float getCloudHeight()
	    {
	        return 8.0F;
	    }

	    public int getAverageGroundLevel()
	    {
	        return 70;
	    }
	    
	    
}

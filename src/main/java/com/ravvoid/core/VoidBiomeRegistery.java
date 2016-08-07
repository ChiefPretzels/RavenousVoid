package com.ravvoid.core;

import com.ravvoid.dimension.VoidBiome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class VoidBiomeRegistery extends BiomeProvider {

	public static int VOIDBIOMEID = 210;
	public static Biome VOIDBIOME;
	
	public static void mainRegistery() {
		initializeBiome();
		registerBiome();
	}
	
	public static void initializeBiome() {
		VOIDBIOME = new VoidBiome(new BiomeProperties("Void Biome").setBaseHeight(0.125F).setHeightVariation(0.05F).setRainDisabled().setWaterColor(6710886).setTemperature(0.8F));
		Biome.registerBiome(VOIDBIOMEID, "Void Biome", VOIDBIOME);
	}
	
	public static void registerBiome() {
		BiomeDictionary.registerBiomeType(VOIDBIOME, Type.END);
	}
}

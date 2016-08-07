package com.ravvoid.core;

import com.ravvoid.dimension.WorldProviderVoid;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	
	public static final int dimensionId = 16;
	public static DimensionType VOID;
	
	public static void mainRegistry() {
		registerDimension();
	}
	
	private static void registerDimension() {
		
		VOID = DimensionType.register("The Void", "_void", dimensionId, WorldProviderVoid.class, false);
		DimensionManager.registerDimension(dimensionId, VOID);
	}

}

package com.ravvoid.core;

import com.ravvoid.entity.mob.EntityShade;
import com.ravvoid.entity.mob.EntityVoidBeast;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class VoidEntities {
	public static void registerEntities() {
		registerEntity(EntityVoidBeast.class, "voidbeast", 64, 3, true);
		registerEntity(EntityShade.class, "shade", 64, 3, true);
	}


	private static int entityID = 0;

	/**
     * Register the mod entity type with FML
     * This will also register a spawn egg.

     * @param entityClass The entity class
     * @param entityName A unique name for the entity
     * @param id A mod specific ID for the entity
     * @param mod The mod
     * @param trackingRange The range at which MC will send tracking updates
     * @param updateFrequency The frequency of tracking updates
     * @param sendsVelocityUpdates Whether to send velocity information packets as well
     * @param eggPrimary Primary egg color
     * @param eggSecondary Secondary egg color
     */
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(entityClass, entityName, entityID++, Ref.MODID, trackingRange, updateFrequency, sendsVelocityUpdates, 1, 1);
	}
}

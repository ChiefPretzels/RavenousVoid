package com.ravvoid.dimension;

import com.ravvoid.core.VoidBlocks;
import com.ravvoid.core.VoidEntities;
import com.ravvoid.entity.mob.EntityShade;
import com.ravvoid.entity.mob.EntityVoidBeast;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;


public class VoidBiome extends Biome {

	public VoidBiome(BiomeProperties properties) {
		super(properties);
		this.topBlock = VoidBlocks.ASH.getDefaultState();
		this.fillerBlock = VoidBlocks.VOIDSTONE.getDefaultState();
		
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableMonsterList.add(new SpawnListEntry(EntityVoidBeast.class, 25, 2, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityShade.class, 10, 0, 1));
		this.flowers.clear();
		this.theBiomeDecorator.mushroomsPerChunk = 0;
	}

}

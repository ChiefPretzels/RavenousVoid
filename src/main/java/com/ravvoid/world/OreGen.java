package com.ravvoid.world;

import java.util.Random;

import com.ravvoid.core.Ref;
import com.ravvoid.core.VoidBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGen implements IWorldGenerator {

	private final WorldGenMinable oreGenOverworld;

	public OreGen() {
		oreGenOverworld = new WorldGenMinable(VoidBlocks.VOIDORE.getDefaultState(), 4, BlockMatcher.forBlock(Blocks.STONE));
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		final BlockPos chunkPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);

		switch (world.provider.getDimensionType()) {
			case NETHER:
				
				break;
			case OVERWORLD:
				for (int i = 0; i < 16; i++) {
					oreGenOverworld.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(108) + 5, random.nextInt(16)));
				}
				break;
			case THE_END:
				break;
			default:
				break;
		}
	}
}
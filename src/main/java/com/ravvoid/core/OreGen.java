package com.ravvoid.core;

import java.util.Random;

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
	private final WorldGenMinable oreGenVoid;
	private final WorldGenerator shardGenVoid;

	public OreGen() {
		oreGenOverworld = new WorldGenMinable(VoidBlocks.VOIDORE.getDefaultState(), 8, BlockMatcher.forBlock(Blocks.STONE));
		oreGenVoid = new WorldGenMinable(VoidBlocks.VOIDORE.getDefaultState(), 8, BlockMatcher.forBlock(VoidBlocks.VOIDSTONE));
		shardGenVoid = new WorldGenMinable(VoidBlocks.SHARDBLOCK.getDefaultState(), 1, BlockMatcher.forBlock(Blocks.AIR));
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		final BlockPos chunkPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);

		switch (world.provider.getDimension()) {
			case -1:
				
				break;
			case 0:
				for (int i = 0; i < 16; i++) {
					oreGenOverworld.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(108) + 5, random.nextInt(16)));
				}
				break;
			case 1:
				break;
			case 16: 
				for (int i = 0; i < 16; i++) {
					oreGenVoid.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(108) + 5, random.nextInt(16)));
					BlockPos test = chunkPos.add(random.nextInt(16), random.nextInt(108) + 5, random.nextInt(16));
					if (world.isAirBlock(test) && VoidBlocks.SHARDBLOCK.canPlaceBlockAt(world, test))
				            {
						shardGenVoid.generate(world, random, test);
				            }
				}
				break;
			default:
				break;
		}
	}
}
package com.ravvoid.dimension;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.ravvoid.core.VoidBlocks;

import net.minecraft.block.BlockChorusFlower;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import net.minecraft.world.gen.structure.MapGenEndCity;


public class VoidChunkProvider implements IChunkGenerator {

	    /** RNG. */
	    private Random rand;
	    protected static final IBlockState VOIDSTONE = VoidBlocks.VOIDSTONE.getDefaultState();
	    protected static final IBlockState ASH = VoidBlocks.ASH.getDefaultState();
	    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
	    private NoiseGeneratorOctaves lperlinNoise1;
	    private NoiseGeneratorOctaves lperlinNoise2;
	    private NoiseGeneratorOctaves perlinNoise1;
	    /** A NoiseGeneratorOctaves used in generating terrain */
	    public NoiseGeneratorOctaves noiseGen5;
	    /** A NoiseGeneratorOctaves used in generating terrain */
	    public NoiseGeneratorOctaves noiseGen6;
	    /** Reference to the World object. */
	    private World worldObj;
	    /** are map structures going to be generated (e.g. strongholds) */
	    private boolean mapFeaturesEnabled;
	    private NoiseGeneratorSimplex islandNoise;
	    private double[] buffer;
	    /** The biomes that are used to generate the chunk */
	    private Biome[] biomesForGeneration;
	    double[] pnr;
	    double[] ar;
	    double[] br;
	    // temporary variables used during event handling
	    private int chunkX = 0;
	    private int chunkZ = 0;

	    public VoidChunkProvider(World worldObjIn, boolean mapFeaturesEnabledIn, long seed)
	    {
	        this.worldObj = worldObjIn;
	        this.mapFeaturesEnabled = mapFeaturesEnabledIn;
	        this.rand = new Random(seed);
	        this.lperlinNoise1 = new NoiseGeneratorOctaves(this.rand, 16);
	        this.lperlinNoise2 = new NoiseGeneratorOctaves(this.rand, 16);
	        this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
	        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
	        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
	        this.islandNoise = new NoiseGeneratorSimplex(this.rand);

	        net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd ctx =
	                new net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd(lperlinNoise1, lperlinNoise2, perlinNoise1, noiseGen5, noiseGen6, islandNoise);
	        ctx = net.minecraftforge.event.terraingen.TerrainGen.getModdedNoiseGenerators(worldObjIn, this.rand, ctx);
	        this.lperlinNoise1 = ctx.getLPerlin1();
	        this.lperlinNoise2 = ctx.getLPerlin2();
	        this.perlinNoise1 = ctx.getPerlin();
	        this.noiseGen5 = ctx.getDepth();
	        this.noiseGen6 = ctx.getScale();
	        this.islandNoise = ctx.getIsland();
	    }

	    /**
	     * Generates a bare-bones chunk of nothing but stone or ocean blocks, formed, but featureless.
	     */
	    public void setBlocksInChunk(int x, int z, ChunkPrimer primer)
	    {
	        int i = 2;
	        int j = 3;
	        int k = 33;
	        int l = 3;
	        this.buffer = this.getHeights(this.buffer, x * 2, 0, z * 2, 3, 33, 3);

	        for (int i1 = 0; i1 < 2; ++i1)
	        {
	            for (int j1 = 0; j1 < 2; ++j1)
	            {
	                for (int k1 = 0; k1 < 32; ++k1)
	                {
	                    double d0 = 0.25D;
	                    double d1 = this.buffer[((i1 + 0) * 3 + j1 + 0) * 33 + k1 + 0];
	                    double d2 = this.buffer[((i1 + 0) * 3 + j1 + 1) * 33 + k1 + 0];
	                    double d3 = this.buffer[((i1 + 1) * 3 + j1 + 0) * 33 + k1 + 0];
	                    double d4 = this.buffer[((i1 + 1) * 3 + j1 + 1) * 33 + k1 + 0];
	                    double d5 = (this.buffer[((i1 + 0) * 3 + j1 + 0) * 33 + k1 + 1] - d1) * 0.25D;
	                    double d6 = (this.buffer[((i1 + 0) * 3 + j1 + 1) * 33 + k1 + 1] - d2) * 0.25D;
	                    double d7 = (this.buffer[((i1 + 1) * 3 + j1 + 0) * 33 + k1 + 1] - d3) * 0.25D;
	                    double d8 = (this.buffer[((i1 + 1) * 3 + j1 + 1) * 33 + k1 + 1] - d4) * 0.25D;

	                    for (int l1 = 0; l1 < 4; ++l1)
	                    {
	                        double d9 = 0.125D;
	                        double d10 = d1;
	                        double d11 = d2;
	                        double d12 = (d3 - d1) * 0.125D;
	                        double d13 = (d4 - d2) * 0.125D;

	                        for (int i2 = 0; i2 < 8; ++i2)
	                        {
	                            double d14 = 0.125D;
	                            double d15 = d10;
	                            double d16 = (d11 - d10) * 0.125D;

	                            for (int j2 = 0; j2 < 8; ++j2)
	                            {
	                                IBlockState iblockstate = AIR;

	                                if (d15 > 0.0D)
	                                {
	                                    iblockstate = VOIDSTONE;
	                                }

	                                int k2 = i2 + i1 * 8;
	                                int l2 = l1 + k1 * 4;
	                                int i3 = j2 + j1 * 8;
	                                primer.setBlockState(k2, l2, i3, iblockstate);
	                                d15 += d16;
	                            }

	                            d10 += d12;
	                            d11 += d13;
	                        }

	                        d1 += d5;
	                        d2 += d6;
	                        d3 += d7;
	                        d4 += d8;
	                    }
	                }
	            }
	        }
	    }

	    public void buildSurfaces(ChunkPrimer primer)
	    {
	        if (!net.minecraftforge.event.ForgeEventFactory.onReplaceBiomeBlocks(this, this.chunkX, this.chunkZ, primer, this.worldObj)) return;
	        for (int i = 0; i < 16; ++i)
	        {
	            for (int j = 0; j < 16; ++j)
	            {
	                int k = 1;
	                int l = -1;
	                IBlockState iblockstate = ASH;
	                IBlockState iblockstate1 = VOIDSTONE;

	                for (int i1 = 127; i1 >= 0; --i1)
	                {
	                    IBlockState iblockstate2 = primer.getBlockState(i, i1, j);

	                    if (iblockstate2.getMaterial() == Material.AIR)
	                    {
	                        l = -1;
	                    }
	                    else if (iblockstate2.getBlock() == VoidBlocks.VOIDSTONE)
	                    {
	                        if (l == -1)
	                        {
	                            l = 1;

	                            if (i1 >= 0)
	                            {
	                                primer.setBlockState(i, i1, j, iblockstate);
	                            }
	                            else
	                            {
	                                primer.setBlockState(i, i1, j, iblockstate1);
	                            }
	                        }
	                        else if (l > 0)
	                        {
	                            --l;
	                            primer.setBlockState(i, i1, j, iblockstate1);
	                        }
	                    }
	                }
	            }
	        }
	    }

	    public Chunk provideChunk(int x, int z)
	    {
	        this.chunkX = x; this.chunkZ = z;
	        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
	        ChunkPrimer chunkprimer = new ChunkPrimer();
	        this.biomesForGeneration = this.worldObj.getBiomeProvider().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
	        this.setBlocksInChunk(x, z, chunkprimer);
	        this.buildSurfaces(chunkprimer);


	        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
	        byte[] abyte = chunk.getBiomeArray();

	        for (int i = 0; i < abyte.length; ++i)
	        {
	            abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
	        }

	        chunk.generateSkylightMap();
	        return chunk;
	    }

	    private double[] getHeights(double[] p_185963_1_, int p_185963_2_, int p_185963_3_, int p_185963_4_, int p_185963_5_, int p_185963_6_, int p_185963_7_)
	    {
	        net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField event = new net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField(this, p_185963_1_, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_);
	        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
	        if (event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) return event.getNoisefield();

	        if (p_185963_1_ == null)
	        {
	            p_185963_1_ = new double[p_185963_5_ * p_185963_6_ * p_185963_7_];
	        }

	        double d0 = 684.412D;
	        double d1 = 684.412D;
	        d0 = d0 * 2.0D;
	        this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0 / 80.0D, 4.277575000000001D, d0 / 80.0D);
	        this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
	        this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
	        int i = p_185963_2_ / 2;
	        int j = p_185963_4_ / 2;
	        int k = 0;

	        for (int l = 0; l < p_185963_5_; ++l)
	        {
	            for (int i1 = 0; i1 < p_185963_7_; ++i1)
	            {

	                for (int j1 = 0; j1 < p_185963_6_; ++j1)
	                {
	                    double d2 = this.ar[k] / 512.0D;
	                    double d3 = this.br[k] / 512.0D;
	                    double d5 = (this.pnr[k] / 10.0D + 1.0D) / 2.0D;
	                    double d4;

	                    if (d5 < 0.0D)
	                    {
	                        d4 = d2;
	                    }
	                    else if (d5 > 1.0D)
	                    {
	                        d4 = d3;
	                    }
	                    else
	                    {
	                        d4 = d2 + (d3 - d2) * d5;
	                    }

	                    d4 = d4 - 8.0D;
	                    int k1 = 2;

	                    if (j1 > p_185963_6_ / 2 - k1)
	                    {
	                        double d6 = (double)((float)(j1 - (p_185963_6_ / 2 - k1)) / 64.0F);
	                        d6 = MathHelper.clamp_double(d6, 0.0D, 1.0D);
	                        d4 = d4 * (1.0D - d6) + -3000.0D * d6;
	                    }

	                    k1 = 8;

	                    if (j1 < k1)
	                    {
	                        double d7 = (double)((float)(k1 - j1) / ((float)k1 - 1.0F));
	                        d4 = d4 * (1.0D - d7) + -30.0D * d7;
	                    }

	                    p_185963_1_[k] = d4;
	                    ++k;
	                }
	            }
	        }

	        return p_185963_1_;
	    }

	    public void populate(int x, int z)
	    {
	        BlockFalling.fallInstantly = true;
	        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(true, this, this.worldObj, this.rand, x, z, false);
	        BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);


	        this.worldObj.getBiomeGenForCoords(blockpos.add(16, 0, 16)).decorate(this.worldObj, this.worldObj.rand, blockpos);
	        long i = (long)x * (long)x + (long)z * (long)z;


	        net.minecraftforge.event.ForgeEventFactory.onChunkPopulate(false, this, this.worldObj, this.rand, x, z, false);
	        BlockFalling.fallInstantly = false;
	    }

	    public boolean generateStructures(Chunk chunkIn, int x, int z)
	    {
	        return false;
	    }

	    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
	    {
	        return this.worldObj.getBiomeGenForCoords(pos).getSpawnableList(creatureType);
	    }

	    @Nullable
	    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position)
	    {
	        return null;
	    }

	    public void recreateStructures(Chunk chunkIn, int x, int z)
	    {
	    }
}
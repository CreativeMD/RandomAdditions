package com.creativemd.randomadditions.common.world;

import java.util.Random;

import com.creativemd.randomadditions.common.systems.ore.SubSystemOre;
import com.creativemd.randomadditions.core.RandomAdditions;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId)
		{
			case -1: generateNether(world, random, chunkX*16, chunkZ*16);
			case 0: generateSurface(world, random, chunkX*16, chunkZ*16);
		}
	}
	
	public static boolean generateCopper;
	public static boolean generateTin;
	public static boolean generateRuby;
	public static boolean generateTourmaline;
	
	public void generateSurface(World world, Random random, int blockX, int blockZ) 
	{
		for (int i = 0; i < RandomAdditions.materials.size(); i++) {
			int meta = SubSystemOre.instance.getMetaByName(RandomAdditions.materials.get(i).name);
			switch(RandomAdditions.materials.get(i).generateOre)
			{
			case 1:
				addOreSpawn(meta, SubSystemOre.instance.block, world, random, blockX, blockZ, 0, 7, 1, 0, 16);
				break;
			case 2:
				addOreSpawn(meta, SubSystemOre.instance.block, world, random, blockX, blockZ, 0, 8, 2, 0, 32);
				break;
			case 3:
				addOreSpawn(meta, SubSystemOre.instance.block, world, random, blockX, blockZ, 0, 8, 20, 0, 64);
				break;
			default:
				break;
			}
		}
	}
	
	public void addOreSpawn(int meta, Block block, World world, Random random, int blockXPos, int blockZPos, int minVeinSize, int maxVeinSize, int chancesToSpawn, int minY, int maxY )
    {
        WorldGenMinable minable = new WorldGenMinable(block, meta, (minVeinSize + random.nextInt(maxVeinSize - minVeinSize)), Blocks.stone);
        for(int i = 0; i < chancesToSpawn; i++)
        {
            int posX = blockXPos + random.nextInt(16);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(16);
            minable.generate(world, random, posX, posY, posZ);
        }
    }
	
	
	public void generateNether(World world, Random random, int blockX, int blockY) 
	{
	
	}
	
	public String getName()
	{
		return "Random Additions";
	}
	
}

package com.creativemd.randomadditions.common.systems.machine.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.machine.MachineRecipe;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.tileentity.TileEntityMachine;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.common.registry.GameRegistry;

public class Crusher extends SubBlockMachine{

	public Crusher(SubBlockSystem system) {
		super("Crusher", system);
	}

	@Override
	public int getNumberOfInputs() {
		return 1;
	}

	@Override
	public void registerRecipes() {
		registerRecipe(new MachineRecipe("stone", new ItemStack(Blocks.cobblestone)));
		registerRecipe(new MachineRecipe("cobblestone", new ItemStack(Blocks.gravel)));
		registerRecipe(new MachineRecipe(Blocks.gravel, new ItemStack(Blocks.sand)));
		registerRecipe(new MachineRecipe(Blocks.sandstone, new ItemStack(Blocks.sand, 2)));
		registerRecipe(new MachineRecipe(Blocks.nether_brick, new ItemStack(Items.netherbrick, 2)));
		
		//Iron
		registerRecipe(new MachineRecipe(new ItemStack(Items.iron_axe), new ItemStack(Items.iron_ingot, 3)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.iron_hoe), new ItemStack(Items.iron_ingot, 2)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.iron_pickaxe), new ItemStack(Items.iron_ingot, 3)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.iron_shovel), new ItemStack(Items.iron_ingot, 1)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.iron_helmet), new ItemStack(Items.iron_ingot, 5)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.iron_chestplate), new ItemStack(Items.iron_ingot, 8)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.iron_leggings), new ItemStack(Items.iron_ingot, 7)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.iron_boots), new ItemStack(Items.iron_ingot, 4)));
		
		//Gold
		registerRecipe(new MachineRecipe(new ItemStack(Items.golden_axe), new ItemStack(Items.gold_ingot, 3)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.golden_hoe), new ItemStack(Items.gold_ingot, 2)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.golden_pickaxe), new ItemStack(Items.gold_ingot, 3)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.golden_shovel), new ItemStack(Items.gold_ingot, 1)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.golden_helmet), new ItemStack(Items.gold_ingot, 5)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.golden_chestplate), new ItemStack(Items.gold_ingot, 8)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.golden_leggings), new ItemStack(Items.gold_ingot, 7)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.golden_boots), new ItemStack(Items.gold_ingot, 4)));
		
		//Diamond
		registerRecipe(new MachineRecipe(new ItemStack(Items.diamond_axe), new ItemStack(Items.diamond, 3)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.diamond_hoe), new ItemStack(Items.diamond, 2)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.diamond_pickaxe), new ItemStack(Items.diamond, 3)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.diamond_shovel), new ItemStack(Items.diamond, 1)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.diamond_helmet), new ItemStack(Items.diamond, 5)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.diamond_chestplate), new ItemStack(Items.diamond, 8)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.diamond_leggings), new ItemStack(Items.diamond, 7)));
		registerRecipe(new MachineRecipe(new ItemStack(Items.diamond_boots), new ItemStack(Items.diamond, 4)));
		
		//Flower
		//Red
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.red_flower, 1, 0), new ItemStack(Items.dye, 4, 1)));
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.red_flower, 1, 4), new ItemStack(Items.dye, 4, 1)));
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.double_plant, 1, 4), new ItemStack(Items.dye, 8, 1)));
		
		//Green
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.cactus, 1, 0), new ItemStack(Items.dye, 4, 2)));
		
		//Lightgray
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.red_flower, 1, 3), new ItemStack(Items.dye, 4, 7)));
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.red_flower, 1, 6), new ItemStack(Items.dye, 4, 7)));
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.red_flower, 1, 8), new ItemStack(Items.dye, 4, 7)));
		
		//Pink
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.red_flower, 1, 7), new ItemStack(Items.dye, 4, 9)));
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.double_plant, 1, 5), new ItemStack(Items.dye, 8, 9)));
		
		//Yellow
		registerRecipe(new MachineRecipe(Blocks.yellow_flower, new ItemStack(Items.dye, 4, 11)));
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.double_plant, 1, 0), new ItemStack(Items.dye, 8, 11)));
		
		//Blue
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.red_flower, 1, 1), new ItemStack(Items.dye, 4, 12)));
		
		//Magenta
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.red_flower, 1, 2), new ItemStack(Items.dye, 4, 13)));
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.double_plant, 1, 1), new ItemStack(Items.dye, 8, 13)));
		
		//Orange
		registerRecipe(new MachineRecipe(new ItemStack(Blocks.red_flower, 1, 5), new ItemStack(Items.dye, 4, 14)));
		
		//Sugar
		registerRecipe(new MachineRecipe(Items.reeds, new ItemStack(Items.sugar, 3)));
		
		//Flour
		registerRecipe(new MachineRecipe(Items.wheat, RandomItem.flour.getItemStack()));
		
		//Pumkin
		registerRecipe(new MachineRecipe(Blocks.pumpkin, new ItemStack(Items.pumpkin_seeds, 8)));
		
		//Melon
		registerRecipe(new MachineRecipe(Items.melon, new ItemStack(Items.melon_seeds, 4)));
		
		//Blaze
		registerRecipe(new MachineRecipe(Items.blaze_rod, new ItemStack(Items.blaze_powder, 4)));
		
		//Bonemeal
		registerRecipe(new MachineRecipe(Items.bone, new ItemStack(Items.dye, 6, 15), 500));
		
		registerRecipe(new MachineRecipe(Blocks.coal_ore, new ItemStack(Items.coal, 4)));
		registerRecipe(new MachineRecipe(Blocks.redstone_ore, new ItemStack(Items.redstone, 8)));
		
		//Crush ingots
		registerRecipe(new MachineRecipe("ingotAluminium", RandomItem.aluminiumDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotBronze", RandomItem.bronzeDust.getItemStack()));
		registerRecipe(new MachineRecipe("gemCoal", RandomItem.coalDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotCopper", RandomItem.copperDust.getItemStack()));
		registerRecipe(new MachineRecipe("gemDiamond", RandomItem.diamondDust.getItemStack()));
		registerRecipe(new MachineRecipe("gemEmerald", RandomItem.emeraldDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotGold", RandomItem.goldDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotIron", RandomItem.ironDust.getItemStack()));
		registerRecipe(new MachineRecipe("gemLapis", RandomItem.lapisDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotLead", RandomItem.leadDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotObsidian", RandomItem.obsidianDust.getItemStack()));
		registerRecipe(new MachineRecipe("gemRuby", RandomItem.rubyDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotSilver", RandomItem.silverDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotTin", RandomItem.tinDust.getItemStack()));
		registerRecipe(new MachineRecipe("gemTourmaline", RandomItem.tourmalineDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotTungsten", RandomItem.tungstenDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotFerrous", RandomItem.ferrousDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotZinc", RandomItem.zincDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotOsmium", RandomItem.osmiumDust.getItemStack()));
		registerRecipe(new MachineRecipe("ingotYellorite", RandomItem.yelloriteDust.getItemStack()));
		
		//Crush ores
		registerRecipe(new MachineRecipe("oreAluminium", new ItemStack(RandomAdditions.items, 2, RandomItem.aluminiumDust.id)));
		registerRecipe(new MachineRecipe("oreBronze", new ItemStack(RandomAdditions.items, 2, RandomItem.bronzeDust.id)));
		registerRecipe(new MachineRecipe("gemCoal", new ItemStack(RandomAdditions.items, 2, RandomItem.coalDust.id)));
		registerRecipe(new MachineRecipe("oreCopper", new ItemStack(RandomAdditions.items, 2, RandomItem.copperDust.id)));
		registerRecipe(new MachineRecipe("oreDiamond", new ItemStack(RandomAdditions.items, 2, RandomItem.diamondDust.id)));
		registerRecipe(new MachineRecipe("oreEmerald", new ItemStack(RandomAdditions.items, 2, RandomItem.emeraldDust.id)));
		registerRecipe(new MachineRecipe("oreGold", new ItemStack(RandomAdditions.items, 2, RandomItem.goldDust.id)));
		registerRecipe(new MachineRecipe("oreIron", new ItemStack(RandomAdditions.items, 2, RandomItem.ironDust.id)));
		registerRecipe(new MachineRecipe("oreLapis", new ItemStack(RandomAdditions.items, 2, RandomItem.lapisDust.id)));
		registerRecipe(new MachineRecipe("oreLead", new ItemStack(RandomAdditions.items, 2, RandomItem.leadDust.id)));
		registerRecipe(new MachineRecipe("oreObsidian", new ItemStack(RandomAdditions.items, 2, RandomItem.obsidianDust.id)));
		registerRecipe(new MachineRecipe("oreRuby", new ItemStack(RandomAdditions.items, 2, RandomItem.rubyDust.id)));
		registerRecipe(new MachineRecipe("oreSilver", new ItemStack(RandomAdditions.items, 2, RandomItem.silverDust.id)));
		registerRecipe(new MachineRecipe("oreTin", new ItemStack(RandomAdditions.items, 2, RandomItem.tinDust.id)));
		registerRecipe(new MachineRecipe("oreTourmaline", new ItemStack(RandomAdditions.items, 2, RandomItem.tourmalineDust.id)));
		registerRecipe(new MachineRecipe("oreTungsten", new ItemStack(RandomAdditions.items, 2, RandomItem.tungstenDust.id)));
		registerRecipe(new MachineRecipe("oreFerrous", new ItemStack(RandomAdditions.items, 2, RandomItem.ferrousDust.id)));
		registerRecipe(new MachineRecipe("oreZinc", new ItemStack(RandomAdditions.items, 2, RandomItem.zincDust.id)));
		registerRecipe(new MachineRecipe("oreOsmium", new ItemStack(RandomAdditions.items, 2, RandomItem.osmiumDust.id)));
		registerRecipe(new MachineRecipe("oreYellorite", new ItemStack(RandomAdditions.items, 2, RandomItem.yelloriteDust.id)));
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.cobblestone.getIcon(side, meta);
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		Block block = Blocks.planks;
		cubes.add(new CubeObject(0, 0, 0, 1, 0.1, 1, Blocks.stonebrick));
		cubes.add(new CubeObject(0.1, 0.1, 0.1, 0.9, 0.2, 0.9, Blocks.cobblestone));
		cubes.add(new CubeObject(0.3, 0.7, 0.3, 0.7, 1, 0.7, Blocks.cobblestone));
		cubes.add(new CubeObject(0.45, 0, 0, 0.55, 0.9, 0.1, block));
		cubes.add(new CubeObject(0.45, 0, 0.9, 0.55, 0.9, 1, block));
		cubes.add(new CubeObject(0.45, 0.8, 0.1, 0.55, 0.9, 0.9, block));
		return cubes;
	}
	
	@Override
	public void drawRender(TileEntity entity, double x, double y, double z)
	{
		if(entity instanceof TileEntityMachine)
		{
			float rotation = System.nanoTime()/30000000;
			float speed = ((TileEntityMachine) entity).getCraftingSpeed();
			if(entity instanceof TileEntityMachine)
				rotation *= (double)speed * 0.6;
			if(((TileEntityMachine) entity).progress == 0)
				rotation = 0;
			RenderHelper3D.renderBlock(Blocks.stone_slab, x+0.5, y+0.35, z+0.5, 0.6, 0.3, 0.6, 0, 45, 0);
			RenderHelper3D.renderBlock(Blocks.stone_slab, x+0.5, y+0.5, z+0.5, 0.6, 0.3, 0.6, 0, rotation, 0);
			RenderHelper3D.renderBlock(Blocks.planks, x+0.5, y+0.6, z+0.5, 0.25, 0.7, 0.25, 0, rotation, 0);
		}
	}

	@Override
	public void addRecipe(ItemStack battery, ItemStack output) {
		GameRegistry.addRecipe(output, new Object[]
				{
				"XAX", "WPW", "CSC", 'A', battery, 'X', Items.stick, 'W', Blocks.planks, 'C', Blocks.cobblestone, 'P', Blocks.piston, 'S', new ItemStack(Blocks.stone_slab, 1, 0)
				});
	}
	
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		TileEntityMachine machine = (TileEntityMachine) par1World.getTileEntity(par2, par3, par4);
		if(machine.progress > 0)
		{
			float speed = machine.getCraftingSpeed();
			for (int i = 0; i < speed; i++) {
				par1World.spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.stone_slab) + "_0", par2+0.5, par3+0.35, par4+0.5, 0, 0, 0);	
			}
		}
	}

	@Override
	public void renderProgressField(double percent) {
		for (int i = 0; i < 11; i++) {
        	RenderHelper2D.renderIcon(Blocks.piston_head.getIcon(0, 2), i*8+4, 40+getOffset(i), percent, true, 0, 8, 8);
        	RenderHelper2D.renderIcon(Blocks.piston_head.getIcon(0, 2), i*8+4, 3-getOffset(i), percent, true, 180, 8, 8);
		}
	}
	
	public static int getOffset(int index)
	{
		return (int)((Math.sin((double)System.nanoTime()/(250000000D*Math.sin((index+1)*0.369D)))-1)*2.5D);
	}

	@Override
	public int getPlayTime() {
		return 40;
	}
	
	@Override
	public float getPlayVolume(){
		return 0.25F;
	}
}

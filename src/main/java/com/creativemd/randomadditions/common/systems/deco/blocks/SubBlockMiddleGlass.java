package com.creativemd.randomadditions.common.systems.deco.blocks;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.deco.SubBlockDeco;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockMiddleGlass extends SubBlockDeco {

	public SubBlockMiddleGlass(String name, SubBlockSystem system) {
		super(name, system);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.glass.getIcon(side, meta);
	}
	
	@Override
	public TileEntityRandom getTileEntity() {
		return null;
	}
	
	public CubeObject getBox(IBlockAccess world, int x, int y, int z)
	{
		return new CubeObject(0, 0.45, 0, 1, 0.55, 1);
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(ItemStack stack, IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0, 0.45, 0, 1, 0.55, 1));
		return cubes;
	}
	
	@Override
	public void onRegister()
	{
		ItemStack output = getItemStack();
		output.stackSize = 2;
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
				{
				"WW", 'W', Blocks.glass_pane
				}));
	}
}

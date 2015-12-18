package com.creativemd.randomadditions.common.systems.battery;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.battery.tileentity.TileEntityBattery;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockBattery extends SubBlock{
	
	public int storage;
	public int output;
	public int input;
	public Block block;
	
	@SideOnly(Side.CLIENT)
	public IIcon inputTexture;
	@SideOnly(Side.CLIENT)
	public IIcon outputTexture;
	
	public SubBlockBattery(int storage, int output, int input, Block block, String name, SubBlockSystem system) {
		super(name, system);
		this.storage = storage;
		this.input = input;
		this.output = output;
		this.block = block;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		return new SubGuiBattery((TileEntityBattery) tileEntity, this);
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		return new SubContainerBattery((TileEntityRandom) tileEntity, player);
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityBattery();
	}

	@Override
	public boolean isSolid(TileEntity tileEntity) {
		return true;
	}
	
	@Override
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		if(tileEntity instanceof TileEntityBattery)
		{
			if(stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			((TileEntityBattery) tileEntity).receivePower(stack.stackTagCompound.getFloat("power"));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		int power = 0;
		if(stack.stackTagCompound != null)
			power = stack.stackTagCompound.getInteger("power");
		list.add(power + "/" + storage + " RA");
		list.add("Input/Output: " + input + "/" + output);
		//list.add("Output: " + system.output);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if(side == 3)
			return inputTexture;
		else
			return outputTexture;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side, int meta)
    {
		TileEntity entity = world.getTileEntity(x, y, z);
		if(entity instanceof TileEntityBattery)
			if(((TileEntityBattery) entity).direction == side)
				return inputTexture;
			else
				return outputTexture;
        return this.getIcon(side, world.getBlockMetadata(x, y, z));
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcon(IIconRegister IconRegistry)
    {
		inputTexture = IconRegistry.registerIcon(RandomAdditions.modid + ":" + "BatteryInput");
		outputTexture = IconRegistry.registerIcon(RandomAdditions.modid + ":" + "BatteryOutput");
    }
	
	@Override
	public ArrayList<ItemStack> getDrop(IBlockAccess world, int x, int y, int z, int fortune)
	{
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y, int z) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0.01, 0.01, 0.01, 0.99, 0.99, 0.99, block));
		cubes.add(new CubeObject());
		return cubes;
	}
	
	@Override
	public ArrayList<ItemStack> getExtraDrop(TileEntity tileEntity)
	{
		ArrayList<ItemStack> stacks = super.getExtraDrop(tileEntity);
		if(tileEntity instanceof TileEntityBattery)
		{
			ItemStack stack = new ItemStack(system.block, 1, getID());
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setFloat("power", ((TileEntityBattery) tileEntity).getCurrentPower());
			stacks.add(stack);
		}
		return stacks;
	}
	
	@Override
	public int getRotation()
	{
		return 2;
	}
}

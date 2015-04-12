package com.creativemd.randomadditions.common.item.items;

import java.util.List;

import com.creativemd.randomadditions.common.systems.battery.SubSystemBattery;
import com.creativemd.randomadditions.common.systems.cable.SubSystemCable;
import com.creativemd.randomadditions.common.systems.cable.tileentity.TileEntityWire;
import com.creativemd.randomadditions.core.RandomAdditions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RandomItemWire extends RandomItem{

	public RandomItemWire(String name) {
		super(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		if(stack.hasTagCompound())
		{
			int x = stack.stackTagCompound.getInteger("x");
			int y = stack.stackTagCompound.getInteger("y");
			int z = stack.stackTagCompound.getInteger("z");
			list.add("Pos: " + x + ", " + y + ", " + z);
		}
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if(player.isSneaking())
		{
			stack.stackTagCompound = null;
			if(!world.isRemote)
				player.addChatMessage(new ChatComponentText("Removed position"));
			return true;
		}
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityWire)
		{
			if(!world.isRemote)
			{
				if(stack.hasTagCompound())
				{
					int posX = stack.stackTagCompound.getInteger("x");
					int posY = stack.stackTagCompound.getInteger("y");
					int posZ = stack.stackTagCompound.getInteger("z");
					TileEntity tileEntity2 = world.getTileEntity(posX, posY, posZ);
					if(tileEntity2 instanceof TileEntityWire)
					{
						//Calculate distance
						TileEntityWire wire = (TileEntityWire) tileEntity;
						TileEntityWire wire2 = (TileEntityWire) tileEntity2;
						int distance = wire.getDistance(wire2);
						if(distance <= 64)
						{
							int found = 0;
							for (int i = 0; i < player.inventory.mainInventory.length; i++) {
								if(RandomItem.getRandomItem(player.inventory.mainInventory[i]) instanceof RandomItemWire)
									found += player.inventory.mainInventory[i].stackSize;
							}
							if(found >= distance)
							{
								if(wire == wire2)
									return true;
								if(wire.checkConnection(wire2))
								{
									if(wire.connections.contains(wire2) || wire2.connections.contains(wire))
									{
										player.addChatMessage(new ChatComponentText("Already Connected!"));
										return true;
									}
									wire.connections.add(wire2);
									wire2.connections.add(wire);
									wire.updateBlock();
									wire2.updateBlock();
									int i = 0;
									
									//Consume stacks
									int tempdistance = distance;
									while(i < player.inventory.mainInventory.length && tempdistance > 0)
									{
										ItemStack tempStack = player.inventory.mainInventory[i];
										if(RandomItem.getRandomItem(tempStack) instanceof RandomItemWire)
										{
											int stackSize = tempStack.stackSize;
											int consume = tempdistance;
											if(stackSize < consume)
												consume = stackSize;
											tempStack.stackSize -= consume;
											if(tempStack.stackSize == 0)
												player.inventory.mainInventory[i] = null;
											player.inventory.mainInventory[i] = tempStack;
											tempdistance -= consume;
										}
										i++;
									}
									stack.stackTagCompound = null;
									player.addChatMessage(new ChatComponentText("Connected! distance=" + distance));
								}else{
									player.addChatMessage(new ChatComponentText("Cannot cannot connect (Blocks between)! distance=" + distance));
								}
							}else{
								player.addChatMessage(new ChatComponentText("Not enough wire! distance=" + distance));
							}
						}else{
							player.addChatMessage(new ChatComponentText("Cannot connect this blocks (Distance larger than 64)! distance=" + distance));
						}
					}
				}else{
					stack.stackTagCompound = new NBTTagCompound();
					stack.stackTagCompound.setInteger("x", x);
					stack.stackTagCompound.setInteger("y", y);
					stack.stackTagCompound.setInteger("z", z);
					player.addChatMessage(new ChatComponentText("Bound this to " + x + ", " + y + ", " + z));
				}
			}
			return true;
		}
		return false;
    }
	
	@Override
	public void onRegister()
	{
		ItemStack stack = getItemStack();
		stack.stackSize = 8;
		GameRegistry.addRecipe(new ShapedOreRecipe(stack, new Object[]
				{
				"AXA", "XBX", "AXA", 'A', RandomItem.compressedplastic.getItemStack(), 'X', "ingotCopper", 'B', SubSystemCable.instance.getItemStack(0)
				}));
	}
	
}

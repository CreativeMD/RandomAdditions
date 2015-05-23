package com.creativemd.randomadditions.common.subsystem.waila;

import java.util.List;

import com.creativemd.randomadditions.common.energy.core.EnergyCable;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.subsystem.BlockSub;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.systems.producer.tileentity.TileEntityProducer;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.Method;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;

@Interface(iface = "mcp.mobius.waila.api.IWailaDataProvider", modid = "Waila")
public class WailaHandler implements IWailaDataProvider {

    public static void callbackRegister(IWailaRegistrar register) {
		WailaHandler instance = new WailaHandler();
		register.registerBodyProvider(instance, BlockSub.class);
		
		register.registerNBTProvider(instance, BlockSub.class);
	}
	
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return accessor.getStack();
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		
		BlockSub block = (BlockSub) accessor.getBlock();
		currenttip = block.system.getSubBlock(accessor.getMetadata()).getWailaBody(itemStack, currenttip, accessor, config);
		
		TileEntity te = accessor.getTileEntity();
		NBTTagCompound nbt = accessor.getNBTData();
		if(!nbt.getBoolean("empty"))
			te.readFromNBT(accessor.getNBTData());
		if(te instanceof EnergyComponent)
		{
			currenttip.add(((EnergyComponent) te).getCurrentPower() + "/" + ((EnergyComponent) te).getInteralStorage() + " RA");
			if(!((EnergyComponent) te).isActive())
				currenttip.add("not running");
		}
		if(te instanceof EnergyCable)
		{
			currenttip.add(((EnergyCable) te).transmitedPower + "/" + ((EnergyCable) te).getTransmitablePower() + " RA");			
		}
		if(te instanceof TileEntityProducer)
		{
			currenttip.add("Production: " + ((TileEntityProducer) te).speed + "/" + ((TileEntityProducer) te).maxSpeed + " RA");		
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack,
			List<String> currenttip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te,
			NBTTagCompound tag, World world, int x, int y, int z) {
		if (te != null && player.openContainer == null)
            te.writeToNBT(tag);
		else
			tag.setBoolean("empty", true);
        return tag;
	}

}

package com.creativemd.randomadditions.common.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.container.ContainerSub;
import com.creativemd.creativecore.common.packet.CreativeCorePacket;
import com.creativemd.randomadditions.common.redstone.IRedstoneControl;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RedstonePacket extends CreativeCorePacket{
	
	public int x;
	public int y;
	public int z;
	public int mode;
	
	public RedstonePacket(){
		
	}
	
	public RedstonePacket(int x, int y, int z, int mode){
		this.x = x;
		this.y = y;
		this.z = z;
		this.mode = mode;
	}
	
	@Override
	public void writeBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(mode);
	}

	@Override
	public void readBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		mode = buf.readInt();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void executeClient(EntityPlayer player) {
		
	}

	@Override
	public void executeServer(EntityPlayer player) {
		TileEntity entity = player.worldObj.getTileEntity(x, y, z);
		if(entity instanceof IRedstoneControl)
			((IRedstoneControl) entity).setMode(mode);
	}

}

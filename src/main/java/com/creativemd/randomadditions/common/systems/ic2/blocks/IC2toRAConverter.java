package com.creativemd.randomadditions.common.systems.ic2.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.ic2.SubBlockIC2;
import com.creativemd.randomadditions.common.systems.ic2.SubContainerIC2;
import com.creativemd.randomadditions.common.systems.ic2.SubGuiIC2toRA;
import com.creativemd.randomadditions.common.systems.ic2.tileentity.TileEntityIC2toRA;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IC2toRAConverter extends SubBlockIC2{

	public IC2toRAConverter(SubBlockSystem system) {
		super("IC2toRAConverter", system);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		return new SubGuiIC2toRA((TileEntityIC2toRA) tileEntity, this);
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		return new SubContainerIC2((TileEntityRandom) tileEntity, player);
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityIC2toRA();
	}

	@Override
	public String getTextureName() {
		return "IC2toRA";
	}

}

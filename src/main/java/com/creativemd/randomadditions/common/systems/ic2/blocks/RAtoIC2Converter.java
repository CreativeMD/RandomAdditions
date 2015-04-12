package com.creativemd.randomadditions.common.systems.ic2.blocks;

import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.SubGuiTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.ic2.SubBlockIC2;
import com.creativemd.randomadditions.common.systems.ic2.SubContainerIC2;
import com.creativemd.randomadditions.common.systems.ic2.SubGuiRAtoIC2;
import com.creativemd.randomadditions.common.systems.ic2.tileentity.TileEntityRAtoIC2;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RAtoIC2Converter extends SubBlockIC2{

	public RAtoIC2Converter(SubBlockSystem system) {
		super("RAtoIC2Converter", system);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity) {
		return new SubGuiRAtoIC2((TileEntityRAtoIC2) tileEntity, this);
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity) {
		return new SubContainerIC2((TileEntityRandom) tileEntity);
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityRAtoIC2();
	}

	@Override
	public String getTextureName() {
		return "RAtoIC2";
	}

}

package com.creativemd.randomadditions.common.systems.enchant.blocks;

import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.SubGuiTileEntity;
import com.creativemd.randomadditions.common.systems.enchant.SubBlockEnchant;

public class DefaultEnchantTable extends SubBlockEnchant{

	public DefaultEnchantTable(String name, SubBlockSystem system) {
		super(name, system);
	}

	@Override
	public int getInventorySize() {
		return 0;
	}

	@Override
	public SubGuiTileEntity getGui(TileEntity tileEntity) {
		return null;
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity) {
		return null;
	}

}

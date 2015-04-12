package com.creativemd.randomadditions.common.systems.deco;

import net.minecraft.block.material.Material;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.deco.blocks.DefaultDeco;
import com.creativemd.randomadditions.common.systems.deco.blocks.SubBlockChair;
import com.creativemd.randomadditions.common.systems.deco.blocks.SubBlockMiddleGlass;
import com.creativemd.randomadditions.common.systems.deco.blocks.SubBlockShelf;
import com.creativemd.randomadditions.common.systems.deco.blocks.SubBlockSofa;
import com.creativemd.randomadditions.common.systems.deco.blocks.SubBlockTable;

public class SubSystemDeco extends SubBlockSystem<SubBlockDeco>{
	
	public static SubSystemDeco instance;
	
	public SubSystemDeco() {
		super("Decoration");
		instance = this;
	}

	@Override
	public SubBlockDeco getDefault() {
		return new DefaultDeco("DefaultDeco", this);
	}

	@Override
	public Material getBlockMaterial() {
		return Material.wood;
	}

	@Override
	public void registerBlocks() {
		registerBlock(new SubBlockSofa("Sofa", this));
		registerBlock(new SubBlockChair("Chair", this));
		registerBlock(new SubBlockTable("Table", this));
		SubBlockTable.initTables();
		registerBlock(new SubBlockMiddleGlass("MiddleGlass", this));
		registerBlock(new SubBlockShelf("Shelf", this));
	}

	@Override
	public String getHarvestTool() {
		return "axe";
	}

}

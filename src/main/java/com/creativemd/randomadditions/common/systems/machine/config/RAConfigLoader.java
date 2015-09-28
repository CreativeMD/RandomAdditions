package com.creativemd.randomadditions.common.systems.machine.config;

import com.creativemd.ingameconfigmanager.api.core.TabRegistry;
import com.creativemd.ingameconfigmanager.api.tab.ModTab;
import com.creativemd.randomadditions.common.systems.machine.SubSystemMachine;
import com.creativemd.randomadditions.core.RandomAdditions;

import net.minecraft.item.ItemStack;

public class RAConfigLoader {
	
	public static ModTab raTab = new ModTab("RandomAddition Machines", new ItemStack(SubSystemMachine.instance.block, 1, 0));
	
	public static void loadConfig()
	{
		for (int i = 0; i < SubSystemMachine.instance.blocks.size(); i++) {
			new RARecipeMachine(raTab, SubSystemMachine.instance.blocks.get(i));
		}
		TabRegistry.registerModTab(raTab);
	}
	
}

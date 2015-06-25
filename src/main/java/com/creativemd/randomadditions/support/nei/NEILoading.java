package com.creativemd.randomadditions.support.nei;

import com.creativemd.randomadditions.common.systems.machine.SubSystemMachine;

import codechicken.nei.api.API;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class NEILoading {
	
	public static void loadNEI()
	{
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			System.out.println("Loading NEI classes");
			SubSystemMachine.instance.furnace.neiClass = NEISmelter.class;
			SubSystemMachine.instance.crusher.neiClass = NEICrusher.class;
			SubSystemMachine.instance.pool.neiClass = NEICoolingPool.class;
			SubSystemMachine.instance.anvil.neiClass = NEIAnvil.class;
			SubSystemMachine.instance.saw.neiClass = NEISaw.class;
			for (int i = 0; i < SubSystemMachine.instance.blocks.size(); i++) {
				try {
					ClassLoader.getSystemClassLoader().loadClass(SubSystemMachine.instance.blocks.get(i).neiClass.getName());
				} catch (Exception e){
					System.out.println("Creating instance for NEI was not successful: " + SubSystemMachine.instance.blocks.get(i).name);
					e.printStackTrace();
				}
				try {
					NEIMachine neiMachine = (NEIMachine) SubSystemMachine.instance.blocks.get(i).neiClass.getConstructor().newInstance();
					API.registerRecipeHandler(neiMachine);
					API.registerUsageHandler(neiMachine);
				} catch (Exception e){
					System.out.println("Could not register NEIMachine: " + SubSystemMachine.instance.blocks.get(i).name);
					e.printStackTrace();
				}
			}
		}
	}
	
}

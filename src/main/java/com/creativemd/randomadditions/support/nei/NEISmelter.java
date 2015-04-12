package com.creativemd.randomadditions.support.nei;

import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.SubSystemMachine;

public class NEISmelter extends NEIMachine{

	@Override
	public SubBlockMachine getMachine() {
		return SubSystemMachine.instance.furnace;
	}

}

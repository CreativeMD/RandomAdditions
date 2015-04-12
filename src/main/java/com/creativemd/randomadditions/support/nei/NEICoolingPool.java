package com.creativemd.randomadditions.support.nei;

import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.SubSystemMachine;

public class NEICoolingPool extends NEIMachine{

	@Override
	public SubBlockMachine getMachine() {
		return SubSystemMachine.instance.pool;
	}
}

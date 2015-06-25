package com.creativemd.randomadditions.support.nei;

import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.SubSystemMachine;
import com.creativemd.randomadditions.core.RandomAdditions;

public class NEISaw extends NEIMachine{

	@Override
	public SubBlockMachine getMachine() {
		return SubSystemMachine.instance.saw;
	}
}

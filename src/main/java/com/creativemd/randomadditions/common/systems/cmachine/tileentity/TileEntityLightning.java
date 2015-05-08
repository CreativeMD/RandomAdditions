package com.creativemd.randomadditions.common.systems.cmachine.tileentity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.redstone.RedstoneControlHelper;
import com.creativemd.randomadditions.core.RandomAdditions;

public class TileEntityLightning extends EnergyComponent{
	
	public int range = 4;
	public int usePerHit = 300;
	public boolean canDischarge = false;
	
	@Override
	public boolean canRecieveEnergy(ForgeDirection direction) {
		return true;
	}

	@Override
	public boolean canProduceEnergy(ForgeDirection direction) {
		return false;
	}

	@Override
	public int getInteralStorage() {
		return 10000;
	}

	@Override
	public int getMaxOutput() {
		return 0;
	}

	@Override
	public int getMaxInput() {
		return 10000;
	}
	
	public EntityLivingBase getEntityInRange()
	{
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(xCoord-range, yCoord-range, zCoord-range, xCoord+1+range, yCoord+1+range, zCoord+1+range);
		List entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, box);
		EntityLivingBase living = null;
		double distance = range+1;
		for (int h = 0; h < entities.size(); h++)
		{
			double tempdistance = ((Entity) entities.get(h)).getDistance(xCoord+0.5, yCoord+0.5, zCoord+0.5);
			if(range >= tempdistance && tempdistance < distance)
			{
				distance = tempdistance;
				living = (EntityLivingBase) entities.get(h);
			}
				
		}
		return living;
	}
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(RedstoneControlHelper.handleRedstoneInput(this, this))
			{
				setInputPower(0);
				if(getCurrentPower() > usePerHit)
				{
					EntityLivingBase living = getEntityInRange();
					if(living != null)
					{
						if(living.attackEntityFrom(DamageSource.magic, 5F))
						{
							drainPower(usePerHit);
							worldObj.playSoundEffect(xCoord, yCoord, zCoord, RandomAdditions.modid + ":electricspark", 1, 1);
						}
					}
					if(!canDischarge)
					{
						canDischarge = true;
						updateBlock();
					}
				}else{
					if(canDischarge)
					{
						canDischarge = false;
						updateBlock();
					}
				}
			}else{
				if(canDischarge)
				{
					canDischarge = false;
					updateBlock();
				}
			}
		}
	}
	
}

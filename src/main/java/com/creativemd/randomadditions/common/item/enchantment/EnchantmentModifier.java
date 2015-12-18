package com.creativemd.randomadditions.common.item.enchantment;

import java.util.ArrayList;
import java.util.Random;

import com.creativemd.randomadditions.common.entity.EntityRandomArrow;
import com.creativemd.randomadditions.common.entity.EntityThrow;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.item.items.RandomItemBattery;
import com.creativemd.randomadditions.common.item.items.RandomItemDamage;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EnchantmentModifier {
	
	/**A temporary variable**/
	public int level;
	
	public static Random rand = new Random();
	
	public ArrayList<EnchantmentModifier> getIncompatibleModifiers()
	{
		return new ArrayList<EnchantmentModifier>();
	}
	
	public abstract String getName();
	
	public void onEntityBeforeDeath(EntityPlayer player, EntityLivingBase entity){}
	
	public void onEntityDeath(EntityPlayer player, EntityLivingBase entity, boolean recentlyHit){}
	
	public void onAttackEntity(EntityPlayer player, EntityLivingBase entity){}
	
	/**Currently unsupported**/
	public void onPlayerJump(EntityPlayer player, int jumped){}
	
	/**Currently unsupported**/
	public void onArmorDamage(EntityPlayer player, DamageSource source, float amount){}
	
	/**Currently unsupported**/
	public void onThrowStar(EntityPlayer player, EntityThrow throwingStar){}
	
	/**Currently unsupported**/
	public void onArrorHits(EntityPlayer player, Entity entity, EntityArrow arrow){}
	
	public int getSpanTime(EntityPlayer player, int time)
	{
		return time;
	}
	
	public double getRange(EntityPlayer player, double range)
	{
		return range;
	}
	
	public void onShotArrow(EntityPlayer player, EntityRandomArrow arrow) {}	
	
	/**If player clicks in air x, y  and z = -1 and block = null**/
	public boolean onRightClick(EntityPlayer player, int x, int y, int z, Block block, int side, float hitX, float hitY, float hitZ)
	{
		return false;
	}
	
	public float getMiningSpeed(EntityPlayer player, boolean canHarvest, Block block, float speed)
	{
		return speed;
	}
	
	public int onToolTakenDamage(EntityPlayer player, ItemStack stack, int amount)
	{
		return amount;
	}
	
	public float getDamageOnEntity(EntityPlayer player, EntityLivingBase entity, float damage)
	{
		return damage;
	}
	
	public float getMiningSpeed(float speed)
	{
		return speed;
	}
	
	public float getMiningSpeedLate(EntityPlayer player, ItemStack stack, boolean canHarvest, Block block, float speed)
	{
		return speed;
	}
	
	public float getDamage(float damage)
	{
		return damage;
	}
	
	public void onHarvestBlockPre(EntityPlayer player, World world, int x, int y, int z, int meta, Block block, ArrayList<ItemStack> drops){}
	
	public void onHarvestBlock(EntityPlayer player, World world, int x, int y, int z, int meta, Block block, ArrayList<ItemStack> drops){}
	
	public void onHarvestBlockPost(EntityPlayer player, World world, int x, int y, int z, int meta, Block block, ArrayList<ItemStack> drops){}
	
	@Override
	public boolean equals(Object object)
	{
		return object.getClass() == getClass();
	}
	
	public static boolean cantakeNormalEnergy(EntityPlayer player, int level)
	{
		return takeEnergy(player, getNormalDamage(level));
	}
	
	public static int getNormalDamage(int level)
	{
		switch(level)
		{
		case 1:
			return 50;
		case 2:
			return 25;
		case 3:
			return 10;
		default:
			return 0;
		}
	}
	
	public static boolean hasNormalEngouhEnergy(EntityPlayer player, int level)
	{
		return hasEnoughEnergy(player, getNormalDamage(level));
	}
	
	public static boolean hasEnoughEnergy(EntityPlayer player, int amount)
	{
		if(player.capabilities.isCreativeMode)
			return true;
		int power = 0;
		for (int i = 0; i < player.inventory.mainInventory.length; i++) {
			power += RandomItemBattery.getPower(player.inventory.mainInventory[i]);
		}
		return power >= amount;
	}
	
	public static boolean takeEnergy(EntityPlayer player, int amount)
	{
		if(player.capabilities.isCreativeMode)
			return true;
		ArrayList<ItemStack> batteries = new ArrayList<ItemStack>();
		int power = 0;
		for (int i = 0; i < player.inventory.mainInventory.length; i++) {
			float powerStack = RandomItemBattery.getPower(player.inventory.mainInventory[i]);
			if(powerStack > 0)
			{
				power += powerStack;
				batteries.add(player.inventory.mainInventory[i]);
			}
		}
		
		if(power >= amount)
		{
			int usedpower = 0;
			for (int i = 0; i < batteries.size(); i++) {
				if(usedpower < power)
				{
					float takenPower = RandomItemBattery.getDamage(batteries.get(i));
					if(takenPower > amount-usedpower)
						takenPower = amount-usedpower;
					RandomItemBattery.setDamage(batteries.get(i), RandomItemBattery.getDamage(batteries.get(i))-takenPower);
					usedpower += takenPower;
				}
			}
			return true;
		}else
			return false;
	}
	
	public static void dropXP(World world, int x, int y, int z, Block block, float xp)
	{
		int var2 = 1;
		int var4;
        if (xp == 0.0F)
        {
            var2 = 0;
        }
        else if (xp < 1.0F)
        {
        	var4 = MathHelper.floor_float((float)var2 * xp);
        	
            if (var4 < MathHelper.ceiling_float_int((float)var2 * xp) && (float)Math.random() < (float)var2 * xp - (float)var4)
            {
            	++var4;
            }

            var2 = var4;
        }
        block.dropXpOnBlockBreak(world, x, y, z, var2);
	}
}

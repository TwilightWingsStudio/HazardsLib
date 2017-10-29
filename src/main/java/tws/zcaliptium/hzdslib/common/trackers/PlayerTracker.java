/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.common.trackers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import tws.zcaliptium.hzdslib.common.DamageSourceHZDS;

public class PlayerTracker
{
	public EntityLivingBase owner;
	
	public int prevRadiation = 0;
	public int radiation = 0;
	
	public int updateTimer = 0;
	public int syncTimer = 0;
	
	public boolean isDisabled = false;
	
	public PlayerTracker(EntityLivingBase owner) {
		this.owner = owner;
	}
	
	public void loadFromNBT() {
		NBTTagCompound tags = owner.getEntityData();
		
		if (tags.hasKey(TrackerManager.HZDSLIB_RADIATION)) {
			radiation = tags.getInteger(TrackerManager.HZDSLIB_RADIATION);
		}
		
		clampSafeRange();
	}
	
	public void updateData()
	{
		prevRadiation = radiation;
		
		updateTimer = 0;
		
		if (owner == null || isDisabled) {
			TrackerManager.removeTracker(this);
			return;
		}
		
		if (owner.isDead) {
			return;
		}
		
		boolean isCreative = false;
		
		if (owner instanceof EntityPlayer) {
			if (((EntityPlayer)owner).capabilities.isCreativeMode) {
				isCreative = true;
				radiation = prevRadiation;
			}
		}
		
		float damage = 0.0F;
		
		boolean hasRadiation = radiation > 0;
		
		if (!isCreative && hasRadiation) {
			float health = owner.getHealth();
			
			if (radiation >= 20000) {
				damage = 1000.0F;
			} else if (radiation >= 15000) {
				damage = 0.2F;    // 1m
				owner.addPotionEffect(new PotionEffect(Potion.hunger.id, 200, 2));
				owner.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 3));
				owner.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 200, 3));
				owner.addPotionEffect(new PotionEffect(Potion.weakness.id, 200, 3));
				owner.addPotionEffect(new PotionEffect(Potion.blindness.id, 200, 0));
				owner.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
			} else if (radiation >= 10000) {
				damage = 0.15F;   // 1.5m
				owner.addPotionEffect(new PotionEffect(Potion.hunger.id, 200, 1));
				owner.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 2));
				owner.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 200, 2));
			} else if (radiation >= 7500) {
				damage = 0.1F;   // 2m
				owner.addPotionEffect(new PotionEffect(Potion.hunger.id, 200, 1));
				owner.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 1));
				owner.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 200, 1));
			} else if (radiation >= 5000) {
				damage = 0.05F;   // 4m
				owner.addPotionEffect(new PotionEffect(Potion.hunger.id, 200, 0));
				owner.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 200, 0));
				owner.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 200, 0));
			} else if (radiation >= 1000) {
				damage = 0.02F;  // 8.25m
				owner.addPotionEffect(new PotionEffect(Potion.hunger.id, 200, 0));
			} else if (radiation >= 500) {
				damage = 0.01F;  // 16.5m
			} else if (radiation >= 300) {
				damage = 0.005F; // 33m
			}
			
			//damage = 0.0F;
			
			//System.out.println("Damage: " + damage);
			
			boolean lethalDose = false;
			
			if (radiation >= 14000) {
				lethalDose = true;
			}
			
			if (damage > 0.0F) {
				if (health > damage) {
					owner.setHealth(health - damage);
				} else {
					if (lethalDose) {
						owner.attackEntityFrom(DamageSourceHZDS.radiationDD, 1000.0F);
					} else {
						owner.attackEntityFrom(DamageSourceHZDS.radiation, 1000.0F);
					}
				}
			}
		}
		
		TrackerManager.saveTracker(this);
	}
	
	public void resetData()
	{
		radiation = 0;
	}
	
	public void increaseRadiation(int incValue)
	{
		incValue = Math.abs(incValue);
		radiation += incValue;
	}
	
	public void decreaseRadiation(int decValue)
	{
		decValue = Math.abs(decValue);
		radiation -= decValue;
		
		if (radiation < 0) {
			radiation = 0;
		}
	}
	
	public void resetRadiation()
	{
		radiation = 0;
	}
	
	public void clampSafeRange()
	{
		radiation = MathHelper.clamp_int(radiation, 0, 15000);
	}
}

/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.common.trackers;

import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import tws.zcaliptium.hzdslib.common.HazardsLib;
import tws.zcaliptium.hzdslib.common.network.MsgSyncRadiation;
import tws.zcaliptium.hzdslib.common.network.PacketHandler;

public class TrackerManager
{
	public static HashMap<String, PlayerTracker> trackerList = new HashMap<String, PlayerTracker>();
	
	public static String HZDSLIB_RADIATION = "HZDSLIB_RADIATION";
	
	public static PlayerTracker lookupTracker(EntityLivingBase entity)
	{
		if (entity instanceof EntityPlayer) {
			if (trackerList.containsKey("" + entity.getCommandSenderName())) {
				return trackerList.get("" + entity.getCommandSenderName());
			} else {
				return null;
			}
		} else {
			if (trackerList.containsKey("" + entity.getEntityId())) {
				return trackerList.get("" + entity.getEntityId());
			} else {
				return null;
			}
		}
	}
	
	public static void saveAllWorldTrackers(World world) {
		HashMap<String,PlayerTracker> tempList = new HashMap<String,PlayerTracker>(trackerList);
		Iterator<PlayerTracker> iterator = tempList.values().iterator();
		
		while(iterator.hasNext()) {
			PlayerTracker tracker = iterator.next();
			
			if (tracker.owner.worldObj == world) {
				NBTTagCompound tags = tracker.owner.getEntityData();
				tags.setInteger(HZDSLIB_RADIATION, tracker.radiation);
			}
		}
	}

	public static void saveAndDeleteWorldTrackers(World world) {
		HashMap<String,PlayerTracker> tempList = new HashMap<String,PlayerTracker>(trackerList);
		Iterator<PlayerTracker> iterator = tempList.values().iterator();
		
		while(iterator.hasNext()) {
			PlayerTracker tracker = iterator.next();
			
			if (tracker.owner.worldObj == world) {
				NBTTagCompound tags = tracker.owner.getEntityData();
				tags.setInteger(HZDSLIB_RADIATION, tracker.radiation);
				if (tracker.owner instanceof EntityPlayer) {
					trackerList.remove(tracker.owner.getCommandSenderName());
				} else {
					trackerList.remove("" + tracker.owner.getEntityId());
				}
			}
		}		
	}

	
	public static void saveTracker(PlayerTracker tracker) {
		NBTTagCompound tags = tracker.owner.getEntityData();
		tags.setFloat(HZDSLIB_RADIATION, tracker.radiation);
	}
	
	public static void updateTracker(PlayerTracker tracker)
	{
		if (tracker == null) {
			return;
		}
		
		if (HazardsLib.proxy.isClient()) {
			if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
				if (Minecraft.getMinecraft().isGamePaused() && !HazardsLib.proxy.isOpenToLAN()) {
					return;
				}
			}
		}
		
		tracker.updateTimer += 1;
		
		if (tracker.updateTimer >= 10) {
			tracker.updateData();
			
			if (!HazardsLib.proxy.isClient() || HazardsLib.proxy.isOpenToLAN()) {
				syncMultiplayerTracker(tracker);
			}
		}
	}
	
	public static void removeTracker(PlayerTracker tracker)
	{
		if (trackerList.containsValue(tracker)) {
			tracker.isDisabled = true;
			if (tracker.owner instanceof EntityPlayer) {
				trackerList.remove(tracker.owner.getCommandSenderName());
			} else {
				trackerList.remove("" + tracker.owner.getEntityId());
			}
		}
	}
	
	public static void addToManager(PlayerTracker tracker)
	{
		if(tracker.owner instanceof EntityPlayer) {
			trackerList.put("" + tracker.owner.getCommandSenderName(), tracker);
		} else {
			trackerList.put("" + tracker.owner.getEntityId(), tracker);
		}
	}

	public static void syncMultiplayerTracker(PlayerTracker tracker) {
		if (!(tracker.owner instanceof EntityPlayer)) {
			return;
		}
		
		if (tracker.owner instanceof EntityPlayerMP) {
			
			PacketHandler.INSTANCE.sendTo(new MsgSyncRadiation(tracker.radiation), (EntityPlayerMP)tracker.owner);
		}
	}
	
	public static PlayerTracker lookupTrackerFromUsername(String username) {
		if(trackerList.containsKey(username)) {
			return trackerList.get(username);
		} else {
			return null;
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.common.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import tws.zcaliptium.hzdslib.common.HazardsLib;
import tws.zcaliptium.hzdslib.common.trackers.PlayerTracker;
import tws.zcaliptium.hzdslib.common.trackers.TrackerManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CommonEventHandler {
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		PlayerTracker tracker = TrackerManager.lookupTracker(event.original);
		
		if (tracker != null) {
			HazardsLib.modLog.info("Changing tracker owner for: '" + event.entity.getCommandSenderName() + "'...");
			tracker.owner = event.entityPlayer;
			
			if (event.wasDeath) {
				tracker.resetData();
				TrackerManager.saveTracker(tracker);
			}
			
			tracker.loadFromNBT();
		}
		
		if (event.wasDeath) {
			doDeath(event.entityPlayer);
			doDeath(event.original);
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityLivingBase) {
			boolean allowTracker = !(event.world.isRemote && HazardsLib.proxy.isClient() && Minecraft.getMinecraft().isIntegratedServerRunning());
			
			if (event.entity instanceof EntityPlayer && allowTracker) {
				PlayerTracker tracker = TrackerManager.lookupTracker((EntityLivingBase)event.entity);
				boolean hasOld = tracker != null;// && !tracker.isDisabled;
				
				if (!hasOld) {
					HazardsLib.modLog.info("Created new tracker instance for '" + event.entity.getCommandSenderName() + "'!");
					PlayerTracker newTracker = new PlayerTracker((EntityLivingBase)event.entity);
					TrackerManager.addToManager(newTracker);
					newTracker.loadFromNBT();

					/*
					if(!HazardsLib.proxy.isClient() || HazardsLib.proxy.isOpenToLAN()) {
						TrackerManager.syncMultiplayerTracker(newTracker);
					}
					*/
				} else {
					HazardsLib.modLog.info("Player '" + event.entity.getCommandSenderName() + "' has old tracker. Remounting!");
					tracker.owner = (EntityLivingBase)event.entity; // Change owner
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event){
		HazardsLib.modLog.info("Saving and unloading trackers for dimension " + event.world.provider.dimensionId);
		TrackerManager.saveAndDeleteWorldTrackers(event.world);
	}
	
	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event) {	
		//HazardsLib.modLog.info("Saving trackers for dimension " + event.world.provider.dimensionId);
		TrackerManager.saveAllWorldTrackers(event.world);
	}
	
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		if(!event.entityLiving.isDead && event.entityLiving instanceof EntityPlayer) {
			PlayerTracker tracker = TrackerManager.lookupTracker(event.entityLiving);
			
			if(tracker == null) {
				if(!HazardsLib.proxy.isClient() || Minecraft.getMinecraft().isIntegratedServerRunning()) {
					if(event.entityLiving instanceof EntityPlayer) {
						HazardsLib.modLog.info("Server lost track of player! Attempting to re-sync...");

						PlayerTracker newTracker = new PlayerTracker((EntityLivingBase)event.entity);
						TrackerManager.addToManager(newTracker);
						newTracker.loadFromNBT();
						TrackerManager.syncMultiplayerTracker(newTracker);
						tracker = newTracker;
					} else {
						return;
					}
				} else {
					return;
				}
			}
			
			TrackerManager.updateTracker(tracker);
		}
	}
	
	public static void doDeath(EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {}
	}
}

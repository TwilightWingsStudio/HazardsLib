/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import tws.zcaliptium.hzdslib.common.items.ItemsHZDS;
import tws.zcaliptium.hzdslib.common.trackers.PlayerTracker;
import tws.zcaliptium.hzdslib.common.trackers.TrackerManager;

@SideOnly(Side.CLIENT)
public class ClientEventHandler
{	
	public boolean isPlayerHasItemOnHotbar(InventoryPlayer inventory, Item item) {
        for (int i = 0; i < inventory.getHotbarSize(); ++i) {
            if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() == item) {
                return true;
            }
        }
        return false;
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderTick(TickEvent.RenderTickEvent event)
	{
		Minecraft mc = FMLClientHandler.instance().getClient();
		World world = mc.theWorld;
		
		if (event.phase != TickEvent.Phase.START) {
		    if ((Minecraft.getMinecraft().renderViewEntity instanceof EntityPlayer)) {
		    	EntityPlayer player = (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
		        if ((player != null) && (mc.inGameHasFocus) && (Minecraft.isGuiEnabled())) {
		        	
		        	if (isPlayerHasItemOnHotbar(player.inventory, ItemsHZDS.radiation_detector)) {

	        			PlayerTracker tracker = TrackerManager.lookupTrackerFromUsername(mc.thePlayer.getCommandSenderName());
	        			
	        			if (tracker != null) {
	        				renderRadiation(tracker);
	        			}
	        		}

		        }
		    }
		}
	}
	
	
	public void renderRadiation(PlayerTracker tracker) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		
		mc.fontRenderer.drawString("Radiation: " + tracker.radiation + " RADS", 50, 50, 16777215);
	}
}

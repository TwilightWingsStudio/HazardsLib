/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tws.zcaliptium.hzdslib.common.trackers.PlayerTracker;
import tws.zcaliptium.hzdslib.common.trackers.TrackerManager;

public class ItemRadDebug extends ItemHZDS
{
	boolean isGiver;
	
	public ItemRadDebug(String id, boolean giver)
	{
		super(id);
		
		this.isGiver = giver;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if (world.isRemote) {
			return itemStack;
		}

		if (player instanceof EntityPlayerMP)
		{
			PlayerTracker tracker = TrackerManager.lookupTracker(player);

			if (tracker != null) {
				if (isGiver) {
					tracker.radiation += 1000;
				} else {
					tracker.radiation = 0;
				}
			}
		}
		
		return itemStack;
	}
}

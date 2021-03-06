/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.common.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tws.zcaliptium.hzdslib.common.ModInfo;
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

			if (tracker != null)
			{
				if (isGiver) {
					tracker.increaseRadiation(1000);
				} else {
					tracker.resetRadiation();
				}
			}
		}
		
		return itemStack;
	}
	
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bo)
    {
    	list.add(StatCollector.translateToLocal(this.getUnlocalizedName(stack) + ".desc"));
    }
}

/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.common;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import tws.zcaliptium.hzdslib.common.items.ItemsHZDS;

public class CreativeTabHZDS extends CreativeTabs
{
	public CreativeTabHZDS(int par1, String par2) {
		super(par1, par2);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Item getTabIconItem()
	{
	    return Items.bed;
	}
}

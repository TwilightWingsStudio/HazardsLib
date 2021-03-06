/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.common.items;

import net.minecraft.creativetab.CreativeTabs;
import tws.zcaliptium.hzdslib.common.CreativeTabHZDS;

public class ItemsHZDS
{
	public static CreativeTabs tabHZDSItems = new CreativeTabHZDS(CreativeTabs.getNextID(), "hzdslib_items");

	public static ItemHZDS radiation_detector = null;
	
	public static void init()
	{
		new ItemAntirad("antirad");
		new ItemRadDebug("debug_radgiver", true);
		new ItemRadDebug("debug_radremover", false);

		// Debug Radioactivity
		new ItemRadioactive("debug_uranium238piece", 1);
		new ItemRadioactive("debug_uranium235piece", 8);
		
		radiation_detector = new ItemHZDS("radiation_detector");
	}
}

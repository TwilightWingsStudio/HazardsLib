/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import tws.zcaliptium.hzdslib.common.ModInfo;

public class ItemHZDS extends Item
{
	public ItemHZDS(String id) {
		GameRegistry.registerItem(this, id, ModInfo.MODID);
		setUnlocalizedName(id);
		setTextureName(ModInfo.MODID + ":" + id);
		setCreativeTab(ItemsHZDS.tabHZDSItems);
	}
}

/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.common;

import net.minecraft.init.Blocks;
import tws.zcaliptium.hzdslib.common.items.ItemsHZDS;
import tws.zcaliptium.hzdslib.common.network.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInfo.MODID, name = ModInfo.MODNAME, version = ModInfo.VERSION)
public class HazardsLib
{
	@Instance(ModInfo.MODID)
	public static HazardsLib instance;
	
	@SidedProxy(clientSide = "tws.zcaliptium.hzdslib.client.ClientProxy", serverSide = "tws.zcaliptium.hzdslib.common.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ItemsHZDS.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
    	PacketHandler.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
}

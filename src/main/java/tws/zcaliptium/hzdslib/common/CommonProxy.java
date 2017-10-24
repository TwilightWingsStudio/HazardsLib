/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.common;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;
import tws.zcaliptium.hzdslib.common.handlers.CommonEventHandler;

public class CommonProxy
{
	public void registerEventHandlers() {
		CommonEventHandler eventhandler = new CommonEventHandler();
		FMLCommonHandler.instance().bus().register(eventhandler);
		MinecraftForge.EVENT_BUS.register(eventhandler);
	}
	
	public boolean isClient() {
		return false;
	}
	
	public boolean isOpenToLAN() {
		return false;
	}
}

/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.common.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import tws.zcaliptium.hzdslib.common.trackers.PlayerTracker;
import tws.zcaliptium.hzdslib.common.trackers.TrackerManager;

public class MsgSyncRadiation implements IMessage, IMessageHandler<MsgSyncRadiation, IMessage>
{
	int radiation;
	
	public MsgSyncRadiation() {}
	
	public MsgSyncRadiation(int radiation) {
		this.radiation = radiation;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeInt(radiation);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		radiation = buffer.readInt();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(MsgSyncRadiation message, MessageContext ctx) {
		PlayerTracker tracker = TrackerManager.lookupTrackerFromUsername(Minecraft.getMinecraft().thePlayer.getCommandSenderName());
		
		if (tracker == null) {
			tracker = new PlayerTracker(Minecraft.getMinecraft().thePlayer);
			TrackerManager.addToManager(tracker);
			System.out.println("Tracker == null? Add new!");
		}
		
		
		if (tracker != null) {
			tracker.prevRadiation = tracker.radiation;
			tracker.radiation = message.radiation;
		}
		
		//System.out.println("RECV! " + message.radiation);

		return null;
	}
}

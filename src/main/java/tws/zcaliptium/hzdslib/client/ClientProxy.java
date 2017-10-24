/*******************************************************************************
 * Copyright (c) 2017 ZCaliptium.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 ******************************************************************************/
package tws.zcaliptium.hzdslib.client;

import tws.zcaliptium.hzdslib.common.CommonProxy;

public class ClientProxy extends CommonProxy
{
	@Override
	public boolean isClient() {
		return true;
	}
}

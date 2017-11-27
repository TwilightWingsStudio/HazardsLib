package tws.zcaliptium.hzdslib.common.items;

import tws.zcaliptium.hzdslib.api.item.IEffectiveItem;

public class ItemRadioactive extends ItemHZDS implements IEffectiveItem
{
	private int act;
	
	public ItemRadioactive(String id, int act)
	{
		super(id);
		
		this.act = act;
	}

	@Override
	public int getRadioactivity() {
		return act;
	}
}

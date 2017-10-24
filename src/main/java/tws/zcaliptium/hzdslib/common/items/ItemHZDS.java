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
		setCreativeTab(CreativeTabs.tabRedstone);
	}
}

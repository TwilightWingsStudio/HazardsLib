package tws.zcaliptium.hzdslib.common;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class DamageSourceHZDS extends DamageSource {
	
	public static DamageSourceHZDS radiation = (DamageSourceHZDS)(new DamageSourceHZDS("radiation").setDamageBypassesArmor());
	public static DamageSourceHZDS radiationDD = (DamageSourceHZDS)(new DamageSourceHZDS("radiation_dd").setDamageBypassesArmor());

	public DamageSourceHZDS(String p_i1566_1_) {
		super(p_i1566_1_);
	}

	@Override
	public IChatComponent func_151519_b(EntityLivingBase par1EntityLivingBase)
	{
		return new ChatComponentTranslation("death." + ModInfo.MODID + "." + this.damageType, par1EntityLivingBase.getCommandSenderName());
	}
	
	public void shit() {
		float a = 10.5F;
	}
}

package simelectricity.extension.buildcraft;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import simelectricity.essential.api.ISECoverPanelFactory;
import simelectricity.essential.api.coverpanel.ISECoverPanel;

import buildcraft.api.facades.FacadeAPI;
import buildcraft.api.facades.IFacade;
import buildcraft.api.facades.IFacadeItem;
import buildcraft.api.facades.IFacadePhasedState;
import buildcraft.api.facades.IFacadeState;

public class BCCoverFactory implements ISECoverPanelFactory{

	@Override
	public boolean acceptItemStack(ItemStack itemStack) {
		Item item = itemStack.getItem();
		
		return (item instanceof IFacadeItem);
	}
	
	@Override
	public ISECoverPanel fromItemStack(ItemStack itemStack) {
		try {
			IFacade facade = FacadeAPI.facadeItem.getFacade(itemStack);
			if (facade != null) {
				IFacadePhasedState facadePhasedState = facade.getPhasedStates()[0];
				IFacadeState facadeState = facadePhasedState.getState();
				
				boolean isHollow = facadePhasedState.isHollow();
				IBlockState blockState = facadeState.getBlockState();
				ItemStack reqStack = facadeState.getRequiredStack();
				
				return new BCFacadePanel(isHollow, blockState, itemStack);
			}
		}catch(Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public boolean acceptNBT(NBTTagCompound nbt) {
		return nbt.getString("coverPanelType").equals("BCFacade");
	}

	@Override
	public ISECoverPanel fromNBT(NBTTagCompound nbt) {
		return new BCFacadePanel(nbt);
	}
}

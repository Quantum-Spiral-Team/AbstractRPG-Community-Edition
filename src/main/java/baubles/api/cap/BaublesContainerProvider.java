package baubles.api.cap;

import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.BaublesContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class BaublesContainerProvider implements INBTSerializable<NBTTagCompound>, ICapabilityProvider {

	private final baubles.api.cap.BaublesContainer container;

	public BaublesContainerProvider(BaublesContainer container) {
		this.container = container;
	}

	@Override
	public boolean hasCapability (Capability<?> capability, EnumFacing facing) {
		return capability == baubles.api.cap.BaublesCapabilities.CAPABILITY_BAUBLES;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability (Capability<T> capability, EnumFacing facing) {
		if (capability == BaublesCapabilities.CAPABILITY_BAUBLES) return (T) this.container;
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT () {
		return this.container.serializeNBT();
	}

	@Override
	public void deserializeNBT (NBTTagCompound nbt) {
		this.container.deserializeNBT(nbt);
	}
}

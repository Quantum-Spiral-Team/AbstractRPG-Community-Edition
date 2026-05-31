package com.vivern.arpg.items;

import com.vivern.arpg.items.animation.ItemFlickersPack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class AnimationCapabilityProvider implements ICapabilityProvider {

    public ItemFlickersPack animations = new ItemFlickersPack();
    @CapabilityInject(ItemFlickersPack.class)
    public static Capability<ItemFlickersPack> CAPABILITY = null;
    public static ItemFlickersPack missing = new ItemFlickersPack();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return (T) (capability == CAPABILITY ? this.animations : null);
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(ItemFlickersPack.class, new IStorage<ItemFlickersPack>() {
            @Override
            public NBTBase writeNBT(Capability<ItemFlickersPack> capability, ItemFlickersPack instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<ItemFlickersPack> capability, ItemFlickersPack instance, EnumFacing side, NBTBase nbt) {
            }
        }, AnimationCapabilityProvider::getMissing);
    }

    public static ItemFlickersPack getMissing() {
        return missing;
    }

}

package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.hooks.ARPGHooks;
import com.vivern.arpg.items.IWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketCooldown;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient {

    @Shadow public abstract void sendPacket(Packet<?> packet);

    @Inject(
            method = "handleCooldown",
            at = @At("RETURN")
    )
    private void arpg$handleCooldown(SPacketCooldown packetIn, CallbackInfo ci) {
        if (packetIn.getTicks() == 0 && Minecraft.getMinecraft().player != null) {
            ItemStack stack = Minecraft.getMinecraft().player.inventory.getCurrentItem();
            if (stack.getItem() instanceof IWeapon && ((IWeapon) stack.getItem()).canChangeItem(stack, Minecraft.getMinecraft().player)) {
                Minecraft.getMinecraft().player.inventory.currentItem = ARPGHooks.moveSlot;
                sendPacket(new CPacketHeldItemChange(ARPGHooks.moveSlot));
            }
        }
    }

    @Inject(
            method = "sendPacket",
            at = @At("HEAD")
    )
    private void arpg$sendPacket(Packet<?> packetIn, CallbackInfo ci) {
        if (packetIn instanceof CPacketHeldItemChange) {
            CPacketHeldItemChange packet = (CPacketHeldItemChange) packetIn;
            ARPGHooks.moveSlot = packet.getSlotId();
        }
    }

}

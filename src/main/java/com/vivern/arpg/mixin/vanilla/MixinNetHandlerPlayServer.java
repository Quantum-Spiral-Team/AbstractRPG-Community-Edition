package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.items.IWeapon;
import com.vivern.arpg.potions.PotionEffects;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayServer.class)
public abstract class MixinNetHandlerPlayServer {

    @Shadow public EntityPlayerMP player;

    @Inject(
            method = "processClientStatus",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$processClientStatus(CPacketClientStatus packetIn, CallbackInfo ci) {
        if (packetIn.getStatus() == CPacketClientStatus.State.PERFORM_RESPAWN && !player.queuedEndExit && player.isPotionActive(PotionEffects.RESPAWN_PENALTY))
            ci.cancel();
    }

    @Inject(
            method = "processHeldItemChange",
            at = @At("HEAD"),
            cancellable = true
    )
    private void arpg$processHeldItemChange(CPacketHeldItemChange packetIn, CallbackInfo ci) {
        if (packetIn.getSlotId() >= 0 && packetIn.getSlotId() < InventoryPlayer.getHotbarSize()) {
            InventoryPlayer inventory = player.inventory;
            ItemStack stack = inventory.getCurrentItem();
            Item item = stack.getItem();
            if (item instanceof IWeapon && !((IWeapon) item).canChangeItem(stack, player)) {
                ci.cancel();
            }
        }
    }

}

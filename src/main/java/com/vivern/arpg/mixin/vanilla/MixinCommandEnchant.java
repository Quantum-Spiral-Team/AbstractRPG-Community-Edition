package com.vivern.arpg.mixin.vanilla;

import com.vivern.arpg.AbstractRPG;
import net.minecraft.command.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* B: Переписал прошлый хук, который полностью переписывал метод, на несколько Redirect/Inject
      Вроде работает так же (снимает огран на чарку командой, перезаписывает чары при повторке
      и игнорит тип предмета (что логично для команды. читы xD)) */
@Mixin(CommandEnchant.class)
public class MixinCommandEnchant {

    @Redirect(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/Enchantment;canApply(Lnet/minecraft/item/ItemStack;)Z"
            )
    )
    private boolean allowAllItems(Enchantment enchantment, ItemStack stack) {
        return true;
    }

    @Redirect(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/Enchantment;getMaxLevel()I"
            )
    )
    private int allowLevel255(Enchantment instance) {
        return 255;
    }

    @Redirect(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/enchantment/Enchantment;isCompatibleWith(Lnet/minecraft/enchantment/Enchantment;)Z"
            )
    )
    private boolean allowAllCombinations(Enchantment enchantment, Enchantment enchantmentIn) {
        return true;
    }

    @Inject(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;addEnchantment(Lnet/minecraft/enchantment/Enchantment;I)V"
            )
    )
    private void removeExistingEnchantment(
            net.minecraft.server.MinecraftServer server,
            ICommandSender sender,
            String[] args,
            CallbackInfo ci
    ) {
        try {
            EntityLivingBase entity = CommandBase.getEntity(server, sender, args[0], EntityLivingBase.class);
            ItemStack itemstack = entity.getHeldItemMainhand();

            Enchantment targetEnchant;
            try {
                targetEnchant = Enchantment.getEnchantmentByID(CommandBase.parseInt(args[1], 0));
            } catch (NumberInvalidException e) {
                targetEnchant = Enchantment.getEnchantmentByLocation(args[1]);
            }

            if (targetEnchant != null && itemstack.hasTagCompound()) {
                NBTTagList nbttaglist = itemstack.getEnchantmentTagList();

                for (int j = nbttaglist.tagCount() - 1; j >= 0; j--) {
                    int id = nbttaglist.getCompoundTagAt(j).getShort("id");
                    if (Enchantment.getEnchantmentByID(id) == targetEnchant) {
                        nbttaglist.removeTag(j);
                    }
                }
            }
        } catch (Exception e) {
            AbstractRPG.LOGGER.error(e);
        }
    }

}

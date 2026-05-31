package com.vivern.arpg.main;

import baubles.api.BaublesApi;
import com.vivern.arpg.items.IItemDamaged;
import com.vivern.arpg.items.armor.IItemAttacked;
import com.vivern.arpg.items.armor.IItemKilling;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = "arpg")
public class ArmorProtectionTracker {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        float damage = event.getAmount();
        boolean cancelOn0 = false;
        if (event.getEntityLiving() instanceof EntityPlayer) {
            IInventory inv = BaublesApi.getBaubles((EntityPlayer) event.getEntityLiving());

            for (int i = 0; i < 7; i++) {
                ItemStack bitem = inv.getStackInSlot(i);
                if (bitem.getItem() instanceof IItemDamaged) {
                    IItemDamaged iItemDamaged = (IItemDamaged) bitem.getItem();
                    damage = iItemDamaged.onDamagedWithItem(damage, bitem, (EntityPlayer) event.getEntityLiving(), source);
                    if (iItemDamaged.cancelOnNull()) {
                        cancelOn0 = true;
                    }
                }
            }
        }

        if (cancelOn0 && damage <= 0.0F) {
            event.setCanceled(true);
        }

        event.setAmount(damage);
    }

    @SubscribeEvent
    public static void onLivingKnockBack(LivingKnockBackEvent event) {
        if (event.getAttacker() instanceof EntityLivingBase) {
            event.setStrength(event.getStrength() + (float) ((EntityLivingBase) event.getAttacker()).getEntityAttribute(PropertiesRegistry.MELEE_KNOCKBACK).getAttributeValue());
        }
    }

    public static void onEntityLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            DamageSource source = event.getSource();
            EntityPlayer player = (EntityPlayer) source.getTrueSource();

            for (ItemStack stack : player.inventory.armorInventory) {
                if (stack.getItem() instanceof IItemKilling) {
                    ((IItemKilling) stack.getItem()).onKillWithItem(stack, player, event.getEntityLiving(), source);
                }
            }

            IInventory inv = BaublesApi.getBaubles(player);

            for (int i = 0; i < 7; i++) {
                ItemStack bitem = inv.getStackInSlot(i);
                if (bitem.getItem() instanceof IItemKilling) {
                    ((IItemKilling) bitem.getItem()).onKillWithItem(bitem, player, event.getEntityLiving(), source);
                }
            }

            ItemStack stackm = player.getHeldItemMainhand();
            ItemStack stacko = player.getHeldItemOffhand();
            if (stackm.getItem() instanceof IItemKilling) {
                ((IItemKilling) stackm.getItem()).onKillWithItem(stackm, player, event.getEntityLiving(), source);
            }

            if (stacko.getItem() instanceof IItemKilling) {
                ((IItemKilling) stacko.getItem()).onKillWithItem(stacko, player, event.getEntityLiving(), source);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            DamageSource source = event.getSource();
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            float damage = event.getAmount();

            for (ItemStack stack : player.inventory.armorInventory) {
                if (stack.getItem() instanceof IItemAttacked) {
                    IItemAttacked itematt = (IItemAttacked) stack.getItem();
                    damage = itematt.onAttackedWithItem(damage, stack, player, source);
                    if (damage <= 0.0F && itematt.cancelOnNull()) {
                        event.setCanceled(true);
                    }
                }
            }

            IInventory inv = BaublesApi.getBaubles(player);

            for (int i = 0; i < 7; i++) {
                ItemStack bitem = inv.getStackInSlot(i);
                if (bitem.getItem() instanceof IItemAttacked) {
                    IItemAttacked itematt = (IItemAttacked) bitem.getItem();
                    damage = itematt.onAttackedWithItem(damage, bitem, player, source);
                    if (damage <= 0.0F && itematt.cancelOnNull()) {
                        event.setCanceled(true);
                    }
                }
            }

            ItemStack stackm = player.getHeldItemMainhand();
            ItemStack stacko = player.getHeldItemOffhand();
            if (stackm.getItem() instanceof IItemAttacked) {
                IItemAttacked itematt = (IItemAttacked) stackm.getItem();
                damage = itematt.onAttackedWithItem(damage, stackm, player, source);
                if (damage <= 0.0F && itematt.cancelOnNull()) {
                    event.setCanceled(true);
                }
            }

            if (stacko.getItem() instanceof IItemAttacked) {
                IItemAttacked itematt = (IItemAttacked) stacko.getItem();
                damage = itematt.onAttackedWithItem(damage, stacko, player, source);
                if (damage <= 0.0F && itematt.cancelOnNull()) {
                    event.setCanceled(true);
                }
            }
        }
    }

}

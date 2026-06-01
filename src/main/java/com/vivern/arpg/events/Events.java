package com.vivern.arpg.events;

import baubles.api.BaublesApi;
import com.vivern.arpg.Tags;
import com.vivern.arpg.items.armor.IItemDamaging;
import com.vivern.arpg.items.armor.IItemHurted;
import com.vivern.arpg.main.ItemsRegister;
import com.vivern.arpg.main.Mana;
import com.vivern.arpg.main.PropertiesRegistry;
import com.vivern.arpg.main.ServerKeyTracker;
import com.vivern.arpg.potions.AdvancedPotion;
import com.vivern.arpg.renders.KillScore;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

@EventBusSubscriber(modid = Tags.MOD_ID)
public class Events {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void blockDrop(HarvestDropsEvent event) {
        Block block = event.getState().getBlock();
        if (block == Blocks.MOB_SPAWNER && RANDOM.nextFloat() < 0.75F) {
            event.getDrops().add(new ItemStack(ItemsRegister.SPAWNER_PIECE));
            if (RANDOM.nextFloat() < 0.2F) {
                event.getDrops().add(new ItemStack(ItemsRegister.SPAWNER_PIECE));
            }
        }
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource source = event.getSource();

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (source == DamageSource.FALL) {
                IInventory inv = BaublesApi.getBaubles(player);
                ItemStack stack = inv.getStackInSlot(6);
                if (stack.getItem() == ItemsRegister.ETHER_SIGN && stack.getItemDamage() < 2000) {
                    event.setCanceled(true);
                    stack.damageItem(1, player);
                    return;
                }
            }
        }

        KillScore.onLivingHurt(event);

        float damage = event.getAmount();
        boolean canceled = false;

        if (source != DamageSource.DROWN && source != DamageSource.ON_FIRE && source != DamageSource.MAGIC && source != DamageSource.OUT_OF_WORLD && source != DamageSource.STARVE && source != DamageSource.WITHER && source != DamageSource.FALL && damage > 1.0F) {
            damage = Math.max(damage - (float) Mana.getArmorProtection(entity), 1.0F);
        }

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            for (ItemStack stack : player.inventory.armorInventory) {
                if (stack.getItem() instanceof IItemHurted) {
                    IItemHurted iItemHurted = (IItemHurted) stack.getItem();
                    damage = iItemHurted.onHurtWithItem(damage, stack, player, source);
                    if (iItemHurted.cancelOnNull()) {
                        canceled = true;
                    }
                }
            }

            IInventory inv = BaublesApi.getBaubles(player);
            for (int i = 0; i < 7; i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if (stack.getItem() instanceof IItemHurted) {
                    IItemHurted iItemHurted = (IItemHurted) stack.getItem();
                    damage = iItemHurted.onHurtWithItem(damage, stack, player, source);
                    if (iItemHurted.cancelOnNull()) {
                        canceled = true;
                    }
                }
            }

            ItemStack mainStack = player.getHeldItemMainhand();
            if (mainStack.getItem() instanceof IItemHurted) {
                IItemHurted iItemHurted = (IItemHurted) mainStack.getItem();
                damage = iItemHurted.onHurtWithItem(damage, mainStack, player, source);
                if (iItemHurted.cancelOnNull()) {
                    canceled = true;
                }
            }

            ItemStack offStack = player.getHeldItemOffhand();
            if (offStack.getItem() instanceof IItemHurted) {
                IItemHurted iItemHurted = (IItemHurted) offStack.getItem();
                damage = iItemHurted.onHurtWithItem(damage, offStack, player, source);
                if (iItemHurted.cancelOnNull()) {
                    canceled = true;
                }
            }
        }

        for (PotionEffect effect : entity.getActivePotionEffects()) {
            if (effect.getPotion() instanceof AdvancedPotion) {
                damage = ((AdvancedPotion) effect.getPotion()).onHurtThis(entity, source, damage, effect);
            }
        }

        if (source.getTrueSource() instanceof EntityPlayer) {
            EntityPlayer damager = (EntityPlayer) source.getTrueSource();

            for (ItemStack armorStack : damager.inventory.armorInventory) {
                if (armorStack.getItem() instanceof IItemDamaging) {
                    damage = ((IItemDamaging) armorStack.getItem()).onCauseDamageWithItem(damage, armorStack, damager, entity, source);
                }
            }

            IInventory inv = BaublesApi.getBaubles(damager);
            for (int ix = 0; ix < 7; ix++) {
                ItemStack stack = inv.getStackInSlot(ix);
                if (stack.getItem() instanceof IItemDamaging) {
                    damage = ((IItemDamaging) stack.getItem()).onCauseDamageWithItem(damage, stack, damager, entity, source);
                }
            }

            ItemStack mainStack = damager.getHeldItemMainhand();
            if (mainStack.getItem() instanceof IItemDamaging) {
                damage = ((IItemDamaging) mainStack.getItem()).onCauseDamageWithItem(damage, mainStack, damager, entity, source);
            }

            ItemStack offStack = damager.getHeldItemOffhand();
            if (offStack.getItem() instanceof IItemDamaging) {
                damage = ((IItemDamaging) offStack.getItem()).onCauseDamageWithItem(damage, offStack, damager, entity, source);
            }
        }

        if (source.getTrueSource() instanceof EntityLivingBase && !source.damageType.equals("thorns")) {
            EntityLivingBase damager = (EntityLivingBase) source.getTrueSource();
            double vampirism = damager.getEntityAttribute(PropertiesRegistry.VAMPIRISM).getAttributeValue();
            if (vampirism > 0.0) {
                damager.heal((float) (damage * vampirism));
            } else if (vampirism < 0.0) {
                damager.attackEntityFrom(DamageSource.causeThornsDamage(entity), (float) (-(damage * vampirism)));
            }
        }

        if (damage <= 0.0F) {
            event.setCanceled(canceled);
        }

        event.setAmount(damage);
    }

    @SubscribeEvent
    public static void onPlayerConstructing(EntityEvent.EntityConstructing event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityLivingBase) {
            PropertiesRegistry.onEntityConstructing((EntityLivingBase) entity);
            if (entity instanceof EntityPlayer) {
                PropertiesRegistry.onPlayerConstructing((EntityPlayer) entity);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        ServerKeyTracker.clearPlayer(event.player);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ServerKeyTracker.resetTick();
        }
    }

}

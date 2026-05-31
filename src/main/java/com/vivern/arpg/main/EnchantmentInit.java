package com.vivern.arpg.main;

import com.vivern.arpg.enchants.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = "arpg")
public class EnchantmentInit {

    public static final List<Enchantment> ENCHANTMENTSLIST = new ArrayList<>();
    public static final Enchantment MIGHT = new MightEnch();
    public static final Enchantment ACCURACY = new AccuracyEnch();
    public static final Enchantment SPECIAL = new SpecialEnch();
    public static final Enchantment IMPULSE = new ImpulseEnch();
    public static final Enchantment REUSE = new ReuseEnch();
    public static final Enchantment RAPIDITY = new RapidityEnch();
    public static final Enchantment SORCERY = new SorceryEnch();
    public static final Enchantment RELOADING = new ReloadingEnch();
    public static final Enchantment RANGE = new RangeEnch();
    public static final Enchantment WITCHERY = new WitcheryEnch();
    public static EnumEnchantmentType enchantmentTypeWeapon;

    @SubscribeEvent
    public static void RegisterEnch(Register<Enchantment> event) {
        event.getRegistry().registerAll(ENCHANTMENTSLIST.toArray(new Enchantment[0]));
    }

}

package com.vivern.arpg.main;

import com.vivern.arpg.items.ItemBullet;
import com.vivern.arpg.items.ItemMagicScroll;
import com.vivern.arpg.items.ItemRocket;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static com.vivern.arpg.main.Items.REGISTRY;

@EventBusSubscriber
public class Registry {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) throws IllegalAccessException {
        ItemMagicScroll.createScrolls();
        REGISTRY.addAll(ItemMagicScroll.magicScrolls.values());

        event.getRegistry().registerAll(REGISTRY.toArray(new Item[0]));

        BlocksRegister.setupAfterItems();
        ItemBullet.init();
        ItemRocket.init();
    }

}

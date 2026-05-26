package com.vivern.arpg.main;

import com.vivern.arpg.Tags;
import com.vivern.arpg.items.ItemBullet;
import com.vivern.arpg.items.ItemMagicScroll;
import com.vivern.arpg.items.ItemRocket;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Tags.MOD_ID)
public class ContentRegister {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) throws IllegalAccessException {
        Field[] fields = ItemsRegister.class.getFields();
        List<Item> items = new ArrayList<>();

        for (Field field : fields) {
            if (field.getType() == Item.class) {
                Item item = (Item) field.get(new ItemsRegister());
                items.add(item);
            }
        }

        ItemMagicScroll.createScrolls();
        items.addAll(ItemMagicScroll.magicScrolls.values());

        event.getRegistry().registerAll(items.toArray(new Item[0]));

        BlocksRegister.setupAfterItems();
        ItemBullet.init();
        ItemRocket.init();
    }

}

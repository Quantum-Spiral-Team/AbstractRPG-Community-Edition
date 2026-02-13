package com.Vivern.Arpg.elements;

import com.Vivern.Arpg.arpgfix.ItemKeyPressInfo;
import net.minecraft.item.Item;

//public abstract class ItemKeyPressInfo_Item extends Item implements ItemKeyPressInfo {
//public abstract class Item_SideSync extends Item implements ItemKeyPressInfo, ItemDataSync_Client {
public abstract class Item_SideSync extends Item implements ItemKeyPressInfo{

//    private final Map<String, Object> dataStorage = new HashMap<>();
//
//    public Map<String, Object> getMap() {
//        return this.dataStorage;
//    }
//
//    public <T> T getData(String dataName, Class<T> type, T defaultValue) {
//        Object obj = this.dataStorage.get(dataName);
//        return type.isInstance(obj) ? type.cast(obj) : defaultValue;
//    }
//
//    public <T> void setData(String dataName, Class<T> type, @Nonnull Object value) {
//        Object oldValue = this.getData(dataName, type);
//
//        if (!type.isInstance(oldValue)) { // also - null-check
//            this.dataStorage.put(dataName, value);
//            return;
//
//        } else {
//            if (!oldValue.equals(value)) {
//                // sync on client
//                .
//            }
//            this.dataStorage.put(dataName, value);
//        }
//    }

}
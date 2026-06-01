package com.vivern.arpg.items.armor;

import com.google.common.collect.Multimap;
import com.vivern.arpg.items.models.BoneArmorModel;
import com.vivern.arpg.items.models.LichArmorModel;
import com.vivern.arpg.main.PropertiesRegistry;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LichHelm extends AbstractArmor {

    public static String armortexture = "arpg:textures/lich_armor_model_tex.png";

    public LichHelm() {
        super(EntityEquipmentSlot.HEAD, "lich_armor_helmet", 3000, 6);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == this.armorType) {
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 2.0, 0));
            multimap.put(PropertiesRegistry.MAGIC_POWER_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor magic", 0.2, 0));
            multimap.put(PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 20.0, 0));
            multimap.put(PropertiesRegistry.MANA_SPEED_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.15, 0));
            multimap.put(PropertiesRegistry.ARMOR_PROTECTION.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 3.0, 0));
        }

        return multimap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
        if (itemStack != ItemStack.EMPTY) {
            LichArmorModel armorModel = (LichArmorModel) Armors.ArmorModels.LICH.getModel();
            armorModel.setModelAttributes(model);
            armorModel.helm.showModel = true;
            armorModel.chest.showModel = false;
            armorModel.rightarm.showModel = false;
            armorModel.leftarm.showModel = false;
            armorModel.rightleg.showModel = false;
            armorModel.leftleg.showModel = false;
            armorModel.rightboot.showModel = false;
            armorModel.leftboot.showModel = false;
            return armorModel;
        } else {
            return null;
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return armortexture;
    }

    public static class LichBoots extends AbstractArmor {

        public LichBoots() {
            super(EntityEquipmentSlot.FEET, "lich_armor_boots", 3000, 6);
        }

        @Override
        public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
            Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
            if (equipmentSlot == this.armorType) {
                multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 2.0, 0));
                multimap.put(PropertiesRegistry.MAGIC_POWER_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor magic", 0.1, 0));
                multimap.put(PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 15.0, 0));
                multimap.put(PropertiesRegistry.MANA_SPEED_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.1, 0));
                multimap.put(PropertiesRegistry.ARMOR_PROTECTION.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.0, 0));
                multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.04, 0));
            }

            return multimap;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
            if (itemStack != ItemStack.EMPTY) {
                LichArmorModel armorModel = (LichArmorModel) Armors.ArmorModels.LICH.getModel();
                armorModel.setModelAttributes(model);
                armorModel.helm.showModel = false;
                armorModel.chest.showModel = false;
                armorModel.rightarm.showModel = false;
                armorModel.leftarm.showModel = false;
                armorModel.rightleg.showModel = false;
                armorModel.leftleg.showModel = false;
                armorModel.rightboot.showModel = true;
                armorModel.leftboot.showModel = true;
                return armorModel;
            } else {
                return null;
            }
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
            return LichHelm.armortexture;
        }

    }

    public static class LichChestplate extends AbstractArmor {

//        public static BoneArmorModel model = new BoneArmorModel();

        public LichChestplate() {
            super(EntityEquipmentSlot.CHEST, "lich_armor_chestplate", 3500, 6);
        }

        @Override
        public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
            Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
            if (equipmentSlot == this.armorType) {
                multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 4.0, 0));
                multimap.put(PropertiesRegistry.MAGIC_POWER_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor magic", 0.2, 0));
                multimap.put(PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 30.0, 0));
                multimap.put(PropertiesRegistry.MANA_SPEED_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.2, 0));
                multimap.put(PropertiesRegistry.ARMOR_PROTECTION.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 3.5, 0));
            }

            return multimap;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
            if (itemStack != ItemStack.EMPTY) {
                LichArmorModel armorModel = (LichArmorModel) Armors.ArmorModels.LICH.getModel();
                armorModel.setModelAttributes(model);
                armorModel.helm.showModel = false;
                armorModel.chest.showModel = true;
                armorModel.rightarm.showModel = true;
                armorModel.leftarm.showModel = true;
                armorModel.rightleg.showModel = false;
                armorModel.leftleg.showModel = false;
                armorModel.rightboot.showModel = false;
                armorModel.leftboot.showModel = false;
                return armorModel;
            } else {
                return null;
            }
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
            return LichHelm.armortexture;
        }

    }

    public static class LichLeggins extends AbstractArmor {

//        public static BoneArmorModel model = new BoneArmorModel();

        public LichLeggins() {
            super(EntityEquipmentSlot.LEGS, "lich_armor_leggins", 3200, 6);
        }

        @Override
        public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
            Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
            if (equipmentSlot == this.armorType) {
                multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", 2.0, 0));
                multimap.put(PropertiesRegistry.MAGIC_POWER_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor magic", 0.15, 0));
                multimap.put(PropertiesRegistry.MANA_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor mana", 20.0, 0));
                multimap.put(PropertiesRegistry.MANA_SPEED_MAX.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor manaregen", 0.1, 0));
                multimap.put(PropertiesRegistry.ARMOR_PROTECTION.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 2.0, 0));
            }

            return multimap;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
            if (itemStack != ItemStack.EMPTY) {
                LichArmorModel armorModel = (LichArmorModel) Armors.ArmorModels.LICH.getModel();
                armorModel.setModelAttributes(model);
                armorModel.helm.showModel = false;
                armorModel.chest.showModel = false;
                armorModel.rightarm.showModel = false;
                armorModel.leftarm.showModel = false;
                armorModel.rightleg.showModel = true;
                armorModel.leftleg.showModel = true;
                armorModel.rightboot.showModel = false;
                armorModel.leftboot.showModel = false;
                return armorModel;
            } else {
                return null;
            }
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
            return LichHelm.armortexture;
        }

    }

}

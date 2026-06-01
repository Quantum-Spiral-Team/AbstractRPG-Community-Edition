package com.vivern.arpg.items.armor;

import com.google.common.collect.Multimap;
import com.vivern.arpg.items.models.BoneArmorModel;
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

public class BoneHelmet extends AbstractArmor {

    public static final String ARMOR_TEXTURE_PATH = "arpg:textures/bone_armor_model_tex.png";

    public BoneHelmet() {
        super(EntityEquipmentSlot.HEAD, "bone_armor_helmet", 5000, 6);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == this.armorType) {
            multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0));
            multimap.put(PropertiesRegistry.JUMP_HEIGHT.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor jumping", 0.04, 0));
            multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.03, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0));
            multimap.put(PropertiesRegistry.ARMOR_PROTECTION.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 0.5, 0));
        }

        return multimap;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
        if (itemStack != ItemStack.EMPTY) {
            BoneArmorModel armorModel = (BoneArmorModel) Armors.ArmorModels.BONE_HELMET.getModel();
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
        return ARMOR_TEXTURE_PATH;
    }

    public static class BoneBoots extends AbstractArmor {

        public BoneBoots() {
            super(EntityEquipmentSlot.FEET, "bone_armor_boots", 5000, 6);
        }

        @Override
        public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
            Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
            if (equipmentSlot == this.armorType) {
                multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0));
                multimap.put(PropertiesRegistry.JUMP_HEIGHT.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor jumping", 0.04, 0));
                multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.03, 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0));
                multimap.put(PropertiesRegistry.ARMOR_PROTECTION.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 0.5, 0));
            }

            return multimap;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
            if (itemStack != ItemStack.EMPTY) {
                BoneArmorModel armorModel = (BoneArmorModel) Armors.ArmorModels.BONE_HELMET.getModel();
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
            return BoneHelmet.ARMOR_TEXTURE_PATH;
        }

    }

    public static class BoneChestplate extends AbstractArmor {

        public BoneChestplate() {
            super(EntityEquipmentSlot.CHEST, "bone_armor_chestplate", 6000, 6);
        }

        @Override
        public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
            Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
            if (equipmentSlot == this.armorType) {
                multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0));
                multimap.put(PropertiesRegistry.JUMP_HEIGHT.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor jumping", 0.04, 0));
                multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.03, 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0));
                multimap.put(PropertiesRegistry.ARMOR_PROTECTION.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 0.5, 0));
            }

            return multimap;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
            if (itemStack != ItemStack.EMPTY) {
                BoneArmorModel armorModel = (BoneArmorModel) Armors.ArmorModels.BONE_HELMET.getModel();
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
            return BoneHelmet.ARMOR_TEXTURE_PATH;
        }

    }

    public static class BoneLeggins extends AbstractArmor {

        public BoneLeggins() {
            super(EntityEquipmentSlot.LEGS, "bone_armor_leggins", 5500, 6);
        }

        @Override
        public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
            Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
            if (equipmentSlot == this.armorType) {
                multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor modifier", this.damageReduceAmount, 0));
                multimap.put(PropertiesRegistry.JUMP_HEIGHT.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor jumping", 0.04, 0));
                multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor speed", 0.03, 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor attackspeed", 0.1, 0));
                multimap.put(PropertiesRegistry.ARMOR_PROTECTION.getName(), new AttributeModifier(ARMOR_MODIFIERSG[equipmentSlot.getIndex()], "Armor protection", 0.5, 0));
            }

            return multimap;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped model) {
            if (itemStack != ItemStack.EMPTY) {
                BoneArmorModel armorModel = (BoneArmorModel) Armors.ArmorModels.BONE_HELMET.getModel();
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
            return BoneHelmet.ARMOR_TEXTURE_PATH;
        }

    }

}

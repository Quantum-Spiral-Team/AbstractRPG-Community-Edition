package com.vivern.arpg.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;

import java.util.ArrayList;
import java.util.List;

public class SieveRecipe {

    public Ingridient input;
    public NonNullList<Ingridient> output;
    public float[] chances;
    public int timeToCraft;
    public SoundEvent sound;

    public SieveRecipe(Ingridient input, int timeToCraft, SoundEvent sound, float[] chances, Ingridient... output) {
        this.input = input;
        this.output = NonNullList.from(Ingridient.EMPTY, output);
        this.chances = chances;
        this.timeToCraft = timeToCraft;
        this.sound = sound;
        if (chances.length != output.length) {
            throw new RuntimeException("SieveRecipe chances and output may be equal length!!!");
        }
    }

    public List<ItemStack> exportInputsAsList() {
        List<ItemStack> list = new ArrayList<>();
        list.add(this.input.createStack());
        return list;
    }

    public List<ItemStack> exportOutputAsList() {
        List<ItemStack> list = new ArrayList<>();

        for (Ingridient ingridient : this.output) {
            if (ingridient != Ingridient.EMPTY) {
                list.add(ingridient.createStack());
            }
        }

        return list;
    }

}

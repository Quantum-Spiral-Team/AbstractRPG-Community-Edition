package com.vivern.arpg.recipes;

import com.vivern.arpg.main.Catalyst;
import com.vivern.arpg.main.ShardType;
import com.vivern.arpg.main.Spell;
import com.vivern.arpg.tileentity.TileSpellForge;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SpellForgeRecipe {

    public final Ingridient craftresult;
    public boolean useEnergy = false;
    public NonNullList<Ingridient> recipe;
    public int[] arrayelementsCost;
    public Spell[] spells;
    public float hitsNeed;
    public Catalyst catalyst;
    public int id;
    @Nullable
    public ShardType fallbackShardType;

    public SpellForgeRecipe(float hitsNeed, Ingridient[] slots, Ingridient craftresult, int[] array12elementsCost) {
        this.recipe = NonNullList.from(Ingridient.EMPTY, slots);
        this.craftresult = craftresult;
        this.hitsNeed = hitsNeed;
        this.arrayelementsCost = array12elementsCost;

        for (int j : array12elementsCost) {
            if (j > 0) {
                this.useEnergy = true;
                break;
            }
        }
    }

    public SpellForgeRecipe setSpells(Spell... spells) {
        this.spells = spells;
        return this;
    }

    public SpellForgeRecipe setCatalyst(Catalyst catalyst) {
        this.catalyst = catalyst;
        return this;
    }

    public SpellForgeRecipe setCatalyst(Item... item) {
        return this.setCatalyst(new Catalyst.CatalystItems(item));
    }

    public SpellForgeRecipe setCatalyst(Block... blocks) {
        Item[] items = new Item[blocks.length];
        Arrays.parallelSetAll(items, i -> Item.getItemFromBlock(blocks[i]));
        return this.setCatalyst(items);
    }

    public boolean tryCraft(TileSpellForge sforge, @Nullable Entity entityCrafts, boolean shouldComplete) {
        if (this.useEnergy) {
            for (int i = 0; i < 12; i++) {
                if (sforge.elementsCollected[i] < this.arrayelementsCost[i]) {
                    return false;
                }
            }
        }

        NonNullList<ItemStack> stacks = sforge.stacks;

        for (int ix = 0; ix < stacks.size(); ix++) {
            ItemStack have = stacks.get(ix);
            if (!this.recipe.get(ix).isStackAllowed(have)) {
                return false;
            }
        }

        if (shouldComplete) {
            for (int ixx = 0; ixx < stacks.size(); ixx++) {
                ItemStack have = stacks.get(ixx);
                Ingridient need = this.recipe.get(ixx);
                int size = have.getCount() - need.getCount();
                ItemStack result = size > 0 ? new ItemStack(have.getItem(), size, have.getMetadata(), have.getTagCompound()) : ItemStack.EMPTY;
                sforge.stacks.set(ixx, result);
            }

            if (this.useEnergy) {
                for (int ixx = 0; ixx < 12; ixx++) {
                    sforge.addElementEnergy(ixx, -this.arrayelementsCost[ixx]);
                }
            }
        }

        return true;
    }

    @Nullable
    public ShardType getRandomWeightedShardType(Random rand) {
        if (this.useEnergy) {
            float sum = 0.0F;

            for (int j : this.arrayelementsCost) {
                sum += j;
            }

            float r = rand.nextFloat() * sum;
            float all = 0.0F;

            for (int i = 0; i < this.arrayelementsCost.length; i++) {
                float weight = this.arrayelementsCost[i];
                all += weight;
                if (r < all) {
                    return ShardType.byId(i + 1);
                }
            }
        }

        return this.fallbackShardType;
    }

    public List<ItemStack> exportInputsAsList() {
        List<ItemStack> list = new ArrayList<>();

        for (Ingridient ingridient : this.recipe) {
            if (ingridient != Ingridient.EMPTY) {
                list.add(ingridient.createStack());
            }
        }

        if (this.catalyst != null) {
            this.catalyst.addAllGoodStacks(list);
        }

        return list;
    }

}

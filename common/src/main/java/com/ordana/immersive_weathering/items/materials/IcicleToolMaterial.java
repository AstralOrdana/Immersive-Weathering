package com.ordana.immersive_weathering.items.materials;

import com.ordana.immersive_weathering.reg.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class IcicleToolMaterial implements Tier {

    public static final IcicleToolMaterial INSTANCE = new IcicleToolMaterial();

    @Override
    public float getSpeed() {
        return 8f;
    }

    @Override
    public int getUses() {
        return 131;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return BlockTags.INCORRECT_FOR_STONE_TOOL;
    }

    @Override
    public float getAttackDamageBonus() {
        return 1f;
    }

    @Override
    public int getEnchantmentValue() {
        return 10;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.ICICLE.get());
    }
}

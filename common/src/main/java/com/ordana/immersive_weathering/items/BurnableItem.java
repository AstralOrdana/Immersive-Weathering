package com.ordana.immersive_weathering.items;

import com.ordana.immersive_weathering.platform.RegistryPlatform;
import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class BurnableItem extends Item {
    private final int burnTime;

    public BurnableItem(Properties properties, int burnTime) {
        super(properties);
        this.burnTime = burnTime;
        RegistryPlatform.registerItemBurnTime(this, burnTime);
    }

    @PlatformOnly(PlatformOnly.FORGE)
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return this.burnTime;
    }

}

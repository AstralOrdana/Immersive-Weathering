package com.ordana.immersive_weathering.mixins.accessors;

import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RandomBlockMatchTest.class)
public interface RandomBlockMatchTestAccessor {

    @Accessor
    float getProbability();
}

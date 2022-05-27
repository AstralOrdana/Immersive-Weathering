package com.ordana.immersive_weathering.registry.entities;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterHelper {

    public static <T extends Entity> EntityType<T> registerEntity(String registryName, EntityType<T> entityType) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(ImmersiveWeathering.MOD_ID, registryName), entityType);
    }
}

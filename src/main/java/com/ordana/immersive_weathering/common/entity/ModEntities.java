package com.ordana.immersive_weathering.common.entity;

import com.ordana.immersive_weathering.ImmersiveWeathering;
import com.ordana.immersive_weathering.common.blocks.ModBlocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ImmersiveWeathering.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ImmersiveWeathering.MOD_ID);

    public static <T extends Entity> RegistryObject<EntityType<T>> regEntity(String name, EntityType.Builder<T> builder) {
        return ENTITIES.register(name, () -> builder.build(name));
    }

    public static final RegistryObject<BlockEntityType<IcicleBlockEntity>> ICICLE_TILE = BLOCK_ENTITIES.register("icicle",
            () -> BlockEntityType.Builder.of(IcicleBlockEntity::new, ModBlocks.ICICLE.get()).build(null));

    public static final RegistryObject<EntityType<FallingIcicleEntity>> FALLING_ICICLE = regEntity("falling_icicle",
            EntityType.Builder.<FallingIcicleEntity>of(FallingIcicleEntity::new, MobCategory.MISC)
                    .sized(0.98F, 0.98F)
                    .clientTrackingRange(10)
                    .updateInterval(20));
}

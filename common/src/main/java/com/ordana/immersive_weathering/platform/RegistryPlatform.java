package com.ordana.immersive_weathering.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RegistryPlatform {



    public enum BlockType {
        RUSTABLE_BARS, RUSTABLE_BLOCK, RUSTABLE_DOOR, RUSTABLE_SLAB,
        RUSTABLE_STAIRS, RUSTABLE_TRAPDOOR, RUSTABLE_VERTICAL_SLAB;
    }

    @ExpectPlatform
    public static Block createSpecialBlock(BlockType type, BlockBehaviour.Properties properties, Object ...extraParams) {
        throw new AssertionError();
    }

}




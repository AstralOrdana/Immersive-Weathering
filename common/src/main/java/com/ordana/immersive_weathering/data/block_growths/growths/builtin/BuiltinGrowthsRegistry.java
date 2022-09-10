package com.ordana.immersive_weathering.data.block_growths.growths.builtin;

import com.ordana.immersive_weathering.data.block_growths.TickSource;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuiltinGrowthsRegistry {

    final static Map<String, BuiltinGrowthFactory> BUILTIN_GROWTHS = new HashMap<>();

    static {
        register("no_op", NoOpBlockGrowth::new);
        register("campfire_soot", CampfireSootGrowth::new);
        register("fire_soot", FireSootGrowth::new);
        register("ice_icicle_and_melt", IceGrowth::new);
        register("leaf_piles_from_leaves", LeavesGrowth::new);
        register("icicle_from_snow", SnowIcicleGrowth::new);
        register("lightning_vitrified_sand", LightningGrowth::new);
    }


    public static void register(String name, BuiltinGrowthFactory factory) {
        BUILTIN_GROWTHS.put(name,factory);
    }


    @FunctionalInterface
    public interface BuiltinGrowthFactory{
        BuiltinBlockGrowth create(String name, HolderSet<Block> owners, List<TickSource> source);
    }

}

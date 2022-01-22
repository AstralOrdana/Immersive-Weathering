package com.ordana.mossier_moss.registry.blocks;

import com.ordana.mossier_moss.MossierMoss;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block MOSSY_BRICKS = new MossyBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block MOSSY_BRICK_STAIRS = new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_BRICKS.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block MOSSY_BRICK_SLAB = new MossySlabBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));
    public static final Block MOSSY_BRICK_WALL = new MossyWallBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.RED).requiresTool().strength(2f, 6f));

    public static final Block MOSSY_STONE = new MossyBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block MOSSY_STONE_STAIRS = new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_STONE.getDefaultState(), FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));
    public static final Block MOSSY_STONE_SLAB = new MossySlabBlock(Mossable.MossLevel.MOSSY, FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5f, 6f));

    public static void registerBlocks() {

        Registry.register(Registry.BLOCK, new Identifier(MossierMoss.MOD_ID, "mossy_bricks"), MOSSY_BRICKS);
        Registry.register(Registry.BLOCK, new Identifier(MossierMoss.MOD_ID, "mossy_brick_stairs"), MOSSY_BRICK_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(MossierMoss.MOD_ID, "mossy_brick_slab"), MOSSY_BRICK_SLAB);
        Registry.register(Registry.BLOCK, new Identifier(MossierMoss.MOD_ID, "mossy_brick_wall"), MOSSY_BRICK_WALL);

        Registry.register(Registry.BLOCK, new Identifier(MossierMoss.MOD_ID, "mossy_stone"), MOSSY_STONE);
        Registry.register(Registry.BLOCK, new Identifier(MossierMoss.MOD_ID, "mossy_stone_stairs"), MOSSY_STONE_STAIRS);
        Registry.register(Registry.BLOCK, new Identifier(MossierMoss.MOD_ID, "mossy_stone_slab"), MOSSY_STONE_SLAB);

    }
}

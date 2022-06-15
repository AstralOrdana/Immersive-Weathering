package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.*;
import com.ordana.immersive_weathering.registry.blocks.crackable.*;
import com.ordana.immersive_weathering.registry.blocks.mossable.*;
import com.ordana.immersive_weathering.registry.blocks.rustable.Rustable;
import com.ordana.immersive_weathering.registry.blocks.rustable.RustableBarsBlock;
import com.ordana.immersive_weathering.registry.blocks.rustable.RustableDoorBlock;
import com.ordana.immersive_weathering.registry.blocks.rustable.RustableTrapdoorBlock;
import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public class BlocksMixin {

    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/PropaguleBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=mangrove_propagule"
                                            )
                            )
            )
    private static PropaguleBlock mangrovePropagule(AbstractBlock.Settings settings)
    {
        return new ModPropaguleBlock(settings);
    }

    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/HangingRootsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=hanging_roots"
                                            )
                            )
            )
    private static HangingRootsBlock hangingRoots(AbstractBlock.Settings settings)
    {
        return new ModHangingRootsBlock(settings.offsetType(AbstractBlock.OffsetType.NONE));
    }

    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/GrassBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=grass_block"
                                            )
                            )
            )
    private static GrassBlock grassBlock(AbstractBlock.Settings settings)
    {
        return new ModGrassBlock(settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SnowyBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=podzol"
                                            )
                            )
            )
    private static SnowyBlock podzol(AbstractBlock.Settings settings)
    {
        return new SoilBlock(settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/MyceliumBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=mycelium"
                                            )
                            )
            )
    private static MyceliumBlock mycelium(AbstractBlock.Settings settings)
    {
        return new ModMyceliumBlock (settings);
    }

    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/Block",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=polished_blackstone_bricks"
                                            )
                            )
            )
    private static Block polishedBlackstoneBricks(AbstractBlock.Settings settings)
    {
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SlabBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=polished_blackstone_brick_slab"
                                            )
                            )
            )
    private static SlabBlock polishedBlackstoneBrickSlab(AbstractBlock.Settings settings)
    {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }
    @Shadow @Final public static Block POLISHED_BLACKSTONE_BRICKS;
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/StairsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=polished_blackstone_brick_stairs"
                                            )
                            )
            )
    private static StairsBlock polishedBlackstoneBricksStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, POLISHED_BLACKSTONE_BRICKS.getDefaultState(), settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/WallBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=polished_blackstone_brick_wall"
                                            )
                            )
            )
    private static WallBlock polishedBlackstoneBrickWall(AbstractBlock.Settings settings)
    {
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }


    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/Block",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=prismarine_bricks"
                                            )
                            )
            )
    private static Block prismarineBricks(AbstractBlock.Settings settings)
    {
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SlabBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=prismarine_brick_slab"
                                            )
                            )
            )
    private static SlabBlock prismarineBrickSlab(AbstractBlock.Settings settings)
    {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }
    @Shadow @Final public static Block PRISMARINE_BRICKS;
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/StairsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=prismarine_brick_stairs"
                                            )
                            )
            )
    private static StairsBlock prismarineBricksStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, PRISMARINE_BRICKS.getDefaultState(), settings);
    }


    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/Block",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=stone"
                                            )
                            )
            )
    private static Block stone(AbstractBlock.Settings settings)
    {
        return new CrackableMossableBlock(Mossable.MossLevel.MOSSABLE, Crackable.CrackLevel.UNCRACKED, settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SlabBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=stone_slab"
                                            )
                            )
            )
    private static SlabBlock stoneSlab(AbstractBlock.Settings settings)
    {
        return new CrackableMossableSlabBlock(Mossable.MossLevel.MOSSABLE, Crackable.CrackLevel.UNCRACKED, settings);
    }
    @Shadow @Final public static Block STONE;
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/StairsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=stone_stairs"
                                            )
                            )
            )
    private static StairsBlock stoneStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new CrackableMossableStairsBlock(Mossable.MossLevel.MOSSABLE, Crackable.CrackLevel.UNCRACKED, STONE.getDefaultState(), settings);
    }


    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/Block",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=end_stone_bricks"
                                            )
                            )
            )
    private static Block endStoneBricks(AbstractBlock.Settings settings)
    {
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SlabBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=end_stone_brick_slab"
                                            )
                            )
            )
    private static SlabBlock endStoneBrickSlab(AbstractBlock.Settings settings)
    {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }
    @Shadow @Final public static Block END_STONE_BRICKS;
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/StairsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=end_stone_brick_stairs"
                                            )
                            )
            )
    private static StairsBlock endStoneBricksStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, END_STONE_BRICKS.getDefaultState(), settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/WallBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=end_stone_brick_wall"
                                            )
                            )
            )
    private static WallBlock endStoneBrickWall(AbstractBlock.Settings settings)
    {
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }


    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/Block",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=deepslate_bricks"
                                            )
                            )
            )
    private static Block deepslateBricks(AbstractBlock.Settings settings)
    {
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SlabBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=deepslate_brick_slab"
                                            )
                            )
            )
    private static SlabBlock deepslateBrickSlab(AbstractBlock.Settings settings)
    {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }
    @Shadow @Final public static Block DEEPSLATE_BRICKS;
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/StairsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=deepslate_brick_stairs"
                                            )
                            )
            )
    private static StairsBlock deepslateBrickStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, DEEPSLATE_BRICKS.getDefaultState(), settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/WallBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=deepslate_brick_wall"
                                            )
                            )
            )
    private static WallBlock deepslateBrickWall(AbstractBlock.Settings settings)
    {
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }



    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/Block",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=deepslate_tiles"
                                            )
                            )
            )
    private static Block deepslateTiles(AbstractBlock.Settings settings)
    {
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SlabBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=deepslate_tile_slab"
                                            )
                            )
            )
    private static SlabBlock deepslateTileSlab(AbstractBlock.Settings settings)
    {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }
    @Shadow @Final public static Block DEEPSLATE_TILES;
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/StairsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=deepslate_tile_stairs"
                                            )
                            )
            )
    private static StairsBlock deepslateTileStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, DEEPSLATE_TILES.getDefaultState(), settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/WallBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=deepslate_tile_wall"
                                            )
                            )
            )
    private static WallBlock deepslateTileWall(AbstractBlock.Settings settings)
    {
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }



    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/Block",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=nether_bricks"
                                            )
                            )
            )
    private static Block netherBricks(AbstractBlock.Settings settings)
    {
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SlabBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=nether_brick_slab"
                                            )
                            )
            )
    private static SlabBlock netherBrickSlab(AbstractBlock.Settings settings)
    {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }
    @Shadow @Final public static Block NETHER_BRICKS;
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/StairsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=nether_brick_stairs"
                                            )
                            )
            )
    private static StairsBlock netherBrickStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, NETHER_BRICKS.getDefaultState(), settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/WallBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=nether_brick_wall"
                                            )
                            )
            )
    private static WallBlock netherBrickWall(AbstractBlock.Settings settings)
    {
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED,settings);
    }


    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/Block",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=bricks"
                                            )
                            )
            )
    private static Block bricks(AbstractBlock.Settings settings)
    {
        return new CrackableMossableBlock(Mossable.MossLevel.MOSSABLE, Crackable.CrackLevel.UNCRACKED, settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SlabBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=brick_slab"
                                            )
                            )
            )
    private static SlabBlock brickSlab(AbstractBlock.Settings settings)
    {
        return new CrackableMossableSlabBlock(Mossable.MossLevel.MOSSABLE, Crackable.CrackLevel.UNCRACKED, settings);
    }
    @Shadow
    @Final
    public static Block BRICKS;
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/StairsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=brick_stairs"
                                            )
                            )
            )
    private static StairsBlock brickStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new CrackableMossableStairsBlock(Mossable.MossLevel.MOSSABLE, Crackable.CrackLevel.UNCRACKED, BRICKS.getDefaultState(), settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/WallBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=brick_wall"
                                            )
                            )
            )
    private static WallBlock brickWall(AbstractBlock.Settings settings)
    {
        return new CrackableMossableWallBlock(Mossable.MossLevel.MOSSABLE, Crackable.CrackLevel.UNCRACKED,settings);
    }


    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/Block",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=stone_bricks"
                                            )
                            )
            )
    private static Block stoneBricks(AbstractBlock.Settings settings)
    {
        return new CrackableMossableBlock(Mossable.MossLevel.MOSSABLE, Crackable.CrackLevel.UNCRACKED, settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SlabBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=stone_brick_slab"
                                            )
                            )
            )
    private static SlabBlock stoneBrickSlab(AbstractBlock.Settings settings)
    {
        return new CrackableMossableSlabBlock(Mossable.MossLevel.MOSSABLE, Crackable.CrackLevel.UNCRACKED, settings);
    }
    @Shadow @Final public static Block STONE_BRICKS;
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/StairsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=stone_brick_stairs"
                                            )
                            )
            )
    private static StairsBlock stoneBricksStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new CrackableMossableStairsBlock(Mossable.MossLevel.MOSSABLE, Crackable.CrackLevel.UNCRACKED, STONE_BRICKS.getDefaultState(), settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/WallBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=stone_brick_wall"
                                            )
                            )
            )
    private static WallBlock stoneBrickWall(AbstractBlock.Settings settings)
    {
        return new CrackableMossableWallBlock(Mossable.MossLevel.MOSSABLE, Crackable.CrackLevel.UNCRACKED,settings);
    }



    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/Block",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=mossy_stone_bricks"
                                            )
                            )
            )
    private static Block mossyStoneBricks(AbstractBlock.Settings settings)
    {
        return new MossyBlock(Mossable.MossLevel.MOSSY, settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SlabBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=mossy_stone_brick_slab"
                                            )
                            )
            )
    private static SlabBlock mossyStoneBrickSlab(AbstractBlock.Settings settings)
    {
        return new MossySlabBlock(Mossable.MossLevel.MOSSY, settings);
    }
    @Shadow @Final public static Block MOSSY_STONE_BRICKS;
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/StairsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=mossy_stone_brick_stairs"
                                            )
                            )
            )
    private static StairsBlock mossyStoneBrickStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_STONE_BRICKS.getDefaultState(), settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/WallBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=mossy_stone_brick_wall"
                                            )
                            )
            )
    private static WallBlock mossyStoneBrickWall(AbstractBlock.Settings settings)
    {
        return new MossyWallBlock(Mossable.MossLevel.MOSSY, settings);
    }


    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/Block",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=cobblestone"
                                            )
                            )
            )
    private static Block cobblestone(AbstractBlock.Settings settings)
    {
        return new MossableBlock(Mossable.MossLevel.MOSSABLE, settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SlabBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=cobblestone_slab"
                                            )
                            )
            )
    private static SlabBlock cobblestoneSlab(AbstractBlock.Settings settings)
    {
        return new MossableSlabBlock(Mossable.MossLevel.MOSSABLE, settings);
    }
    @Shadow @Final public static Block COBBLESTONE;
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/StairsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=cobblestone_stairs"
                                            )
                            )
            )
    private static StairsBlock cobblestoneStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new MossableStairsBlock(Mossable.MossLevel.MOSSABLE, COBBLESTONE.getDefaultState(), settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/WallBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=cobblestone_wall"
                                            )
                            )
            )
    private static WallBlock cobblestoneWall(AbstractBlock.Settings settings)
    {
        return new MossableWallBlock(Mossable.MossLevel.MOSSABLE, settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/Block",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=mossy_cobblestone"
                                            )
                            )
            )
    private static Block mossyCobblestone(AbstractBlock.Settings settings)
    {
        return new MossyBlock(Mossable.MossLevel.MOSSY, settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/SlabBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=mossy_cobblestone_slab"
                                            )
                            )
            )
    private static SlabBlock mossyCobblestoneSlab(AbstractBlock.Settings settings)
    {
        return new MossySlabBlock(Mossable.MossLevel.MOSSY, settings);
    }
    @Shadow @Final public static Block MOSSY_COBBLESTONE;

    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/StairsBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=mossy_cobblestone_stairs"
                                            )
                            )
            )
    private static StairsBlock mossyCobblestoneStairs(BlockState baseBlockState, AbstractBlock.Settings settings)
    {
        return new MossyStairsBlock(Mossable.MossLevel.MOSSY, MOSSY_COBBLESTONE.getDefaultState(), settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/WallBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=mossy_cobblestone_wall"
                                            )
                            )
            )
    private static WallBlock mossyCobblestoneWall(AbstractBlock.Settings settings)
    {
        return new MossyWallBlock(Mossable.MossLevel.MOSSY, settings);
    }


    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/PaneBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=iron_bars"
                                            )
                            )
            )
    private static PaneBlock ironBars(AbstractBlock.Settings settings)
    {
        return new RustableBarsBlock(Rustable.RustLevel.UNAFFECTED, settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/DoorBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=iron_door"
                                            )
                            )
            )
    private static DoorBlock ironDoor(AbstractBlock.Settings settings)
    {
        return new RustableDoorBlock(Rustable.RustLevel.UNAFFECTED, settings);
    }
    @Redirect
            (
                    method = "<clinit>",
                    at = @At
                            (
                                    value = "NEW",
                                    target = "net/minecraft/block/TrapdoorBlock",
                                    ordinal = 0
                            ),
                    slice = @Slice
                            (
                                    from = @At
                                            (
                                                    value = "CONSTANT",
                                                    args="stringValue=iron_trapdoor"
                                            )
                            )
            )
    private static TrapdoorBlock ironTrapdoor(AbstractBlock.Settings settings)
    {
        return new RustableTrapdoorBlock(Rustable.RustLevel.UNAFFECTED, settings);
    }
}
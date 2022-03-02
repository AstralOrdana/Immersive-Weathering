package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.*;
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
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, settings);
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
                                                    args="stringValue=cracked_polished_blackstone_bricks"
                                            )
                            )
            )
    private static Block crackedPolishedBlackstoneBricks(AbstractBlock.Settings settings)
    {
        return new CrackedBlock(Crackable.CrackLevel.CRACKED, settings);
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
                                                    args="stringValue=deepslate_brick_slab"
                                            )
                            )
            )
    private static SlabBlock deepslateBrickSlab(AbstractBlock.Settings settings)
    {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, settings);
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
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, settings);
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
                                                    args="stringValue=cracked_deepslate_bricks"
                                            )
                            )
            )
    private static Block crackedDeepslateBricks(AbstractBlock.Settings settings)
    {
        return new CrackedBlock(Crackable.CrackLevel.CRACKED, settings);
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
                                                    args="stringValue=deepslate_tile_slab"
                                            )
                            )
            )
    private static SlabBlock deepslateTileSlab(AbstractBlock.Settings settings)
    {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, settings);
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
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, settings);
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
                                                    args="stringValue=cracked_deepslate_tiles"
                                            )
                            )
            )
    private static Block crackedDeepslateTiles(AbstractBlock.Settings settings)
    {
        return new CrackedBlock(Crackable.CrackLevel.CRACKED, settings);
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
                                                    args="stringValue=nether_brick_slab"
                                            )
                            )
            )
    private static SlabBlock netherBrickSlab(AbstractBlock.Settings settings)
    {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, settings);
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
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, settings);
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
                                                    args="stringValue=cracked_nether_bricks"
                                            )
                            )
            )
    private static Block crackedNetherBricks(AbstractBlock.Settings settings)
    {
        return new CrackedBlock(Crackable.CrackLevel.CRACKED, settings);
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
        return new MossableBlock(Mossable.MossLevel.UNAFFECTED, settings);
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
        return new MossableSlabBlock(Mossable.MossLevel.UNAFFECTED, settings);
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
        return new MossableStairsBlock(Mossable.MossLevel.UNAFFECTED, BRICKS.getDefaultState(), settings);
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
        return new MossableWallBlock(Mossable.MossLevel.UNAFFECTED, settings);
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
        return new MossableBlock(Mossable.MossLevel.UNAFFECTED, settings);
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
                                                    args="stringValue=cracked_stone_bricks"
                                            )
                            )
            )
    private static Block crackedStoneBricks(AbstractBlock.Settings settings)
    {
        return new CrackedBlock(Crackable.CrackLevel.CRACKED, settings);
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
        return new MossableSlabBlock(Mossable.MossLevel.UNAFFECTED, settings);
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
        return new MossableStairsBlock(Mossable.MossLevel.UNAFFECTED, STONE_BRICKS.getDefaultState(), settings);
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
        return new MossableWallBlock(Mossable.MossLevel.UNAFFECTED, settings);
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
        return new MossableBlock(Mossable.MossLevel.UNAFFECTED, settings);
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
        return new MossableSlabBlock(Mossable.MossLevel.UNAFFECTED, settings);
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
        return new MossableStairsBlock(Mossable.MossLevel.UNAFFECTED, COBBLESTONE.getDefaultState(), settings);
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
        return new MossableWallBlock(Mossable.MossLevel.UNAFFECTED, settings);
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
        return new CleanRustableDoorBlock(Rustable.RustLevel.UNAFFECTED, settings);
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
        return new CleanRustableTrapdoorBlock(Rustable.RustLevel.UNAFFECTED, settings);
    }
}

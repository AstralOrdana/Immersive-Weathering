package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.registry.blocks.crackable.*;
import com.ordana.immersive_weathering.registry.blocks.mossable.*;
import com.ordana.immersive_weathering.registry.blocks.rustable.Rustable;
import com.ordana.immersive_weathering.registry.blocks.rustable.RustableBarsBlock;
import com.ordana.immersive_weathering.registry.blocks.rustable.RustableTrapdoorBlock;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public class BlocksMixin {
    @Redirect(method = "<clinit>", at = @At(
            value = "NEW",
            target = "net/minecraft/world/level/block/Block",
            ordinal = 0
    ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=polished_blackstone_bricks"
                    )
            )
    )
    private static Block polishedBlackstoneBricks(BlockBehaviour.Properties settings) {
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }

    @Redirect(method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/SlabBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=polished_blackstone_brick_slab"
                    )
            )
    )
    private static SlabBlock polishedBlackstoneBrickSlab(BlockBehaviour.Properties settings) {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }

    @Shadow
    @Final
    public static Block POLISHED_BLACKSTONE_BRICKS;

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/StairBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=polished_blackstone_brick_stairs"
                    )
            )
    )
    private static StairBlock polishedBlackstoneBricksStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> POLISHED_BLACKSTONE_BRICKS, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/WallBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=polished_blackstone_brick_wall"
                    )
            )
    )
    private static WallBlock polishedBlackstoneBrickWall(BlockBehaviour.Properties settings) {
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/Block",
                    ordinal = 0
            ),
            slice = @Slice
                    (
                            from = @At
                                    (
                                            value = "CONSTANT",
                                            args = "stringValue=cracked_polished_blackstone_bricks"
                                    )
                    )
    )
    private static Block crackedPolishedBlackstoneBricks(BlockBehaviour.Properties settings) {
        return new CrackedBlock(Crackable.CrackLevel.CRACKED, settings);
    }


    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/Block",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=deepslate_bricks"
                    )
            )
    )
    private static Block deepslateBricks(BlockBehaviour.Properties settings) {
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/SlabBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=deepslate_brick_slab"
                    )
            )
    )
    private static SlabBlock deepslateBrickSlab(BlockBehaviour.Properties settings) {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }

    @Shadow
    @Final
    public static Block DEEPSLATE_BRICKS;

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/StairBlock",
                    ordinal = 0
            ),
            slice = @Slice
                    (
                            from = @At(
                                    value = "CONSTANT",
                                    args = "stringValue=deepslate_brick_stairs"
                            )
                    )
    )
    private static StairBlock deepslateBrickStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED,()-> DEEPSLATE_BRICKS, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/WallBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=deepslate_brick_wall"
                    )
            )
    )
    private static WallBlock deepslateBrickWall(BlockBehaviour.Properties settings) {
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/Block",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=deepslate_tiles"
                    )
            )
    )
    private static Block deepslateTiles(BlockBehaviour.Properties settings) {
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/SlabBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=deepslate_tile_slab"
                    )
            )
    )
    private static SlabBlock deepslateTileSlab(BlockBehaviour.Properties settings) {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }

    @Shadow
    @Final
    public static Block DEEPSLATE_TILES;

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/StairBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=deepslate_tile_stairs"
                    )
            )
    )
    private static StairBlock deepslateTileStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, ()->DEEPSLATE_TILES, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/WallBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=deepslate_tile_wall"
                    )
            )
    )
    private static WallBlock deepslateTileWall(BlockBehaviour.Properties settings) {
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }

    @Redirect
            (
                    method = "<clinit>",
                    at = @At(
                            value = "NEW",
                            target = "net/minecraft/world/level/block/Block",
                            ordinal = 0
                    ),
                    slice = @Slice(
                            from = @At(
                                    value = "CONSTANT",
                                    args = "stringValue=nether_bricks"
                            )
                    )
            )
    private static Block netherBricks(BlockBehaviour.Properties settings) {
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/SlabBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=nether_brick_slab"
                    )
            )
    )
    private static SlabBlock netherBrickSlab(BlockBehaviour.Properties settings) {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }

    @Shadow
    @Final
    public static Block NETHER_BRICKS;

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/StairBlock",
                    ordinal = 0
            ),
            slice = @Slice
                    (
                            from = @At
                                    (
                                            value = "CONSTANT",
                                            args = "stringValue=nether_brick_stairs"
                                    )
                    )
    )
    private static StairBlock netherBrickStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> NETHER_BRICKS, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/WallBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=nether_brick_wall"
                    )
            )
    )
    private static WallBlock netherBrickWall(BlockBehaviour.Properties settings) {
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/Block",
                    ordinal = 0
            ),
            slice = @Slice
                    (
                            from = @At(
                                    value = "CONSTANT",
                                    args = "stringValue=cracked_nether_bricks"
                            )
                    )
    )
    private static Block crackedNetherBricks(BlockBehaviour.Properties settings) {
        return new CrackedBlock(Crackable.CrackLevel.CRACKED, settings);
    }


    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/Block",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=bricks"
                    )
            )
    )
    private static Block bricks(BlockBehaviour.Properties settings) {
        return new MossableBlock(Mossable.MossLevel.UNAFFECTED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/SlabBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=brick_slab"
                    )
            )
    )
    private static SlabBlock brickSlab(BlockBehaviour.Properties settings) {
        return new MossableSlabBlock(Mossable.MossLevel.UNAFFECTED, settings);
    }

    @Shadow
    @Final
    public static Block BRICKS;

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/StairBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=brick_stairs"
                    )
            )
    )
    private static StairBlock brickStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        return new MossableStairsBlock(Mossable.MossLevel.UNAFFECTED, ()-> BRICKS, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/WallBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=brick_wall"
                    )
            )
    )
    private static WallBlock brickWall(BlockBehaviour.Properties settings) {
        return new MossableWallBlock(Mossable.MossLevel.UNAFFECTED, settings);
    }


    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/Block",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=stone_bricks"
                    )
            )
    )
    private static Block stoneBricks(BlockBehaviour.Properties settings) {
        return new MossableBlock(Mossable.MossLevel.UNAFFECTED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/SlabBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=stone_brick_slab"
                    )
            )
    )
    private static SlabBlock stoneBrickSlab(BlockBehaviour.Properties settings) {
        return new MossableSlabBlock(Mossable.MossLevel.UNAFFECTED, settings);
    }

    @Shadow
    @Final
    public static Block STONE_BRICKS;

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/StairBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=stone_brick_stairs"
                    )
            )
    )
    private static StairBlock stoneBricksStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        return new MossableStairsBlock(Mossable.MossLevel.UNAFFECTED, ()->STONE_BRICKS, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/WallBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=stone_brick_wall"
                    )
            )
    )
    private static WallBlock stoneBrickWall(BlockBehaviour.Properties settings) {
        return new MossableWallBlock(Mossable.MossLevel.UNAFFECTED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/Block",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=cobblestone"
                    )
            )
    )
    private static Block cobblestone(BlockBehaviour.Properties settings) {
        return new MossableBlock(Mossable.MossLevel.UNAFFECTED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/SlabBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=cobblestone_slab"
                    )
            )
    )
    private static SlabBlock cobblestoneSlab(BlockBehaviour.Properties settings) {
        return new MossableSlabBlock(Mossable.MossLevel.UNAFFECTED, settings);
    }

    @Shadow
    @Final
    public static Block COBBLESTONE;

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/StairBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=cobblestone_stairs"
                    )
            )
    )
    private static StairBlock cobblestoneStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        return new MossableStairsBlock(Mossable.MossLevel.UNAFFECTED,()-> COBBLESTONE, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/WallBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=cobblestone_wall"
                    )
            )
    )
    private static WallBlock cobblestoneWall(BlockBehaviour.Properties settings) {
        return new MossableWallBlock(Mossable.MossLevel.UNAFFECTED, settings);
    }
    /*
    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/PaneBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=iron_bars"
                    )
            )
    )
    private static IronBarsBlock ironBars(BlockBehaviour.Properties settings) {
        return new RustableBarsBlock(Rustable.RustLevel.UNAFFECTED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/DoorBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=iron_door"
                    )
            )
    )

    private static DoorBlock ironDoor(BlockBehaviour.Properties settings) {
        return new RustableDoorBlock(Rustable.RustLevel.UNAFFECTED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/TrapdoorBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At
                            (
                                    value = "CONSTANT",
                                    args = "stringValue=iron_trapdoor"
                            )
            )
    )
    private static TrapDoorBlock ironTrapdoor(BlockBehaviour.Properties settings) {
        return new RustableTrapdoorBlock(Rustable.RustLevel.UNAFFECTED, settings);
    }
    */

}
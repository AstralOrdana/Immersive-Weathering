package com.ordana.immersive_weathering.mixin;

import com.ordana.immersive_weathering.common.blocks.ModGrassBlock;
import com.ordana.immersive_weathering.common.blocks.ModMyceliumBlock;
import com.ordana.immersive_weathering.common.blocks.SoilBlock;
import com.ordana.immersive_weathering.common.blocks.crackable.*;
import com.ordana.immersive_weathering.common.blocks.mossable.*;
import com.ordana.immersive_weathering.common.blocks.rustable.Rustable;
import com.ordana.immersive_weathering.common.blocks.rustable.RustableBarsBlock;
import com.ordana.immersive_weathering.common.blocks.rustable.RustableDoorBlock;
import com.ordana.immersive_weathering.common.blocks.rustable.RustableTrapdoorBlock;
import com.ordana.immersive_weathering.common.items.ModItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@SuppressWarnings("ALL")
@Mixin(Blocks.class)
public abstract class BlocksMixin {

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
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.BLACKSTONE_BRICK.get(), settings);
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
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.BLACKSTONE_BRICK.get(), settings);
    }

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
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> Blocks.POLISHED_BLACKSTONE_BRICKS, () -> ModItems.BLACKSTONE_BRICK.get(), settings);
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
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.BLACKSTONE_BRICK.get(), settings);
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
        return new CrackedBlock(Crackable.CrackLevel.CRACKED, () -> ModItems.BLACKSTONE_BRICK.get(), settings);
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
                                            args = "stringValue=cracked_stone_bricks"
                                    )
                    )
    )
    private static Block crackedStoneBricks(BlockBehaviour.Properties settings) {
        return new CrackedBlock(Crackable.CrackLevel.CRACKED, () -> ModItems.STONE_BRICK.get(), settings);
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
                                            args = "stringValue=cracked_deepslate_bricks"
                                    )
                    )
    )
    private static Block crackedDeepslateBricks(BlockBehaviour.Properties settings) {
        return new CrackedBlock(Crackable.CrackLevel.CRACKED, () -> ModItems.DEEPSLATE_BRICK.get(), settings);
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
                                            args = "stringValue=cracked_deepslate_tiles"
                                    )
                    )
    )
    private static Block crackedDeepslateTiles(BlockBehaviour.Properties settings) {
        return new CrackedBlock(Crackable.CrackLevel.CRACKED, () -> ModItems.DEEPSLATE_TILE.get(), settings);
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
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.DEEPSLATE_BRICK.get(), settings);
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
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.DEEPSLATE_BRICK.get(), settings);
    }

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
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> Blocks.DEEPSLATE_BRICKS, () -> ModItems.DEEPSLATE_BRICK.get(), settings);
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
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.DEEPSLATE_BRICK.get(), settings);
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
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.DEEPSLATE_TILE.get(), settings);
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
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.DEEPSLATE_TILE.get(), settings);
    }

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
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> Blocks.DEEPSLATE_TILES, () -> ModItems.DEEPSLATE_TILE.get(), settings);
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
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.DEEPSLATE_TILE.get(), settings);
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
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED, () -> Items.NETHER_BRICK, settings);
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
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, () -> Items.NETHER_BRICK, settings);
    }

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
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> Blocks.NETHER_BRICKS, () -> Items.NETHER_BRICK, settings);
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
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, () -> Items.NETHER_BRICK, settings);
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
        return new CrackedBlock(Crackable.CrackLevel.CRACKED, () -> Items.NETHER_BRICK, settings);
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
        return new CrackableMossableBlock(Mossable.MossLevel.UNAFFECTED, Crackable.CrackLevel.UNCRACKED, () -> Items.BRICK, settings);
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
        return new CrackableMossableSlabBlock(Mossable.MossLevel.UNAFFECTED, Crackable.CrackLevel.UNCRACKED, () -> Items.BRICK, settings);
    }

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
        return new CrackableMossableStairsBlock(Mossable.MossLevel.UNAFFECTED, Crackable.CrackLevel.UNCRACKED, () -> Items.BRICK, () -> Blocks.BRICKS, settings);
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
        return new CrackableMossableWallBlock(Mossable.MossLevel.UNAFFECTED, Crackable.CrackLevel.UNCRACKED, () -> Items.BRICK, settings);
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
        return new CrackableMossableBlock(Mossable.MossLevel.UNAFFECTED, Crackable.CrackLevel.UNCRACKED, () -> ModItems.STONE_BRICK.get(), settings);
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
        return new CrackableMossableSlabBlock(Mossable.MossLevel.UNAFFECTED, Crackable.CrackLevel.UNCRACKED, () -> ModItems.STONE_BRICK.get(), settings);
    }

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
        return new CrackableMossableStairsBlock(Mossable.MossLevel.UNAFFECTED, Crackable.CrackLevel.UNCRACKED, () -> ModItems.STONE_BRICK.get(), () -> Blocks.STONE_BRICKS, settings);
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
        return new CrackableMossableWallBlock(Mossable.MossLevel.UNAFFECTED, Crackable.CrackLevel.UNCRACKED, () -> ModItems.STONE_BRICK.get(), settings);
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
        return new MossableStairsBlock(Mossable.MossLevel.UNAFFECTED, () -> Blocks.COBBLESTONE, settings);
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
                            args = "stringValue=mossy_stone_bricks"
                    )
            )
    )
    private static Block mossyStoneBricks(BlockBehaviour.Properties settings) {
        return new MossyBlock(Mossable.MossLevel.MOSSY, settings);
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
                            args = "stringValue=mossy_stone_brick_slab"
                    )
            )
    )
    private static SlabBlock mossyStoneBrickSlab(BlockBehaviour.Properties settings) {
        return new MossySlabBlock(Mossable.MossLevel.MOSSY, settings);
    }

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
                            args = "stringValue=mossy_stone_brick_stairs"
                    )
            )
    )
    private static StairBlock mossyStoneBrickStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        return new MossyStairsBlock(Mossable.MossLevel.MOSSY, () -> Blocks.MOSSY_STONE_BRICKS, settings);
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
                            args = "stringValue=mossy_stone_brick_wall"
                    )
            )
    )
    private static WallBlock mossyStoneBrickWall(BlockBehaviour.Properties settings) {
        return new MossyWallBlock(Mossable.MossLevel.MOSSY, settings);
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
                            args = "stringValue=mossy_cobblestone"
                    )
            )
    )
    private static Block mossyCobblestone(BlockBehaviour.Properties settings) {
        return new MossyBlock(Mossable.MossLevel.MOSSY, settings);
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
                            args = "stringValue=mossy_cobblestone_slab"
                    )
            )
    )
    private static SlabBlock mossyCobblestoneSlab(BlockBehaviour.Properties settings) {
        return new MossySlabBlock(Mossable.MossLevel.MOSSY, settings);
    }

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
                            args = "stringValue=mossy_cobblestone_stairs"
                    )
            )
    )
    private static StairBlock mossyCobblestoneStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        return new MossyStairsBlock(Mossable.MossLevel.MOSSY, () -> Blocks.MOSSY_COBBLESTONE, settings);
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
                            args = "stringValue=mossy_cobblestone_wall"
                    )
            )
    )
    private static WallBlock mossyCobblestoneWall(BlockBehaviour.Properties settings) {
        return new MossyWallBlock(Mossable.MossLevel.MOSSY, settings);
    }


    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/IronBarsBlock",
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
        return new RustableDoorBlock(Rustable.RustLevel.UNAFFECTED, settings.sound(SoundType.COPPER));
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/TrapDoorBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=iron_trapdoor"
                    )
            )
    )
    private static TrapDoorBlock ironTrapdoor(BlockBehaviour.Properties settings) {
        return new RustableTrapdoorBlock(Rustable.RustLevel.UNAFFECTED, settings.sound(SoundType.COPPER));
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
                            args = "stringValue=prismarine_bricks"
                    )
            )
    )
    private static Block prismarineBricks(BlockBehaviour.Properties settings) {
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.PRISMARINE_BRICK.get(), settings);
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
                            args = "stringValue=prismarine_brick_slab"
                    )
            )
    )
    private static SlabBlock prismarineBrickSlab(BlockBehaviour.Properties settings) {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.PRISMARINE_BRICK.get(), settings);
    }

    @Shadow
    @Final
    public static Block PRISMARINE_BRICKS;

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
                            args = "stringValue=prismarine_brick_stairs"
                    )
            )
    )
    private static StairBlock prismarineBricksStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> PRISMARINE_BRICKS, () -> ModItems.PRISMARINE_BRICK.get(), settings);
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
                            args = "stringValue=end_stone_bricks"
                    )
            )
    )
    private static Block endStoneBricks(BlockBehaviour.Properties settings) {
        return new CrackableBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.END_STONE_BRICK.get(), settings);
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
                            args = "stringValue=end_stone_brick_slab"
                    )
            )
    )
    private static SlabBlock endStoneBrickSlab(BlockBehaviour.Properties settings) {
        return new CrackableSlabBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.END_STONE_BRICK.get(), settings);
    }

    @Shadow
    @Final
    public static Block END_STONE_BRICKS;

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
                            args = "stringValue=end_stone_brick_stairs"
                    )
            )
    )
    private static StairBlock endStoneBricksStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> END_STONE_BRICKS, () -> ModItems.END_STONE_BRICK.get(), settings);
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
                            args = "stringValue=end_stone_brick_wall"
                    )
            )
    )
    private static WallBlock endStoneBrickWall(BlockBehaviour.Properties settings) {
        return new CrackableWallBlock(Crackable.CrackLevel.UNCRACKED, () -> ModItems.END_STONE_BRICK.get(), settings);
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
                            args = "stringValue=stone"
                    )
            )
    )
    private static Block stone(BlockBehaviour.Properties settings) {
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
                            args = "stringValue=stone_slab"
                    )
            )
    )
    private static SlabBlock stoneSlab(BlockBehaviour.Properties settings) {
        return new MossableSlabBlock(Mossable.MossLevel.UNAFFECTED, settings);
    }

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
                            args = "stringValue=stone_stairs"
                    )
            )
    )
    private static StairBlock stoneStairs(BlockState baseBlockState, BlockBehaviour.Properties settings) {
        return new MossableStairsBlock(Mossable.MossLevel.UNAFFECTED, () -> Blocks.STONE, settings);
    }


    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/GrassBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=grass_block"
                    )
            )
    )
    private static GrassBlock grassBlock(BlockBehaviour.Properties settings) {
        return new ModGrassBlock(settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/SnowyDirtBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=podzol"
                    )
            )
    )
    private static SnowyDirtBlock podzol(BlockBehaviour.Properties settings) {
        return new SoilBlock(settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/world/level/block/MyceliumBlock",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=mycelium"
                    )
            )
    )
    private static MyceliumBlock mycelium(BlockBehaviour.Properties settings) {
        return new ModMyceliumBlock(settings);
    }

}
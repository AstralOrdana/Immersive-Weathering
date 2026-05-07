package com.ordana.immersive_weathering.mixins;

import com.ordana.immersive_weathering.blocks.ModPropaguleBlock;
import com.ordana.immersive_weathering.blocks.cracked.*;
import com.ordana.immersive_weathering.blocks.mossy.*;
import com.ordana.immersive_weathering.blocks.rusty.Rustable;
import com.ordana.immersive_weathering.blocks.rusty.RustableBarsBlock;
import com.ordana.immersive_weathering.blocks.rusty.RustableDoorBlock;
import com.ordana.immersive_weathering.blocks.rusty.RustableTrapdoorBlock;
import com.ordana.immersive_weathering.reg.ModItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public abstract class BlocksMixin {

        private static BlockBehaviour.Properties legacyStairProperties(Block block) {
                return BlockBehaviour.Properties.ofFullCopy(block);
        }

    @Redirect(method = "<clinit>", at = @At(
            value = "NEW",
            target = "(Lnet/minecraft/world/level/block/grower/TreeGrower;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/MangrovePropaguleBlock;",
            ordinal = 0
    ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=mangrove_propagule"
                    )
            )
    )
        private static MangrovePropaguleBlock mangrovePropaguleBlock(TreeGrower grower, BlockBehaviour.Properties settings) {
        return new ModPropaguleBlock(settings);
    }

    @Redirect(method = "<clinit>", at = @At(
            value = "NEW",
            target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SlabBlock;",
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;legacyStair(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=polished_blackstone_brick_stairs"
                    )
            )
    )
        private static Block polishedBlackstoneBricksStairs(Block baseBlock) {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> Blocks.POLISHED_BLACKSTONE_BRICKS, () -> ModItems.BLACKSTONE_BRICK.get(), legacyStairProperties(baseBlock));
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/WallBlock;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SlabBlock;",
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;legacyStair(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
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
        private static Block deepslateBrickStairs(Block baseBlock) {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> Blocks.DEEPSLATE_BRICKS, () -> ModItems.DEEPSLATE_BRICK.get(), legacyStairProperties(baseBlock));
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/WallBlock;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SlabBlock;",
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;legacyStair(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=deepslate_tile_stairs"
                    )
            )
    )
        private static Block deepslateTileStairs(Block baseBlock) {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> Blocks.DEEPSLATE_TILES, () -> ModItems.DEEPSLATE_TILE.get(), legacyStairProperties(baseBlock));
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/WallBlock;",
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
                            target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SlabBlock;",
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;legacyStair(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
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
                private static Block netherBrickStairs(Block baseBlock) {
                return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> Blocks.NETHER_BRICKS, () -> Items.NETHER_BRICK, legacyStairProperties(baseBlock));
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/WallBlock;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SlabBlock;",
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;legacyStair(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=brick_stairs"
                    )
            )
    )
        private static Block brickStairs(Block baseBlock) {
        return new CrackableMossableStairsBlock(Mossable.MossLevel.UNAFFECTED, Crackable.CrackLevel.UNCRACKED, () -> Items.BRICK, () -> Blocks.BRICKS, legacyStairProperties(baseBlock));
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/WallBlock;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SlabBlock;",
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;legacyStair(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=stone_brick_stairs"
                    )
            )
    )
        private static Block stoneBricksStairs(Block baseBlock) {
        return new CrackableMossableStairsBlock(Mossable.MossLevel.UNAFFECTED, Crackable.CrackLevel.UNCRACKED, () -> ModItems.STONE_BRICK.get(), () -> Blocks.STONE_BRICKS, legacyStairProperties(baseBlock));
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/WallBlock;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=chiseled_stone_bricks"
                    )
            )
    )
    private static Block chiseled_stone_bricks(BlockBehaviour.Properties settings) {
        return new MossableBlock(Mossable.MossLevel.UNAFFECTED, settings);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SlabBlock;",
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;legacyStair(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=cobblestone_stairs"
                    )
            )
    )
        private static Block cobblestoneStairs(Block baseBlock) {
        return new MossableStairsBlock(Mossable.MossLevel.UNAFFECTED, () -> Blocks.COBBLESTONE, legacyStairProperties(baseBlock));
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/WallBlock;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SlabBlock;",
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;legacyStair(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=mossy_stone_brick_stairs"
                    )
            )
    )
        private static Block mossyStoneBrickStairs(Block baseBlock) {
        return new MossyStairsBlock(Mossable.MossLevel.MOSSY, () -> Blocks.MOSSY_STONE_BRICKS, legacyStairProperties(baseBlock));
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/WallBlock;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SlabBlock;",
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;legacyStair(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=mossy_cobblestone_stairs"
                    )
            )
    )
        private static Block mossyCobblestoneStairs(Block baseBlock) {
        return new MossyStairsBlock(Mossable.MossLevel.MOSSY, () -> Blocks.MOSSY_COBBLESTONE, legacyStairProperties(baseBlock));
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/WallBlock;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/IronBarsBlock;",
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
                    target = "(Lnet/minecraft/world/level/block/state/properties/BlockSetType;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/DoorBlock;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=iron_door"
                    )
            )
    )

        private static DoorBlock ironDoor(BlockSetType type, BlockBehaviour.Properties properties) {
        return new RustableDoorBlock(Rustable.RustLevel.UNAFFECTED, properties);
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/properties/BlockSetType;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/TrapDoorBlock;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=iron_trapdoor"
                    )
            )
    )
        private static TrapDoorBlock ironTrapdoor(BlockSetType type, BlockBehaviour.Properties properties) {
        return new RustableTrapdoorBlock(Rustable.RustLevel.UNAFFECTED, properties);
    }


    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SlabBlock;",
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;legacyStair(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=prismarine_brick_stairs"
                    )
            )
    )
        private static Block prismarineBricksStairs(Block baseBlock) {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> PRISMARINE_BRICKS, () -> ModItems.PRISMARINE_BRICK.get(), legacyStairProperties(baseBlock));
    }


    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SlabBlock;",
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;legacyStair(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=end_stone_brick_stairs"
                    )
            )
    )
        private static Block endStoneBricksStairs(Block baseBlock) {
        return new CrackableStairsBlock(Crackable.CrackLevel.UNCRACKED, () -> END_STONE_BRICKS, () -> ModItems.END_STONE_BRICK.get(), legacyStairProperties(baseBlock));
    }

    @Redirect(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/WallBlock;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;",
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
                    target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/SlabBlock;",
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
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Blocks;legacyStair(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;",
                    ordinal = 0
            ),
            slice = @Slice(
                    from = @At(
                            value = "CONSTANT",
                            args = "stringValue=stone_stairs"
                    )
            )
    )
        private static Block stoneStairs(Block baseBlock) {
        return new MossableStairsBlock(Mossable.MossLevel.UNAFFECTED, () -> Blocks.STONE, legacyStairProperties(baseBlock));
    }

}
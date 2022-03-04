package com.ordana.immersive_weathering.registry;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.item.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import java.util.HashMap;
import java.util.Random;

public class ModEvents {
    private static final HashMap<Block, Block> CLEANED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Block> CRACKED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Item> DROPPED_BRICKS = new HashMap<>();
    private static final HashMap<Item, Block> BRICK_REPAIR = new HashMap<>();
    private static final HashMap<Item, Block> BRICK_REPAIR_SLABS = new HashMap<>();
    private static final HashMap<Item, Block> BRICK_REPAIR_STAIRS = new HashMap<>();
    private static final HashMap<Item, Block> BRICK_REPAIR_WALLS = new HashMap<>();
    private static final HashMap<Block, Block> STRIPPED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Item> DROPPED_BARK = new HashMap<>();
    private static final HashMap<Item, Block> UNSTRIP_LOG = new HashMap<>();
    private static final HashMap<Item, Block> UNSTRIP_WOOD = new HashMap<>();
    private static final HashMap<Block, Block> RUSTED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Block> FLOWERY_BLOCKS = new HashMap<>();

    public static void registerEvents() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            BlockPos targetPos = hitResult.getBlockPos();
            BlockPos fixedPos = targetPos.relative(hitResult.getDirection());
            BlockState targetBlock = world.getBlockState(targetPos);
            ItemStack heldItem = player.getItemInHand(hand);

            CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE);
            CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.COBBLESTONE_SLAB);
            CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.COBBLESTONE_STAIRS);
            CLEANED_BLOCKS.put(Blocks.MOSSY_COBBLESTONE_WALL, Blocks.COBBLESTONE_WALL);
            CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICKS, Blocks.STONE_BRICKS);
            CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.STONE_BRICK_SLAB);
            CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.STONE_BRICK_STAIRS);
            CLEANED_BLOCKS.put(Blocks.MOSSY_STONE_BRICK_WALL, Blocks.STONE_BRICK_WALL);
            CLEANED_BLOCKS.put(ModBlocks.MOSSY_BRICKS, Blocks.BRICKS);
            CLEANED_BLOCKS.put(ModBlocks.MOSSY_BRICK_SLAB, Blocks.BRICK_SLAB);
            CLEANED_BLOCKS.put(ModBlocks.MOSSY_BRICK_STAIRS, Blocks.BRICK_STAIRS);
            CLEANED_BLOCKS.put(ModBlocks.MOSSY_BRICK_WALL, Blocks.BRICK_WALL);
            CLEANED_BLOCKS.put(ModBlocks.MOSSY_STONE, Blocks.STONE);
            CLEANED_BLOCKS.put(ModBlocks.MOSSY_STONE_SLAB, Blocks.STONE_SLAB);
            CLEANED_BLOCKS.put(ModBlocks.MOSSY_STONE_STAIRS, Blocks.STONE_STAIRS);

            CRACKED_BLOCKS.put(Blocks.BRICKS, ModBlocks.CRACKED_BRICKS);
            CRACKED_BLOCKS.put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
            CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
            CRACKED_BLOCKS.put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
            CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS);
            CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES);

            CRACKED_BLOCKS.put(Blocks.BRICK_SLAB, ModBlocks.CRACKED_BRICK_SLAB);
            CRACKED_BLOCKS.put(Blocks.STONE_BRICK_SLAB, ModBlocks.CRACKED_STONE_BRICK_SLAB);
            CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_SLAB);
            CRACKED_BLOCKS.put(Blocks.NETHER_BRICK_SLAB, ModBlocks.CRACKED_NETHER_BRICK_SLAB);
            CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICK_SLAB, ModBlocks.CRACKED_DEEPSLATE_BRICK_SLAB);
            CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILE_SLAB, ModBlocks.CRACKED_DEEPSLATE_TILE_SLAB);

            CRACKED_BLOCKS.put(Blocks.BRICK_STAIRS, ModBlocks.CRACKED_BRICK_STAIRS);
            CRACKED_BLOCKS.put(Blocks.STONE_BRICK_STAIRS, ModBlocks.CRACKED_STONE_BRICK_STAIRS);
            CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_STAIRS);
            CRACKED_BLOCKS.put(Blocks.NETHER_BRICK_STAIRS, ModBlocks.CRACKED_NETHER_BRICK_STAIRS);
            CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICK_STAIRS, ModBlocks.CRACKED_DEEPSLATE_BRICK_STAIRS);
            CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILE_STAIRS, ModBlocks.CRACKED_DEEPSLATE_TILE_STAIRS);

            CRACKED_BLOCKS.put(Blocks.BRICK_WALL, ModBlocks.CRACKED_BRICK_WALL);
            CRACKED_BLOCKS.put(Blocks.STONE_BRICK_WALL, ModBlocks.CRACKED_STONE_BRICK_WALL);
            CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, ModBlocks.CRACKED_POLISHED_BLACKSTONE_BRICK_WALL);
            CRACKED_BLOCKS.put(Blocks.NETHER_BRICK_WALL, ModBlocks.CRACKED_NETHER_BRICK_WALL);
            CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICK_WALL, ModBlocks.CRACKED_DEEPSLATE_BRICK_WALL);
            CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILE_WALL, ModBlocks.CRACKED_DEEPSLATE_TILE_WALL);

            DROPPED_BRICKS.put(Blocks.STONE_BRICKS, ModItems.STONE_BRICK);
            DROPPED_BRICKS.put(Blocks.POLISHED_BLACKSTONE_BRICKS, ModItems.BLACKSTONE_BRICK);
            DROPPED_BRICKS.put(Blocks.NETHER_BRICKS, Items.NETHER_BRICK);
            DROPPED_BRICKS.put(Blocks.DEEPSLATE_BRICKS, ModItems.DEEPSLATE_BRICK);
            DROPPED_BRICKS.put(Blocks.DEEPSLATE_TILES, ModItems.DEEPSLATE_TILE);
            DROPPED_BRICKS.put(Blocks.BRICKS, Items.BRICK);

            DROPPED_BRICKS.put(Blocks.STONE_BRICK_SLAB, ModItems.STONE_BRICK);
            DROPPED_BRICKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, ModItems.BLACKSTONE_BRICK);
            DROPPED_BRICKS.put(Blocks.NETHER_BRICK_SLAB, Items.NETHER_BRICK);
            DROPPED_BRICKS.put(Blocks.DEEPSLATE_BRICK_SLAB, ModItems.DEEPSLATE_BRICK);
            DROPPED_BRICKS.put(Blocks.DEEPSLATE_TILE_SLAB, ModItems.DEEPSLATE_TILE);
            DROPPED_BRICKS.put(Blocks.BRICK_SLAB, Items.BRICK);

            DROPPED_BRICKS.put(Blocks.STONE_BRICK_STAIRS, ModItems.STONE_BRICK);
            DROPPED_BRICKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS, ModItems.BLACKSTONE_BRICK);
            DROPPED_BRICKS.put(Blocks.NETHER_BRICK_STAIRS, Items.NETHER_BRICK);
            DROPPED_BRICKS.put(Blocks.DEEPSLATE_BRICK_STAIRS, ModItems.DEEPSLATE_BRICK);
            DROPPED_BRICKS.put(Blocks.DEEPSLATE_TILE_STAIRS, ModItems.DEEPSLATE_TILE);
            DROPPED_BRICKS.put(Blocks.BRICK_STAIRS, Items.BRICK);

            DROPPED_BRICKS.put(Blocks.STONE_BRICK_WALL, ModItems.STONE_BRICK);
            DROPPED_BRICKS.put(Blocks.POLISHED_BLACKSTONE_BRICK_WALL, ModItems.BLACKSTONE_BRICK);
            DROPPED_BRICKS.put(Blocks.NETHER_BRICK_WALL, Items.NETHER_BRICK);
            DROPPED_BRICKS.put(Blocks.DEEPSLATE_BRICK_WALL, ModItems.DEEPSLATE_BRICK);
            DROPPED_BRICKS.put(Blocks.DEEPSLATE_TILE_WALL, ModItems.DEEPSLATE_TILE);
            DROPPED_BRICKS.put(Blocks.BRICK_WALL, Items.BRICK);

            BRICK_REPAIR.put(ModItems.STONE_BRICK, Blocks.STONE_BRICKS);
            BRICK_REPAIR.put(ModItems.BLACKSTONE_BRICK, Blocks.POLISHED_BLACKSTONE_BRICKS);
            BRICK_REPAIR.put(Items.NETHER_BRICK, Blocks.NETHER_BRICKS);
            BRICK_REPAIR.put(ModItems.DEEPSLATE_BRICK, Blocks.DEEPSLATE_BRICKS);
            BRICK_REPAIR.put(ModItems.DEEPSLATE_TILE, Blocks.DEEPSLATE_TILES);
            BRICK_REPAIR.put(Items.BRICK, Blocks.BRICKS);

            BRICK_REPAIR_SLABS.put(ModItems.STONE_BRICK, Blocks.STONE_BRICK_SLAB);
            BRICK_REPAIR_SLABS.put(ModItems.BLACKSTONE_BRICK, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB);
            BRICK_REPAIR_SLABS.put(Items.NETHER_BRICK, Blocks.NETHER_BRICK_SLAB);
            BRICK_REPAIR_SLABS.put(ModItems.DEEPSLATE_BRICK, Blocks.DEEPSLATE_BRICK_SLAB);
            BRICK_REPAIR_SLABS.put(ModItems.DEEPSLATE_TILE, Blocks.DEEPSLATE_TILE_SLAB);
            BRICK_REPAIR_SLABS.put(Items.BRICK, Blocks.BRICK_SLAB);

            BRICK_REPAIR_STAIRS.put(ModItems.STONE_BRICK, Blocks.STONE_BRICK_STAIRS);
            BRICK_REPAIR_STAIRS.put(ModItems.BLACKSTONE_BRICK, Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS);
            BRICK_REPAIR_STAIRS.put(Items.NETHER_BRICK, Blocks.NETHER_BRICK_STAIRS);
            BRICK_REPAIR_STAIRS.put(ModItems.DEEPSLATE_BRICK, Blocks.DEEPSLATE_BRICK_STAIRS);
            BRICK_REPAIR_STAIRS.put(ModItems.DEEPSLATE_TILE, Blocks.DEEPSLATE_TILE_STAIRS);
            BRICK_REPAIR_STAIRS.put(Items.BRICK, Blocks.BRICK_STAIRS);

            BRICK_REPAIR_WALLS.put(ModItems.STONE_BRICK, Blocks.STONE_BRICK_WALL);
            BRICK_REPAIR_WALLS.put(ModItems.BLACKSTONE_BRICK, Blocks.POLISHED_BLACKSTONE_BRICK_WALL);
            BRICK_REPAIR_WALLS.put(Items.NETHER_BRICK, Blocks.NETHER_BRICK_WALL);
            BRICK_REPAIR_WALLS.put(ModItems.DEEPSLATE_BRICK, Blocks.DEEPSLATE_BRICK_WALL);
            BRICK_REPAIR_WALLS.put(ModItems.DEEPSLATE_TILE, Blocks.DEEPSLATE_TILE_WALL);
            BRICK_REPAIR_WALLS.put(Items.BRICK, Blocks.BRICK_WALL);

            STRIPPED_BLOCKS.put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
            STRIPPED_BLOCKS.put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
            STRIPPED_BLOCKS.put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
            STRIPPED_BLOCKS.put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
            STRIPPED_BLOCKS.put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
            STRIPPED_BLOCKS.put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
            STRIPPED_BLOCKS.put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM);
            STRIPPED_BLOCKS.put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM);
            STRIPPED_BLOCKS.put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
            STRIPPED_BLOCKS.put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
            STRIPPED_BLOCKS.put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
            STRIPPED_BLOCKS.put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
            STRIPPED_BLOCKS.put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
            STRIPPED_BLOCKS.put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
            STRIPPED_BLOCKS.put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE);
            STRIPPED_BLOCKS.put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE);

            DROPPED_BARK.put(Blocks.OAK_LOG, ModItems.OAK_BARK);
            DROPPED_BARK.put(Blocks.SPRUCE_LOG, ModItems.SPRUCE_BARK);
            DROPPED_BARK.put(Blocks.JUNGLE_LOG, ModItems.JUNGLE_BARK);
            DROPPED_BARK.put(Blocks.BIRCH_LOG, ModItems.BIRCH_BARK);
            DROPPED_BARK.put(Blocks.DARK_OAK_LOG, ModItems.DARK_OAK_BARK);
            DROPPED_BARK.put(Blocks.ACACIA_LOG, ModItems.ACACIA_BARK);
            DROPPED_BARK.put(Blocks.WARPED_STEM, ModItems.WARPED_SCALES);
            DROPPED_BARK.put(Blocks.CRIMSON_STEM, ModItems.CRIMSON_SCALES);
            DROPPED_BARK.put(Blocks.OAK_WOOD, ModItems.OAK_BARK);
            DROPPED_BARK.put(Blocks.SPRUCE_WOOD, ModItems.SPRUCE_BARK);
            DROPPED_BARK.put(Blocks.JUNGLE_WOOD, ModItems.JUNGLE_BARK);
            DROPPED_BARK.put(Blocks.BIRCH_WOOD, ModItems.BIRCH_BARK);
            DROPPED_BARK.put(Blocks.DARK_OAK_WOOD, ModItems.DARK_OAK_BARK);
            DROPPED_BARK.put(Blocks.ACACIA_WOOD, ModItems.ACACIA_BARK);
            DROPPED_BARK.put(Blocks.WARPED_HYPHAE, ModItems.WARPED_SCALES);
            DROPPED_BARK.put(Blocks.CRIMSON_HYPHAE, ModItems.CRIMSON_SCALES);

            UNSTRIP_LOG.put(ModItems.OAK_BARK, Blocks.OAK_LOG);
            UNSTRIP_LOG.put(ModItems.SPRUCE_BARK, Blocks.SPRUCE_LOG);
            UNSTRIP_LOG.put(ModItems.BIRCH_BARK, Blocks.BIRCH_LOG);
            UNSTRIP_LOG.put(ModItems.JUNGLE_BARK, Blocks.JUNGLE_LOG);
            UNSTRIP_LOG.put(ModItems.DARK_OAK_BARK, Blocks.DARK_OAK_LOG);
            UNSTRIP_LOG.put(ModItems.ACACIA_BARK, Blocks.ACACIA_LOG);
            UNSTRIP_LOG.put(ModItems.WARPED_SCALES, Blocks.WARPED_STEM);
            UNSTRIP_LOG.put(ModItems.CRIMSON_SCALES, Blocks.CRIMSON_STEM);
            UNSTRIP_WOOD.put(ModItems.OAK_BARK, Blocks.OAK_WOOD);
            UNSTRIP_WOOD.put(ModItems.SPRUCE_BARK, Blocks.SPRUCE_WOOD);
            UNSTRIP_WOOD.put(ModItems.BIRCH_BARK, Blocks.BIRCH_WOOD);
            UNSTRIP_WOOD.put(ModItems.JUNGLE_BARK, Blocks.JUNGLE_WOOD);
            UNSTRIP_WOOD.put(ModItems.DARK_OAK_BARK, Blocks.DARK_OAK_WOOD);
            UNSTRIP_WOOD.put(ModItems.ACACIA_BARK, Blocks.ACACIA_WOOD);
            UNSTRIP_WOOD.put(ModItems.WARPED_SCALES, Blocks.WARPED_HYPHAE);
            UNSTRIP_WOOD.put(ModItems.CRIMSON_SCALES, Blocks.CRIMSON_HYPHAE);

            RUSTED_BLOCKS.put(ModBlocks.CUT_IRON, ModBlocks.EXPOSED_CUT_IRON);
            RUSTED_BLOCKS.put(ModBlocks.EXPOSED_CUT_IRON, ModBlocks.WEATHERED_CUT_IRON);
            RUSTED_BLOCKS.put(ModBlocks.WEATHERED_CUT_IRON, ModBlocks.RUSTED_CUT_IRON);
            RUSTED_BLOCKS.put(ModBlocks.CUT_IRON_STAIRS, ModBlocks.EXPOSED_CUT_IRON_STAIRS);
            RUSTED_BLOCKS.put(ModBlocks.EXPOSED_CUT_IRON_STAIRS, ModBlocks.WEATHERED_CUT_IRON_STAIRS);
            RUSTED_BLOCKS.put(ModBlocks.WEATHERED_CUT_IRON_STAIRS, ModBlocks.RUSTED_CUT_IRON_STAIRS);
            RUSTED_BLOCKS.put(ModBlocks.CUT_IRON_SLAB, ModBlocks.EXPOSED_CUT_IRON_SLAB);
            RUSTED_BLOCKS.put(ModBlocks.EXPOSED_CUT_IRON_SLAB, ModBlocks.WEATHERED_CUT_IRON_SLAB);
            RUSTED_BLOCKS.put(ModBlocks.WEATHERED_CUT_IRON_SLAB, ModBlocks.RUSTED_CUT_IRON_SLAB);
            RUSTED_BLOCKS.put(ModBlocks.PLATE_IRON, ModBlocks.EXPOSED_PLATE_IRON);
            RUSTED_BLOCKS.put(ModBlocks.EXPOSED_PLATE_IRON, ModBlocks.WEATHERED_PLATE_IRON);
            RUSTED_BLOCKS.put(ModBlocks.WEATHERED_PLATE_IRON, ModBlocks.RUSTED_PLATE_IRON);
            RUSTED_BLOCKS.put(ModBlocks.PLATE_IRON_STAIRS, ModBlocks.EXPOSED_PLATE_IRON_STAIRS);
            RUSTED_BLOCKS.put(ModBlocks.EXPOSED_PLATE_IRON_STAIRS, ModBlocks.WEATHERED_PLATE_IRON_STAIRS);
            RUSTED_BLOCKS.put(ModBlocks.WEATHERED_PLATE_IRON_STAIRS, ModBlocks.RUSTED_PLATE_IRON_STAIRS);
            RUSTED_BLOCKS.put(ModBlocks.PLATE_IRON_SLAB, ModBlocks.EXPOSED_PLATE_IRON_SLAB);
            RUSTED_BLOCKS.put(ModBlocks.EXPOSED_PLATE_IRON_SLAB, ModBlocks.WEATHERED_PLATE_IRON_SLAB);
            RUSTED_BLOCKS.put(ModBlocks.WEATHERED_PLATE_IRON_SLAB, ModBlocks.RUSTED_PLATE_IRON_SLAB);
            RUSTED_BLOCKS.put(Blocks.IRON_DOOR, ModBlocks.EXPOSED_IRON_DOOR);
            RUSTED_BLOCKS.put(ModBlocks.EXPOSED_IRON_DOOR, ModBlocks.WEATHERED_IRON_DOOR);
            RUSTED_BLOCKS.put(ModBlocks.WEATHERED_IRON_DOOR, ModBlocks.RUSTED_IRON_DOOR);
            RUSTED_BLOCKS.put(Blocks.IRON_TRAPDOOR, ModBlocks.EXPOSED_IRON_TRAPDOOR);
            RUSTED_BLOCKS.put(ModBlocks.EXPOSED_IRON_TRAPDOOR, ModBlocks.WEATHERED_IRON_TRAPDOOR);
            RUSTED_BLOCKS.put(ModBlocks.WEATHERED_IRON_TRAPDOOR, ModBlocks.RUSTED_IRON_TRAPDOOR);
            RUSTED_BLOCKS.put(Blocks.IRON_BARS, ModBlocks.EXPOSED_IRON_BARS);
            RUSTED_BLOCKS.put(ModBlocks.EXPOSED_IRON_BARS, ModBlocks.WEATHERED_IRON_BARS);
            RUSTED_BLOCKS.put(ModBlocks.WEATHERED_IRON_BARS, ModBlocks.RUSTED_IRON_BARS);

            FLOWERY_BLOCKS.put(Blocks.FLOWERING_AZALEA, Blocks.AZALEA);
            FLOWERY_BLOCKS.put(Blocks.FLOWERING_AZALEA_LEAVES, Blocks.AZALEA_LEAVES);
            FLOWERY_BLOCKS.put(ModBlocks.FLOWERING_AZALEA_LEAF_PILE, ModBlocks.AZALEA_LEAF_PILE);

            if (heldItem.getItem() instanceof ShearsItem) {
                if(targetBlock.is(ModTags.FLOWERY)) {
                    Block.popResource(world, fixedPos, new ItemStack(ModItems.AZALEA_FLOWERS));
                    world.playSound(player, targetPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        FLOWERY_BLOCKS.forEach((flowery, shorn) -> {
                            if (targetBlock.is(flowery)) {
                                world.setBlockAndUpdate(targetPos, shorn.withPropertiesOf(targetBlock));
                            }
                        });
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == ModItems.AZALEA_FLOWERS) {
                if(targetBlock.is(ModTags.FLOWERABLE)) {
                    world.playSound(player, targetPos, SoundEvents.FLOWERING_AZALEA_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.shrink(1);
                        FLOWERY_BLOCKS.forEach((flowery, shorn) -> {
                            if (targetBlock.is(shorn)) {
                                world.setBlockAndUpdate(targetPos, flowery.withPropertiesOf(targetBlock));
                            }
                        });
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() instanceof ShovelItem) {
                if(targetBlock.is(Blocks.CAMPFIRE) && targetBlock.getValue(BlockStateProperties.LIT)) {
                    Block.popResource(world, fixedPos , new ItemStack(ModItems.SOOT));
                }
            }
            if (heldItem.getItem() instanceof ShearsItem) {
                if(targetBlock.is(Blocks.FIRE)) {
                    Block.popResource(world, fixedPos, new ItemStack(ModItems.SOOT));
                    world.playSound(player, targetPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    world.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                }
            }
            if (heldItem.getItem() == Items.FLINT_AND_STEEL) {
                if(targetBlock.is(ModBlocks.SOOT)) {
                    world.playSound(player, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        world.setBlockAndUpdate(targetPos, ModBlocks.SOOT.withPropertiesOf(targetBlock).setValue(BlockStateProperties.LIT, true));
                    }
                    return InteractionResult.SUCCESS;
                }
                if(targetBlock.is(ModBlocks.ASH_BLOCK)) {
                    world.playSound(player, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        world.setBlockAndUpdate(targetPos, ModBlocks.ASH_BLOCK.withPropertiesOf(targetBlock).setValue(BlockStateProperties.LIT, true));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == Items.WET_SPONGE) {
                if(targetBlock.is(ModTags.RUSTABLE)) {
                    world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        RUSTED_BLOCKS.forEach((clean, rusty) -> {
                            if (targetBlock.is(clean)) {
                                world.setBlockAndUpdate(targetPos, rusty.withPropertiesOf(targetBlock));
                            }
                        });
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == ModItems.STEEL_WOOL) {
                if(targetBlock.is(ModTags.EXPOSED_IRON)) {
                    world.playSound(player, targetPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        RUSTED_BLOCKS.forEach((clean, rusty) -> {
                            if (targetBlock.is(rusty)) {
                                world.setBlockAndUpdate(targetPos, clean.withPropertiesOf(targetBlock));
                            }
                        });
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() instanceof ShearsItem) {
                if(targetBlock.is(ModTags.MOSSY)) {
                    Block.popResource(world, fixedPos, new ItemStack(ModItems.MOSS_CLUMP));
                    world.playSound(player, targetPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        CLEANED_BLOCKS.forEach((mossy, clean) -> {
                            if (targetBlock.is(mossy)) {
                                world.setBlockAndUpdate(targetPos, clean.withPropertiesOf(targetBlock));
                            }
                        });
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == ModItems.MOSS_CLUMP) {
                if(targetBlock.is(ModTags.MOSSABLE)) {
                    world.playSound(player, targetPos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.shrink(1);
                        CLEANED_BLOCKS.forEach((mossy, clean) -> {
                            if (targetBlock.is(clean)) {
                                world.setBlockAndUpdate(targetPos, mossy.withPropertiesOf(targetBlock));
                            }
                        });
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() instanceof PickaxeItem) {
                if(targetBlock.is(ModTags.CRACKABLE)) {
                    Block.popResource(world, fixedPos, new ItemStack(DROPPED_BRICKS.get(targetBlock.getBlock())));
                    world.playSound(player, targetPos, SoundEvents.STONE_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        CRACKED_BLOCKS.forEach((solid, cracked) -> {
                            if (targetBlock.is(solid)) {
                                world.setBlockAndUpdate(targetPos, cracked.withPropertiesOf(targetBlock));
                            }
                        });
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (BRICK_REPAIR.containsKey(heldItem.getItem()) && targetBlock.is(CRACKED_BLOCKS.get(BRICK_REPAIR.get(heldItem.getItem())))) {
                Block fixedBlock = BRICK_REPAIR.get(heldItem.getItem());
                SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                if(player != null) {
                    if(!player.isCreative())heldItem.shrink(1);
                    world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock));
                }
                return InteractionResult.SUCCESS;
            }
            if (BRICK_REPAIR_SLABS.containsKey(heldItem.getItem()) && targetBlock.is(CRACKED_BLOCKS.get(BRICK_REPAIR_SLABS.get(heldItem.getItem())))) {
                Block fixedBlock = BRICK_REPAIR_SLABS.get(heldItem.getItem());
                SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                if(player != null) {
                    if(!player.isCreative())heldItem.shrink(1);
                    world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock));
                }
                return InteractionResult.SUCCESS;
            }
            if (BRICK_REPAIR_STAIRS.containsKey(heldItem.getItem()) && targetBlock.is(CRACKED_BLOCKS.get(BRICK_REPAIR_STAIRS.get(heldItem.getItem())))) {
                Block fixedBlock = BRICK_REPAIR_STAIRS.get(heldItem.getItem());
                SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                if(player != null) {
                    if(!player.isCreative())heldItem.shrink(1);
                    world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock));
                }
                return InteractionResult.SUCCESS;
            }
            if (BRICK_REPAIR_WALLS.containsKey(heldItem.getItem()) && targetBlock.is(CRACKED_BLOCKS.get(BRICK_REPAIR_WALLS.get(heldItem.getItem())))) {
                Block fixedBlock = BRICK_REPAIR_WALLS.get(heldItem.getItem());
                SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                if(player != null) {
                    if(!player.isCreative())heldItem.shrink(1);
                    world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock));
                }
                return InteractionResult.SUCCESS;
            }
            if (heldItem.getItem() instanceof AxeItem) {
                if(targetBlock.is(ModTags.RAW_LOGS)) {
                    Block.popResource(world, fixedPos, new ItemStack(DROPPED_BARK.get(targetBlock.getBlock())));
                    world.playSound(player, targetPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        STRIPPED_BLOCKS.forEach((raw, stripped) -> {
                            if (targetBlock.is(raw)) {
                                world.setBlockAndUpdate(targetPos, stripped .withPropertiesOf(targetBlock));
                            }
                        });
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (UNSTRIP_LOG.containsKey(heldItem.getItem()) && targetBlock.is(STRIPPED_BLOCKS.get(UNSTRIP_LOG.get(heldItem.getItem())))) {
                Block fixedBlock = UNSTRIP_LOG.get(heldItem.getItem());
                SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                if(player != null) {
                    if(!player.isCreative())heldItem.shrink(1);
                    world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock));
                }
                return InteractionResult.SUCCESS;
            }
            if (UNSTRIP_WOOD.containsKey(heldItem.getItem()) && targetBlock.is(STRIPPED_BLOCKS.get(UNSTRIP_WOOD.get(heldItem.getItem())))) {
                Block fixedBlock = UNSTRIP_WOOD.get(heldItem.getItem());
                SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                if(player != null) {
                    if(!player.isCreative())heldItem.shrink(1);
                    world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock));
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
    }
}



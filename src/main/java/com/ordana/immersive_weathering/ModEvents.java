package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.fabricmc.fabric.impl.client.indigo.renderer.accessor.AccessItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Random;

public class ModEvents {
    private static final HashMap<Block, Block> CLEANED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Block> CRACKED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Item> DROPPED_BRICKS = new HashMap<>();
    private static final HashMap<Item, Block> BRICK_REPAIR = new HashMap<>();
    private static final HashMap<Block, Block> STRIPPED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Item> DROPPED_BARK = new HashMap<>();
    private static final HashMap<Item, Block> UNSTRIP_LOG = new HashMap<>();
    private static final HashMap<Item, Block> UNSTRIP_WOOD = new HashMap<>();

    public static void registerEvents() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

            BlockPos targetPos = hitResult.getBlockPos();
            BlockState targetBlock = world.getBlockState(targetPos);
            ItemStack heldItem = player.getStackInHand(hand);

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

            CRACKED_BLOCKS.put(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
            CRACKED_BLOCKS.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS);
            CRACKED_BLOCKS.put(Blocks.NETHER_BRICKS, Blocks.CRACKED_NETHER_BRICKS);
            CRACKED_BLOCKS.put(Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS);
            CRACKED_BLOCKS.put(Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES);
            CRACKED_BLOCKS.put(Blocks.BRICKS, ModBlocks.CRACKED_BRICKS);

            DROPPED_BRICKS.put(Blocks.STONE_BRICKS, ModItems.STONE_BRICK);
            DROPPED_BRICKS.put(Blocks.POLISHED_BLACKSTONE_BRICKS, ModItems.BLACKSTONE_BRICK);
            DROPPED_BRICKS.put(Blocks.NETHER_BRICKS, Items.NETHER_BRICK);
            DROPPED_BRICKS.put(Blocks.DEEPSLATE_BRICKS, ModItems.DEEPSLATE_BRICK);
            DROPPED_BRICKS.put(Blocks.DEEPSLATE_TILES, ModItems.DEEPSLATE_TILE);
            DROPPED_BRICKS.put(Blocks.BRICKS, Items.BRICK);

            BRICK_REPAIR.put(ModItems.STONE_BRICK, Blocks.STONE_BRICKS);
            BRICK_REPAIR.put(ModItems.BLACKSTONE_BRICK, Blocks.POLISHED_BLACKSTONE_BRICKS);
            BRICK_REPAIR.put(Items.NETHER_BRICK, Blocks.NETHER_BRICKS);
            BRICK_REPAIR.put(ModItems.DEEPSLATE_BRICK, Blocks.DEEPSLATE_BRICKS);
            BRICK_REPAIR.put(ModItems.DEEPSLATE_TILE, Blocks.DEEPSLATE_TILES);
            BRICK_REPAIR.put(Items.BRICK, Blocks.BRICKS);

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

            if (heldItem.isIn(FabricToolTags.SHOVELS)) {
                if(targetBlock.isOf(Blocks.CAMPFIRE) && targetBlock.get(Properties.LIT)) {
                    Block.dropStack(world, targetPos, new ItemStack(ModItems.SOOT));
                }
            }
            if (heldItem.isIn(FabricToolTags.SHOVELS)) {
                if(targetBlock.isOf(Blocks.FIRE)) {
                    Block.dropStack(world, targetPos, new ItemStack(ModItems.SOOT));
                    world.playSound(player, targetPos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    world.setBlockState(targetPos, Blocks.AIR.getDefaultState());
                }
            }
            if (heldItem.getItem() == Items.SHEARS) {
                if(targetBlock.isIn(ImmersiveWeathering.MOSSY)) {
                    Block.dropStack(world, targetPos, new ItemStack(ModItems.MOSS_CLUMP));
                    world.playSound(player, targetPos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.damage(1, new Random(), null);
                        CLEANED_BLOCKS.forEach((mossy, clean) -> {
                            if (targetBlock.isOf(mossy)) {
                                world.setBlockState(targetPos, clean.getStateWithProperties(targetBlock));
                            }
                        });
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == ModItems.MOSS_CLUMP) {
                if(targetBlock.isIn(ImmersiveWeathering.MOSSABLE)) {
                    world.playSound(player, targetPos, SoundEvents.BLOCK_MOSS_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.decrement(1);
                        CLEANED_BLOCKS.forEach((mossy, clean) -> {
                            if (targetBlock.isOf(clean)) {
                                world.setBlockState(targetPos, mossy.getStateWithProperties(targetBlock));
                            }
                        });
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if (heldItem.isIn(FabricToolTags.PICKAXES)) {
                if(targetBlock.isIn(ImmersiveWeathering.CRACKABLE)) {
                    Block.dropStack(world, targetPos, new ItemStack(DROPPED_BRICKS.get(targetBlock.getBlock())));
                    world.playSound(player, targetPos, SoundEvents.BLOCK_STONE_HIT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.damage(1, new Random(), null);
                        CRACKED_BLOCKS.forEach((solid, cracked) -> {
                            if (targetBlock.isOf(solid)) {
                                world.setBlockState(targetPos, cracked.getStateWithProperties(targetBlock));
                            }
                        });
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if (BRICK_REPAIR.containsKey(heldItem.getItem()) && targetBlock.isOf(CRACKED_BLOCKS.get(BRICK_REPAIR.get(heldItem.getItem())))) {
                Block fixedBlock = BRICK_REPAIR.get(heldItem.getItem());
                SoundEvent placeSound = fixedBlock.getSoundGroup(fixedBlock.getDefaultState()).getPlaceSound();
                world.playSound(player, targetPos, placeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                if(player != null) {
                    if(!player.isCreative())heldItem.decrement(1);
                    world.setBlockState(targetPos, fixedBlock.getStateWithProperties(targetBlock));
                }
                return ActionResult.SUCCESS;
            }
            if (heldItem.isIn(FabricToolTags.AXES)) {
                if(targetBlock.isIn(ImmersiveWeathering.RAW_LOGS)) {
                    Block.dropStack(world, targetPos, new ItemStack(DROPPED_BARK.get(targetBlock.getBlock())));
                    world.playSound(player, targetPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.damage(1, new Random(), null);
                        STRIPPED_BLOCKS.forEach((raw, stripped) -> {
                            if (targetBlock.isOf(raw)) {
                                world.setBlockState(targetPos, stripped .getStateWithProperties(targetBlock));
                            }
                        });
                    }
                    return ActionResult.SUCCESS;
                }
            }
            if (UNSTRIP_LOG.containsKey(heldItem.getItem()) && targetBlock.isOf(STRIPPED_BLOCKS.get(UNSTRIP_LOG.get(heldItem.getItem())))) {
                Block fixedBlock = UNSTRIP_LOG.get(heldItem.getItem());
                SoundEvent placeSound = fixedBlock.getSoundGroup(fixedBlock.getDefaultState()).getPlaceSound();
                world.playSound(player, targetPos, placeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                if(player != null) {
                    if(!player.isCreative())heldItem.decrement(1);
                    world.setBlockState(targetPos, fixedBlock.getStateWithProperties(targetBlock));
                }
                return ActionResult.SUCCESS;
            }
            if (UNSTRIP_WOOD.containsKey(heldItem.getItem()) && targetBlock.isOf(STRIPPED_BLOCKS.get(UNSTRIP_WOOD.get(heldItem.getItem())))) {
                Block fixedBlock = UNSTRIP_WOOD.get(heldItem.getItem());
                SoundEvent placeSound = fixedBlock.getSoundGroup(fixedBlock.getDefaultState()).getPlaceSound();
                world.playSound(player, targetPos, placeSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                if(player != null) {
                    if(!player.isCreative())heldItem.decrement(1);
                    world.setBlockState(targetPos, fixedBlock.getStateWithProperties(targetBlock));
                }
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
    }
}



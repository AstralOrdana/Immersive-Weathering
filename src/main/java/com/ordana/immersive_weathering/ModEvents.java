package com.ordana.immersive_weathering;

import com.ordana.immersive_weathering.registry.blocks.ModBlocks;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Random;

public class ModEvents {
    private static final HashMap<Block, Block> CLEANED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Block> CRACKED_BLOCKS = new HashMap<>();
    private static final HashMap<Block, Item> DROPPED_BRICKS = new HashMap<>();
    private static final HashMap<Item, Block> BRICK_REPAIR = new HashMap<>();

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


            if (heldItem.getItem() == Items.SHEARS) {
                if(targetBlock.isIn(ImmersiveWeathering.UNMOSSABLE)) {
                    Block.dropStack(world, targetPos, new ItemStack(Blocks.VINE));
                    world.playSound(player, targetPos, SoundEvents.BLOCK_GROWING_PLANT_CROP, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    if(player != null) {
                        if(!player.isCreative())heldItem.damage(1, new Random(), null);
                        CLEANED_BLOCKS.forEach((mossy, unmossy) -> {
                            if (targetBlock.isOf(mossy)) {
                                world.setBlockState(targetPos, unmossy.getStateWithProperties(targetBlock));
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
            return ActionResult.PASS;
        });
    }
}



package com.ordana.mossier_moss;

import com.ordana.mossier_moss.registry.blocks.ModBlocks;
import com.ordana.mossier_moss.registry.items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Random;

public class MossierMoss implements ModInitializer {

    public static final String MOD_ID = "mossier_moss";
    public static final Tag<Block> UNMOSSABLE = TagFactory.BLOCK.create(new Identifier("mossier_moss", "unmossable"));

    private static final HashMap<Block, Block> CLEANED_BLOCKS = new HashMap<>();

    @Override
    public void onInitialize() {

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {

                    // use these to check for the mossy block :>
                    BlockPos targetPos = hitResult.getBlockPos();
                    BlockState targetBlock = world.getBlockState(targetPos);

                    ItemStack heldItem = player.getMainHandStack();

                    // ur hashmap can also go here i think
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

                    if (heldItem.getItem() == Items.SHEARS) {
                        if(targetBlock.isIn(MossierMoss.UNMOSSABLE)) {
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
            return ActionResult.PASS;
        });

        ModBlocks.registerBlocks();
        ModItems.registerItems();
    }
}

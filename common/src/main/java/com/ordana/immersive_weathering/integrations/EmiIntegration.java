package com.ordana.immersive_weathering.integrations;

import com.google.common.collect.BiMap;
import com.ordana.immersive_weathering.blocks.crackable.Crackable;
import com.ordana.immersive_weathering.blocks.frostable.Frosty;
import com.ordana.immersive_weathering.blocks.mossable.Mossable;
import com.ordana.immersive_weathering.blocks.rustable.Rustable;
import com.ordana.immersive_weathering.blocks.sandy.Sandy;
import com.ordana.immersive_weathering.blocks.snowy.Snowy;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModItems;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

@EmiEntrypoint
public class EmiIntegration implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {

        EmiIngredient pickaxe = EmiStack.of(Items.IRON_PICKAXE);
        EmiIngredient shovel = EmiStack.of(Items.IRON_SHOVEL);
        EmiIngredient hoes = EmiStack.of(Items.IRON_HOE);
        EmiIngredient flint_n_steel = EmiStack.of(Items.FLINT_AND_STEEL);
        EmiIngredient shears = EmiStack.of(Items.SHEARS);
        EmiIngredient azalea = EmiStack.of(ModItems.AZALEA_FLOWERS.get());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/rooted_grass_uprooting"))
                .leftInput(EmiStack.of(ModBlocks.ROOTED_GRASS_BLOCK.get()))
                .rightInput(hoes, true)
                .output(EmiStack.of(Items.HANGING_ROOTS))
                .output(EmiStack.of(Items.GRASS_BLOCK))
                .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/campfire_extinguishing"))
                .leftInput(EmiStack.of(Blocks.CAMPFIRE))
                .rightInput(shovel, true)
                .output(EmiStack.of(ModBlocks.SOOT.get()))
                .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/azalea_leaves_flowering"))
                .leftInput(EmiStack.of(Blocks.AZALEA_LEAVES))
                .rightInput(azalea, false)
                .output(EmiStack.of(Blocks.FLOWERING_AZALEA_LEAVES))
                .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/azalea_bush_flowering"))
                .leftInput(EmiStack.of(Blocks.AZALEA))
                .rightInput(azalea, false)
                .output(EmiStack.of(Blocks.FLOWERING_AZALEA))
                .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/azalea_leaves_shearing"))
                .leftInput(EmiStack.of(Blocks.FLOWERING_AZALEA_LEAVES))
                .rightInput(shears, true)
                .output(EmiStack.of(Blocks.AZALEA_LEAVES))
                .output(EmiStack.of(ModItems.AZALEA_FLOWERS.get()))
                .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/azalea_bush_shearing"))
                .leftInput(EmiStack.of(Blocks.FLOWERING_AZALEA))
                .rightInput(shears, true)
                .output(EmiStack.of(Blocks.AZALEA))
                .output(EmiStack.of(ModItems.AZALEA_FLOWERS.get()))
                .build());

        BiMap<Block, Block> frost = Frosty.UNFROSTY_TO_FROSTY.get();
        for (Block key : frost.keySet()) {
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            EmiStack input = EmiStack.of(frost.get(key));
            EmiStack output = EmiStack.of(key);
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(new ResourceLocation("immersive_weathering", "/block_frosting/" + blockId.getNamespace() + "/" + blockId.getPath()))
                    .leftInput(input)
                    .rightInput(flint_n_steel, true)
                    .output(output)
                    .build());
        }

        BiMap<Block, Block> unfrost = Frosty.FROSTY_TO_UNFROSTY.get();
        for (Block key : unfrost.keySet()) {
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            EmiStack input = EmiStack.of(unfrost.get(key));
            EmiStack output = EmiStack.of(key);
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/block_frosting/" + blockId.getNamespace() + "/" + blockId.getPath()))
                .leftInput(input)
                .rightInput(EmiStack.of(ModItems.FROST_ITEM.get()), false)
                .output(output)
                .build());
        }


        BiMap<Block, Block> cracks = Crackable.CRACK_LEVEL_INCREASES.get();
        for (Block key : cracks.keySet()) {
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            EmiStack input = EmiStack.of(key);
            EmiStack output = EmiStack.of(cracks.get(key));
            if (cracks.get(key) instanceof Crackable cracked) {
                EmiStack brick = EmiStack.of(cracked.getRepairItem(cracks.get(key).defaultBlockState()));
                registry.addRecipe(EmiWorldInteractionRecipe.builder()
                        .id(new ResourceLocation("immersive_weathering", "/brick_cracking/" + blockId.getNamespace() + "/" + blockId.getPath()))
                        .leftInput(input)
                        .rightInput(pickaxe, true)
                        .output(output)
                        .output(brick)
                        .build());
            }
            if (key instanceof Crackable cracked) {
                EmiStack brick = EmiStack.of(cracked.getRepairItem(key.defaultBlockState()));
                registry.addRecipe(EmiWorldInteractionRecipe.builder()
                        .id(new ResourceLocation("immersive_weathering", "/brick_repair/" + blockId.getNamespace() + "/" + blockId.getPath()))
                        .leftInput(output)
                        .rightInput(brick, false)
                        .output(input)
                        .build());
            }
        }

        BiMap<Block, Block> moss = Mossable.MOSS_LEVEL_INCREASES.get();
        for (Block key : moss.keySet()) {
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            EmiStack input = EmiStack.of(key);
            EmiStack output = EmiStack.of(moss.get(key));
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(new ResourceLocation("immersive_weathering", "/mossing/" + blockId.getNamespace() + "/" + blockId.getPath()))
                    .leftInput(input)
                    .rightInput(EmiStack.of(ModItems.MOSS_CLUMP.get()), false)
                    .output(output)
                    .build());
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(new ResourceLocation("immersive_weathering", "/gold_mossing/" + blockId.getNamespace() + "/" + blockId.getPath()))
                    .leftInput(input)
                    .rightInput(EmiStack.of(ModItems.ENCHANTED_GOLDEN_MOSS_CLUMP.get()), true)
                    .output(output)
                    .build());
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(new ResourceLocation("immersive_weathering", "/moss_burn/" + blockId.getNamespace() + "/" + blockId.getPath()))
                    .leftInput(output)
                    .rightInput(flint_n_steel, true)
                    .output(input)
                    .build());
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(new ResourceLocation("immersive_weathering", "/moss_shear/" + blockId.getNamespace() + "/" + blockId.getPath()))
                    .leftInput(output)
                    .rightInput(shears, true)
                    .output(input)
                    .output(EmiStack.of(ModItems.MOSS_CLUMP.get()))
                    .build());
        }

        BiMap<Block, Block> sandy = Sandy.NORMAL_TO_SANDY.get();
        for (Block key : sandy.keySet()) {
            EmiStack input = EmiStack.of(key);
            EmiStack output = EmiStack.of(sandy.get(key));
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(new ResourceLocation("immersive_weathering", "/block_sanding/" + blockId.getNamespace() + "/" + blockId.getPath()))
                    .leftInput(input)
                    .rightInput(EmiStack.of(ModBlocks.SAND_LAYER_BLOCK.get()), false)
                    .output(output)
                    .build());
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(new ResourceLocation("immersive_weathering", "/unsanding/" + blockId.getNamespace() + "/" + blockId.getPath()))
                    .leftInput(output)
                    .rightInput(shovel, true)
                    .output(input)
                    .output(EmiStack.of(ModBlocks.SAND_LAYER_BLOCK.get()))
                    .build());
        }

        BiMap<Block, Block> snowy = Snowy.NORMAL_TO_SNOWY.get();
        for (Block key : snowy.keySet()) {
            EmiStack input = EmiStack.of(key);
            EmiStack output = EmiStack.of(snowy.get(key));
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(new ResourceLocation("immersive_weathering", "/block_snowing/" + blockId.getNamespace() + "/" + blockId.getPath()))
                    .leftInput(input)
                    .rightInput(EmiStack.of(Items.SNOWBALL), false)
                    .output(output)
                    .build());
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(new ResourceLocation("immersive_weathering", "/unsnowing/" + blockId.getNamespace() + "/" + blockId.getPath()))
                    .leftInput(output)
                    .rightInput(shovel, true)
                    .output(input)
                    .output(EmiStack.of(Items.SNOWBALL))
                    .build());
        }

        BiMap<Block, Block> rust = Rustable.RUST_LEVEL_INCREASES.get();
        for (Block key : rust.keySet()) {
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            EmiStack input = EmiStack.of(key);
            EmiStack output = EmiStack.of(rust.get(key));
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(new ResourceLocation("immersive_weathering", "/sponge_rusting/" + blockId.getNamespace() + "/" + blockId.getPath()))
                    .leftInput(input)
                    .rightInput(EmiStack.of(Items.WET_SPONGE), true)
                    .output(output)
                    .build());
        }
    }
}

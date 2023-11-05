package com.ordana.immersive_weathering.integration.fabric;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;

public class EmiIntegration implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {

    }

    /*

    @Override
    public void register(EmiRegistry registry) {


        EmiIngredient pickaxe = EmiStack.of(Items.IRON_PICKAXE);
        EmiIngredient shovel = EmiStack.of(Items.IRON_SHOVEL);
        EmiIngredient hoes = EmiStack.of(Items.IRON_HOE);
        EmiIngredient flint_n_steel = EmiStack.of(Items.FLINT_AND_STEEL);
        EmiIngredient shears = EmiStack.of(Items.SHEARS);
        EmiIngredient bottle = EmiStack.of(Items.GLASS_BOTTLE);
        EmiIngredient slime = EmiStack.of(Items.SLIME_BALL);
        EmiIngredient flint = EmiStack.of(Items.FLINT);
        EmiIngredient azalea = EmiStack.of(ModItems.AZALEA_FLOWERS.get());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/rooted_grass_uprooting"))
                .leftInput(EmiStack.of(ModBlocks.ROOTED_GRASS_BLOCK.get()))
                .rightInput(hoes, true)
                .output(EmiStack.of(Items.HANGING_ROOTS))
                .output(EmiStack.of(Items.GRASS_BLOCK))
                .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/pond_water"))
                //.leftInput(EmiStack.of(ModBlocks.FLUVISOL.get()))
                .rightInput(bottle, false)
                //.output(EmiStack.of(ModBlocks.FLUVISOL.get()))
                .output(EmiStack.of(ModItems.POND_WATER.get()))
                .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/pond_water_silt"))
                .leftInput(EmiStack.of(ModBlocks.SILT.get()))
                .rightInput(bottle, false)
                .output(EmiStack.of(ModBlocks.SILT.get()))
                .output(EmiStack.of(ModItems.POND_WATER.get()))
                .build());


        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/piston_sliming"))
                .leftInput(EmiStack.of(Blocks.PISTON))
                .rightInput(slime, false)
                .output(EmiStack.of(Items.STICKY_PISTON))
                .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/piston_desliming"))
                .leftInput(EmiStack.of(Blocks.STICKY_PISTON))
                .rightInput(shears, true)
                .output(EmiStack.of(Items.SLIME_BALL))
                .output(EmiStack.of(Items.PISTON))
                .build());


        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/dirt_flinting"))
                .leftInput(EmiStack.of(Blocks.DIRT))
                .rightInput(flint, false)
                .output(EmiStack.of(Items.COARSE_DIRT))
                .build());


        registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/campfire_extinguishing"))
                .leftInput(EmiStack.of(Blocks.CAMPFIRE))
                .rightInput(shovel, true)
                //.output(EmiStack.of(ModBlocks.ASH_LAYER_BLOCK.get()))
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
            ResourceLocation blockId = Registry.ITEM.getKey(key.asItem());
            EmiStack input = EmiStack.of(frost.get(key));
            EmiStack output = EmiStack.of(key);
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(new ResourceLocation("immersive_weathering", "/block_frosting/" + blockId.getNamespace() + "/" + blockId.getPath()))
                    .leftInput(input)
                    .rightInput(flint_n_steel, true)
                    .output(output)
                    .build());
        }

        BiMap<Block, Block> cracks = Crackable.CRACK_LEVEL_INCREASES.get();
        for (Block key : cracks.keySet()) {
            ResourceLocation blockId = Registry.ITEM.getKey(key.asItem());
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
            ResourceLocation blockId = Registry.ITEM.getKey(key.asItem());
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
            ResourceLocation blockId = Registry.ITEM.getKey(key.asItem());
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
            ResourceLocation blockId = Registry.ITEM.getKey(key.asItem());
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
            ResourceLocation blockId = Registry.ITEM.getKey(key.asItem());
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

     */
}

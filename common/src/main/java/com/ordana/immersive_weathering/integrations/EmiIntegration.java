package com.ordana.immersive_weathering.integrations;

import com.google.common.collect.BiMap;
import com.ordana.immersive_weathering.blocks.cracked.Crackable;
import com.ordana.immersive_weathering.blocks.frosted.Frosty;
import com.ordana.immersive_weathering.blocks.mossy.Mossable;
import com.ordana.immersive_weathering.blocks.rusty.Rustable;
import com.ordana.immersive_weathering.blocks.sandy.Sandy;
import com.ordana.immersive_weathering.blocks.snowy.Snowy;
import com.ordana.immersive_weathering.reg.ModBlocks;
import com.ordana.immersive_weathering.reg.ModItems;
import com.ordana.immersive_weathering.reg.ModTags;
import com.ordana.immersive_weathering.util.WeatheringHelper;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;

import java.util.Map;

@EmiEntrypoint
public class EmiIntegration implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {

        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/oak_log"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/birch_log"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/spruce_log"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/jungle_log"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/dark_oak_log"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/acacia_log"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/mangrove_log"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/cherry_log"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/bamboo_block"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/warped_stem"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/crimson_stem"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/oak_wood"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/birch_wood"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/spruce_wood"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/jungle_wood"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/dark_oak_wood"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/acacia_wood"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/mangrove_wood"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/cherry_wood"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/warped_hyphae"));
        registry.removeRecipes(new ResourceLocation("emi", "/world/stripping/minecraft/crimson_hyphae"));

        EmiIngredient pickaxes = EmiIngredient.of(ItemTags.PICKAXES);
        EmiIngredient axes = EmiIngredient.of(ItemTags.AXES);
        EmiIngredient shovels = EmiIngredient.of(ItemTags.SHOVELS);
        EmiIngredient hoes = EmiIngredient.of(ItemTags.HOES);
        EmiIngredient shears = EmiStack.of(Items.SHEARS);
        EmiStack azalea = EmiStack.of(ModItems.AZALEA_FLOWERS.get());
        EmiIngredient flint_n_steel = EmiStack.of(Items.FLINT_AND_STEEL);

        EmiStack water = EmiStack.of(Fluids.WATER);
        EmiStack lava = EmiStack.of(Fluids.LAVA);

        var style = Style.EMPTY.applyFormats(ChatFormatting.GREEN);
        EmiStack waterCatalyst = water.copy().setRemainder(water);
        EmiStack lavaCatalyst = lava.copy().setRemainder(lava);



        //OTHER
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
            .rightInput(shovels, true)
            .output(EmiStack.of(ModBlocks.SOOT.get()))
            .build());

        //TILLING
        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/clay_tilling"))
            .leftInput(EmiIngredient.of(Ingredient.of(ModBlocks.EARTHEN_CLAY.get(), ModBlocks.GRASSY_EARTHEN_CLAY.get())))
            .rightInput(hoes, true)
            .output(EmiStack.of(ModBlocks.EARTHEN_CLAY_FARMLAND.get()))
            .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/sandy_tilling"))
            .leftInput(EmiIngredient.of(Ingredient.of(ModBlocks.SANDY_DIRT.get(), ModBlocks.GRASSY_SANDY_DIRT.get())))
            .rightInput(hoes, true)
            .output(EmiStack.of(ModBlocks.SANDY_FARMLAND.get()))
            .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/silt_tilling"))
            .leftInput(EmiIngredient.of(Ingredient.of(ModBlocks.SILT.get(), ModBlocks.GRASSY_SILT.get())))
            .rightInput(hoes, true)
            .output(EmiStack.of(ModBlocks.SILTY_FARMLAND.get()))
            .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/loam_tilling"))
            .leftInput(EmiStack.of(ModBlocks.LOAM.get()))
            .rightInput(hoes, true)
            .output(EmiStack.of(ModBlocks.LOAMY_FARMLAND.get()))
            .build());

        //FLOWERING
        BiMap<Block, Block> flowering = WeatheringHelper.FLOWERY_BLOCKS.get();
        for (Block key : flowering.keySet()) {
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", key.getDescriptionId()))
                .leftInput(EmiStack.of(flowering.get(key)))
                .rightInput(azalea, false)
                .output(EmiStack.of(key))
                .build());
        }
        BiMap<Block, Block> unflowering = WeatheringHelper.FLOWERY_BLOCKS.get().inverse();
        for (Block key : unflowering.keySet()) {
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", key.getDescriptionId()))
                .leftInput(EmiStack.of(unflowering.get(key)))
                .rightInput(shears, true)
                .output(azalea)
                .output(EmiStack.of(key))
                .build());
        }

        //FROSTING
        BiMap<Block, Block> frost = Frosty.UNFROSTY_TO_FROSTY.get();
        for (Block key : frost.keySet()) {
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            EmiStack input = EmiStack.of(frost.get(key));
            EmiStack output = EmiStack.of(key);
            if (!key.defaultBlockState().is(Blocks.AIR)) registry.addRecipe(EmiWorldInteractionRecipe.builder()
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
            if (!key.defaultBlockState().is(ModBlocks.FROST.get())) registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/block_frosting/" + blockId.getNamespace() + "/" + blockId.getPath()))
                .leftInput(input)
                .rightInput(EmiStack.of(ModItems.FROST_ITEM.get()), false)
                .output(output)
                .build());
        }

        //CHARRING
        Map<TagKey<Block>, Block> charred = WeatheringHelper.WOOD_TO_CHARRED.get();
        for (TagKey<Block> key : charred.keySet()) {
            EmiIngredient unburnt_block = EmiIngredient.of(key);
            EmiStack charred_block = EmiStack.of(charred.get(key));
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/block_charring/" + key.location().getNamespace() + "/" + key.location().getPath()))
                .leftInput(unburnt_block)
                .rightInput(EmiStack.of(ModItems.FIRE.get()), true)
                .output(charred_block)
                .build());
        }

        //BRICKING
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
                    .rightInput(pickaxes, true)
                    .output(output)
                    .output(brick)
                    .build());
            }
        }
        for (Block key : cracks.keySet()) {
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            EmiStack input = EmiStack.of(key);
            EmiStack output = EmiStack.of(cracks.get(key));
            if (key instanceof Crackable cracked) {
                var brick = cracked.getRepairItem(key.defaultBlockState());
                registry.addRecipe(EmiWorldInteractionRecipe.builder()
                    .id(new ResourceLocation("immersive_weathering", "/brick_repair/" + blockId.getNamespace() + "/" + blockId.getPath()))
                    .leftInput(output)
                    .rightInput(EmiIngredient.of(Ingredient.of(brick, ModItems.MORTAR.get())), false)
                    .output(input)
                    .build());
            }
        }

        //MOSSING
        BiMap<Block, Block> moss = Mossable.MOSS_LEVEL_INCREASES.get();
        for (Block key : moss.keySet()) {
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            EmiStack input = EmiStack.of(key);
            EmiStack output = EmiStack.of(moss.get(key));
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/moss_shear/" + blockId.getNamespace() + "/" + blockId.getPath()))
                .leftInput(output)
                .rightInput(shears, true)
                .output(input)
                .output(EmiStack.of(ModItems.MOSS_CLUMP.get()))
                .build());
        }
        for (Block key : moss.keySet()) {
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            EmiStack input = EmiStack.of(key);
            EmiStack output = EmiStack.of(moss.get(key));
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/moss_burn/" + blockId.getNamespace() + "/" + blockId.getPath()))
                .leftInput(output)
                .rightInput(flint_n_steel, true)
                .output(input)
                .build());
        }
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
        }

        //SANDING
        BiMap<Block, Block> sandy = Sandy.NORMAL_TO_SANDY.get();
        for (Block key : sandy.keySet()) {
            EmiStack input = EmiStack.of(key);
            EmiStack output = EmiStack.of(sandy.get(key));
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/unsanding/" + blockId.getNamespace() + "/" + blockId.getPath()))
                .leftInput(output)
                .rightInput(shovels, true)
                .output(input)
                .output(EmiStack.of(ModBlocks.SAND_LAYER_BLOCK.get()))
                .build());
        }
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
        }

        //SNOWING
        BiMap<Block, Block> snowy = Snowy.NORMAL_TO_SNOWY.get();
        for (Block key : snowy.keySet()) {
            EmiStack input = EmiStack.of(key);
            EmiStack output = EmiStack.of(snowy.get(key));
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/unsnowing/" + blockId.getNamespace() + "/" + blockId.getPath()))
                .leftInput(output)
                .rightInput(shovels, true)
                .output(input)
                .output(EmiStack.of(Items.SNOWBALL))
                .build());
        }
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
        }

        //RUSTING
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

        //WOODING
        BiMap<Block, Block> log = WeatheringHelper.RAW_TO_STRIPPED.get();
        for (Block key : log.keySet()) {
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            EmiStack raw_log = EmiStack.of(key);
            EmiStack stripped_log = EmiStack.of(log.get(key));
            Item barkToStrip = WeatheringHelper.getBarkToStrip(key.defaultBlockState());
            if(barkToStrip == null){
                //error;
                int aa = 1;
            }
            EmiStack bark = EmiStack.of(barkToStrip);
            if (bark == null) bark = EmiStack.of(WeatheringHelper.getBarkForStrippedLog(log.get(key).defaultBlockState()).get().getFirst());

            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/block_stripping/" + blockId.getNamespace() + "/" + blockId.getPath()))
                .leftInput(raw_log)
                .rightInput(axes, true)
                .output(bark)
                .output(stripped_log)
                .build());
        }
        for (Block key : log.keySet()) {
            ResourceLocation blockId = BuiltInRegistries.ITEM.getKey(key.asItem());
            EmiStack raw_log = EmiStack.of(key);
            EmiStack stripped_log = EmiStack.of(log.get(key));
            Item barkToStrip = WeatheringHelper.getBarkToStrip(key.defaultBlockState());
            if(barkToStrip == null){
                //error;
                int aa = 1;
            }
            EmiStack bark = EmiStack.of(barkToStrip);
            if (bark == null) bark = EmiStack.of(WeatheringHelper.getBarkForStrippedLog(log.get(key).defaultBlockState()).get().getFirst());

            registry.addRecipe(EmiWorldInteractionRecipe.builder()
                .id(new ResourceLocation("immersive_weathering", "/block_unstripping/" + blockId.getNamespace() + "/" + blockId.getPath()))
                .leftInput(stripped_log)
                .rightInput(bark, false)
                .output(raw_log)
                .build());
        }



        //FLUID GENERATORS
        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/andesite"))
            .leftInput(waterCatalyst)
            .rightInput(lavaCatalyst, true)
            .rightInput(EmiStack.of(Blocks.DIORITE), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.adjacent").setStyle(style)))
            .output(EmiStack.of(Blocks.ANDESITE))
            .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/diorite"))
            .leftInput(waterCatalyst)
            .rightInput(lavaCatalyst, true)
            .rightInput(EmiIngredient.of(ModTags.QUARTZ_BLOCKS), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.adjacent").setStyle(style)))
            .output(EmiStack.of(Blocks.DIORITE))
            .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/granite"))
            .leftInput(waterCatalyst)
            .rightInput(lavaCatalyst, true)
            .rightInput(EmiStack.of(Blocks.DIORITE), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.adjacent").setStyle(style)))
            .rightInput(EmiIngredient.of(ModTags.QUARTZ_BLOCKS), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.adjacent").setStyle(style)))
            .output(EmiStack.of(Blocks.GRANITE))
            .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/basalt_below"))
            .leftInput(lavaCatalyst)
            .rightInput(EmiStack.of(Blocks.BASALT), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.below").setStyle(style)))
            .output(EmiStack.of(Blocks.BASALT))
            .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/blackstone"))
            .leftInput(lavaCatalyst)
            .rightInput(EmiStack.of(Blocks.MAGMA_BLOCK), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.adjacent").setStyle(style)))
            .rightInput(EmiStack.of(Blocks.BLUE_ICE), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.adjacent").setStyle(style)))
            .output(EmiStack.of(Blocks.BLACKSTONE))
            .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/calcite"))
            .leftInput(lavaCatalyst)
            .rightInput(EmiStack.of(Blocks.MAGMA_BLOCK), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.adjacent").setStyle(style)))
            .rightInput(EmiStack.of(Blocks.BLUE_ICE), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.adjacent").setStyle(style)))
            .rightInput(EmiStack.of(Blocks.BONE_BLOCK), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.below").setStyle(style)))
            .output(EmiStack.of(Blocks.CALCITE))
            .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/crying_obsidian"))
            .leftInput(lavaCatalyst)
            .rightInput(waterCatalyst, true)
            .rightInput(EmiStack.of(Blocks.SOUL_FIRE), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.adjacent").setStyle(style)))
            .output(EmiStack.of(Blocks.CRYING_OBSIDIAN))
            .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/magma_block"))
            .leftInput(lavaCatalyst)
            .rightInput(waterCatalyst, true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.below").setStyle(style)).appendTooltip(
                Component.translatable("tooltip.immersive_weathering.rising").setStyle(style)))
            .output(EmiStack.of(Blocks.MAGMA_BLOCK))
            .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/tuff"))
            .leftInput(lavaCatalyst)
            .rightInput(waterCatalyst, true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.below").setStyle(style)).appendTooltip(
                Component.translatable("tooltip.immersive_weathering.sinking").setStyle(style)))
            .output(EmiStack.of(Blocks.TUFF))
            .build());

        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new ResourceLocation("immersive_weathering", "/smooth_basalt"))
            .leftInput(lavaCatalyst)
            .rightInput(EmiStack.of(Blocks.BLUE_ICE), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.adjacent").setStyle(style)))
            .rightInput(EmiStack.of(Blocks.SOUL_SOIL), true, s -> s.appendTooltip(
                Component.translatable("tooltip.immersive_weathering.below").setStyle(style)))
            .output(EmiStack.of(Blocks.SMOOTH_BASALT))
            .build());

    }
}

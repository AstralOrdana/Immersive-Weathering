package com.ordana.immersive_weathering.fabric;


import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ModEvents {


    //TODO: add composter stuff


    public static void registerEvents(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {


            BlockPos targetPos = hitResult.getBlockPos();
            BlockPos fixedPos = targetPos.relative(hitResult.getDirection());
            BlockState targetBlock = world.getBlockState(targetPos);
            ItemStack heldItem = player.getItemInHand(hand);

            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.cauldronWashing) {
                if (heldItem.getItem() == Items.DIRT) {
                    if (targetBlock.is(Blocks.WATER_CAULDRON) && (targetBlock.getValue(LayeredCauldronBlock.LEVEL) >= 3)) {
                        Block.popResource(world, fixedPos, new ItemStack(Items.CLAY));
                        if (world.random.nextFloat() < 0.5f) {
                            Block.popResource(world, fixedPos, new ItemStack(Items.GRAVEL));
                        }
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.shrink(1);
                            world.setBlockAndUpdate(targetPos, Blocks.CAULDRON.withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                } else if (heldItem.getItem() == Items.GRAVEL) {
                    if (targetBlock.is(Blocks.WATER_CAULDRON) && (targetBlock.getValue(LayeredCauldronBlock.LEVEL) >= 3)) {
                        Block.popResource(world, fixedPos, new ItemStack(Items.FLINT));
                        if (world.random.nextFloat() < 0.2f) {
                            Block.popResource(world, fixedPos, new ItemStack(Items.IRON_NUGGET));
                        }
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.shrink(1);
                            world.setBlockAndUpdate(targetPos, Blocks.CAULDRON.withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                } else if (heldItem.getItem() == Items.CLAY) {
                    if (targetBlock.is(Blocks.WATER_CAULDRON) && (targetBlock.getValue(LayeredCauldronBlock.LEVEL) >= 3)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModItems.SILT));
                        if (world.random.nextFloat() < 0.2f) {
                            Block.popResource(world, fixedPos, new ItemStack(Items.SAND));
                        }
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SPLASH, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.shrink(1);
                            world.setBlockAndUpdate(targetPos, Blocks.CAULDRON.withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }


            if (heldItem.getItem() instanceof ShearsItem) {
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.soilShearing) {
                    if (targetBlock.hasProperty(ModGrassBlock.FERTILE) && targetBlock.getValue(ModGrassBlock.FERTILE) && targetBlock.hasProperty(SnowyDirtBlock.SNOWY) && !targetBlock.getValue(SnowyDirtBlock.SNOWY)) {
                        world.playSound(player, targetPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, new BlockParticleOption(ParticleTypes.BLOCK, targetBlock), UniformInt.of(3,5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, targetBlock.getBlock().defaultBlockState().setValue(ModGrassBlock.FERTILE, false));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }

                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.azaleaShearing) {
                    if (targetBlock.is(ModTags.FLOWERY)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModItems.AZALEA_FLOWERS));
                        world.playSound(player, targetPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.AZALEA_FLOWER, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, FLOWERY_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.mossShearing) {
                    if (targetBlock.is(ModTags.MOSSY)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModItems.MOSS_CLUMP));
                        world.playSound(player, targetPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.MOSS, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, CLEANED_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }

            if (heldItem.getItem() == Items.LAVA_BUCKET) {
                if (targetBlock.is(ModBlocks.NULCH_BLOCK) && (!targetBlock.getValue(NulchBlock.MOLTEN))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.LAVA_BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.LAVA, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(NulchBlock.MOLTEN, Boolean.TRUE));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if (heldItem.getItem() == Items.BUCKET) {
                if (targetBlock.is(ModBlocks.NULCH_BLOCK) && (targetBlock.getValue(NulchBlock.MOLTEN))) {
                    player.awardStat(Stats.ITEM_USED.get(Items.BUCKET));
                    world.playSound(player, targetPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.LAVA, UniformInt.of(3, 5));
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        ItemStack itemStack2 = ItemUtils.createFilledResult(heldItem, player, Items.LAVA_BUCKET.getDefaultInstance());
                        player.setItemInHand(hand, itemStack2);
                        world.setBlockAndUpdate(targetPos, targetBlock.setValue(NulchBlock.MOLTEN, Boolean.FALSE));
                    }
                    return InteractionResult.SUCCESS;
                }
            }

            if (heldItem.getItem() instanceof ShovelItem) {
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.shovelExtinguishing) {
                    if (targetBlock.is(Blocks.CAMPFIRE) && targetBlock.getValue(BlockStateProperties.LIT)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModItems.ASH_LAYER_BLOCK));
                        world.playSound(player, targetPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SOOT, UniformInt.of(3, 5));
                        if(player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            world.setBlockAndUpdate(targetPos, targetBlock.getBlock().withPropertiesOf(targetBlock).setValue(CampfireBlock.LIT, false));
                        }
                        return InteractionResult.SUCCESS;
                    }
                    if (targetBlock.is(Blocks.FIRE)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModItems.ASH_LAYER_BLOCK));
                        world.playSound(player, targetPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SOOT, UniformInt.of(3, 5));
                        if(player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            world.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                        }
                        return InteractionResult.SUCCESS;
                    }
                    if ((targetBlock.getBlock() instanceof CharredPillarBlock || targetBlock.getBlock() instanceof CharredBlock || targetBlock.getBlock() instanceof CharredStairsBlock || targetBlock.getBlock() instanceof CharredSlabBlock || targetBlock.getBlock() instanceof CharredFenceBlock || targetBlock.getBlock() instanceof CharredFenceGateBlock) && targetBlock.getValue(CharredBlock.SMOLDERING)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModItems.ASH_LAYER_BLOCK));
                        world.playSound(player, targetPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SOOT, UniformInt.of(3, 5));
                        if(player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            world.setBlockAndUpdate(targetPos, targetBlock.getBlock().withPropertiesOf(targetBlock).setValue(CharredBlock.SMOLDERING, false));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if ((targetBlock.is(ModBlocks.HUMUS) || targetBlock.is(ModBlocks.FLUVISOL) || targetBlock.is(ModBlocks.VERTISOL) || targetBlock.is(ModBlocks.CRYOSOL)) && !targetBlock.getValue(BlockStateProperties.SNOWY)) {
                    world.playSound(player, targetPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                        world.setBlockAndUpdate(targetPos, Blocks.DIRT_PATH.defaultBlockState());
                    }
                    return InteractionResult.SUCCESS;
                }

            }
            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.azaleaShearing) {
                if (heldItem.getItem() == ModItems.AZALEA_FLOWERS) {
                    if (targetBlock.is(ModTags.FLOWERABLE)) {
                        world.playSound(player, targetPos, SoundEvents.FLOWERING_AZALEA_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.AZALEA_FLOWER, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.shrink(1);
                            FLOWERY_BLOCKS.forEach((flowery, shorn) -> {
                                if (targetBlock.is(shorn)) {
                                    world.setBlockAndUpdate(targetPos, flowery.withPropertiesOf(targetBlock));
                                }
                            });
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.mossShearing) {
                if (heldItem.getItem() == ModItems.MOSS_CLUMP) {
                    if (targetBlock.is(ModTags.MOSSABLE)) {
                        world.playSound(player, targetPos, SoundEvents.MOSS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.shrink(1);
                            CLEANED_BLOCKS.forEach((mossy, clean) -> {
                                if (targetBlock.is(clean)) {
                                    world.setBlockAndUpdate(targetPos, mossy.withPropertiesOf(targetBlock));
                                }
                            });
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if (heldItem.getItem() == Items.FLINT_AND_STEEL) {
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.charredBlockIgniting) {
                    if ((targetBlock.getBlock() instanceof CharredPillarBlock || targetBlock.getBlock() instanceof CharredBlock || targetBlock.getBlock() instanceof CharredStairsBlock || targetBlock.getBlock() instanceof CharredSlabBlock || targetBlock.getBlock() instanceof CharredFenceBlock || targetBlock.getBlock() instanceof CharredFenceGateBlock) && !targetBlock.getValue(CharredBlock.SMOLDERING)) {
                        world.playSound(player, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.EMBERSPARK, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, targetBlock.getBlock().withPropertiesOf(targetBlock).setValue(CharredBlock.SMOLDERING, true));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.mossBurning) {
                    if (targetBlock.is(ModTags.MOSSY)) {
                        world.playSound(player, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMALL_FLAME, UniformInt.of(3, 5));
                        ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMOKE, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, CLEANED_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock).setValue(Weatherable.WEATHERABLE, Weatherable.WeatheringState.STABLE));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }

            }

            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.spongeRusting) {
                if (heldItem.getItem() == Items.WET_SPONGE) {
                    if (targetBlock.is(ModTags.RUSTABLE)) {
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SCRAPE_RUST, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            world.setBlockAndUpdate(targetPos, RUSTED_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if (heldItem.getItem() == ModItems.STEEL_WOOL) {
                if(targetBlock.is(ModTags.COPPER)) {
                    world.playSound(player, targetPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SCRAPE, UniformInt.of(3,5));
                    if(player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        WeatheringCopper.getPrevious(targetBlock).ifPresent(o-> world.setBlockAndUpdate(targetPos, o));
                    }
                    return InteractionResult.SUCCESS;
                }
                if(targetBlock.is(ModTags.EXPOSED_IRON) || targetBlock.is(ModTags.WEATHERED_IRON) || targetBlock.is(ModTags.RUSTED_IRON)) {
                    world.playSound(player, targetPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SCRAPE_RUST, UniformInt.of(3,5));
                    if(player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        RUSTED_BLOCKS.forEach((clean, rusty) -> {
                            if (targetBlock.is(rusty)) {
                                world.setBlockAndUpdate(targetPos, clean.withPropertiesOf(targetBlock));
                            }
                        });
                    }
                    return InteractionResult.SUCCESS;
                }
                if(targetBlock.is(ModTags.WAXED_BLOCKS)) {
                    world.playSound(player, targetPos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0f, 1.0f);
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.WAX_OFF, UniformInt.of(3,5));
                    if(player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if(!player.isCreative())heldItem.hurt(1, new Random(), null);
                        world.setBlockAndUpdate(targetPos, UNWAXED_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.pickaxeCracking) {

            }
            if (heldItem.getItem() instanceof AxeItem) {
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.axeStripping) {
                    if (targetBlock.is(ModTags.RAW_LOGS)) {
                        Block.popResource(world, fixedPos, new ItemStack(DROPPED_BARK.get(targetBlock.getBlock())));
                        world.playSound(player, targetPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0f, 1.0f);
                        var barkParticle = WeatheringHelper.getBarkParticle(targetBlock).orElse(null);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, barkParticle, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, STRIPPED_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.axeScraping) {
                    if (targetBlock.is(ModTags.WEATHERED_IRON) || targetBlock.is(ModTags.RUSTED_IRON)) {
                        world.playSound(player, targetPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        world.playSound(player, targetPos, SoundEvents.SHIELD_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SCRAPE_RUST, UniformInt.of(3, 5));
                        ModParticles.spawnParticlesOnBlockFaces(world, targetPos, ParticleTypes.SMOKE, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        }
                        return InteractionResult.SUCCESS;
                    }
                    else if (targetBlock.is(ModTags.EXPOSED_IRON)) {
                        world.playSound(player, targetPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SCRAPE_RUST, UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            RUSTED_BLOCKS.forEach((clean, rusty) -> {
                                if (targetBlock.is(rusty)) {
                                    world.setBlockAndUpdate(targetPos, clean.withPropertiesOf(targetBlock));
                                }
                            });
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.axeStripping) {
                if (UNSTRIP_LOG.containsKey(heldItem.getItem()) && targetBlock.is(STRIPPED_BLOCKS.get(UNSTRIP_LOG.get(heldItem.getItem())))) {
                    Block fixedBlock = UNSTRIP_LOG.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock));
                    }
                    return InteractionResult.SUCCESS;
                }
                if (UNSTRIP_WOOD.containsKey(heldItem.getItem()) && targetBlock.is(STRIPPED_BLOCKS.get(UNSTRIP_WOOD.get(heldItem.getItem())))) {
                    Block fixedBlock = UNSTRIP_WOOD.get(heldItem.getItem());
                    SoundEvent placeSound = fixedBlock.getSoundType(fixedBlock.defaultBlockState()).getPlaceSound();
                    world.playSound(player, targetPos, placeSound, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.shrink(1);
                        world.setBlockAndUpdate(targetPos, fixedBlock.withPropertiesOf(targetBlock));
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        });
    }
}



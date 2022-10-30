package com.ordana.immersive_weathering.fabric;


@Deprecated(forRemoval = true)
public class ModEvents {

    //TODO: add composter stuff
    //this is old fabric events class

    /*

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
                        Block.popResource(world, fixedPos, new ItemStack(ModBlocks.SILT.get()));
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
            }



            if (heldItem.getItem() instanceof ShovelItem) {
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.shovelExtinguishing) {
                    if (targetBlock.is(Blocks.CAMPFIRE) && targetBlock.getValue(BlockStateProperties.LIT)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModBlocks.ASH_LAYER_BLOCK.get()));
                        world.playSound(player, targetPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SOOT.get(), UniformInt.of(3, 5));
                        if(player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            world.setBlockAndUpdate(targetPos, targetBlock.getBlock().withPropertiesOf(targetBlock).setValue(CampfireBlock.LIT, false));
                        }
                        return InteractionResult.SUCCESS;
                    }
                    if (targetBlock.is(Blocks.FIRE)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModBlocks.ASH_LAYER_BLOCK.get()));
                        world.playSound(player, targetPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SOOT.get(), UniformInt.of(3, 5));
                        if(player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            world.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                        }
                        return InteractionResult.SUCCESS;
                    }
                    if ((targetBlock.getBlock() instanceof CharredPillarBlock || targetBlock.getBlock() instanceof CharredBlock || targetBlock.getBlock() instanceof CharredStairsBlock || targetBlock.getBlock() instanceof CharredSlabBlock || targetBlock.getBlock() instanceof CharredFenceBlock || targetBlock.getBlock() instanceof CharredFenceGateBlock) && targetBlock.getValue(CharredBlock.SMOLDERING)) {
                        Block.popResource(world, fixedPos, new ItemStack(ModBlocks.ASH_LAYER_BLOCK.get()));
                        world.playSound(player, targetPos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SOOT.get(), UniformInt.of(3, 5));
                        if(player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            world.setBlockAndUpdate(targetPos, targetBlock.getBlock().withPropertiesOf(targetBlock).setValue(CharredBlock.SMOLDERING, false));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if ((targetBlock.is(ModBlocks.HUMUS.get()) || targetBlock.is(ModBlocks.FLUVISOL.get()) || targetBlock.is(ModBlocks.VERTISOL.get()) || targetBlock.is(ModBlocks.CRYOSOL.get())) && !targetBlock.getValue(BlockStateProperties.SNOWY.get())) {
                    world.playSound(player, targetPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
                    if(player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                        if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                        world.setBlockAndUpdate(targetPos, Blocks.DIRT_PATH.defaultBlockState());
                    }
                    return InteractionResult.SUCCESS;
                }

            }

            if (heldItem.getItem() == Items.FLINT_AND_STEEL) {
                if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.charredBlockIgniting) {
                    if ((targetBlock.getBlock() instanceof CharredPillarBlock || targetBlock.getBlock() instanceof CharredBlock || targetBlock.getBlock() instanceof CharredStairsBlock || targetBlock.getBlock() instanceof CharredSlabBlock || targetBlock.getBlock() instanceof CharredFenceBlock || targetBlock.getBlock() instanceof CharredFenceGateBlock) && !targetBlock.getValue(CharredBlock.SMOLDERING)) {
                        world.playSound(player, targetPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.EMBERSPARK.get(), UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            if (!player.isCreative()) heldItem.hurt(1, new Random(), null);
                            world.setBlockAndUpdate(targetPos, targetBlock.getBlock().withPropertiesOf(targetBlock).setValue(CharredBlock.SMOLDERING, true));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }

            if(ImmersiveWeatheringFabric.getConfig().itemUsesConfig.spongeRusting) {
                if (heldItem.getItem() == Items.WET_SPONGE) {
                    if (targetBlock.is(ModTags.RUSTABLE)) {
                        world.playSound(player, targetPos, SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.BLOCKS, 1.0f, 1.0f);
                        ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SCRAPE_RUST.get(), UniformInt.of(3, 5));
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, targetPos, heldItem);
                            world.setBlockAndUpdate(targetPos, RUSTED_BLOCKS.get(targetBlock.getBlock()).withPropertiesOf(targetBlock));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if (heldItem.getItem() == ModItems.STEEL_WOOL.get()) {
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
                    ParticleUtils.spawnParticlesOnBlockFaces(world, targetPos, ModParticles.SCRAPE_RUST.get(), UniformInt.of(3,5));
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

            return InteractionResult.PASS;
        });
    }
     */
}



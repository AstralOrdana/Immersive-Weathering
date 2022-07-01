package com.ordana.immersive_weathering.blocks;

import com.ordana.immersive_weathering.platform.CommonPlatform;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class ModStairBlock extends StairBlock {

    private static final Field FORGE_BLOCK_SUPPLIER = CommonPlatform.findField(StairBlock.class, "stateSupplier");

    public ModStairBlock(Supplier<Block> baseBlockState, Properties settings) {
        super(FORGE_BLOCK_SUPPLIER == null ? baseBlockState.get().defaultBlockState() : Blocks.AIR.defaultBlockState(), settings);
        if(FORGE_BLOCK_SUPPLIER != null){
            FORGE_BLOCK_SUPPLIER.setAccessible(true);
            try {
                FORGE_BLOCK_SUPPLIER.set(this, baseBlockState);
            }catch (Exception ignored){}
        }
    }
}

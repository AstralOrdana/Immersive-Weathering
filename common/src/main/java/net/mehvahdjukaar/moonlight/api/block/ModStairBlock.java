package net.mehvahdjukaar.moonlight.api.block;

import net.mehvahdjukaar.moonlight.api.platform.PlatformHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class ModStairBlock extends StairBlock {

    private static final Field FORGE_BLOCK_SUPPLIER = PlatformHelper.findField(StairBlock.class, "stateSupplier");

    public ModStairBlock(Supplier<Block> baseBlock, Properties settings) {
        super(FORGE_BLOCK_SUPPLIER == null ? baseBlock.get().defaultBlockState() : Blocks.AIR.defaultBlockState(), settings);
        if (FORGE_BLOCK_SUPPLIER != null) {
            FORGE_BLOCK_SUPPLIER.setAccessible(true);
            try {
                FORGE_BLOCK_SUPPLIER.set(this, (Supplier<BlockState>) () -> baseBlock.get().defaultBlockState());
            } catch (Exception ignored) {
            }
        }
    }
}

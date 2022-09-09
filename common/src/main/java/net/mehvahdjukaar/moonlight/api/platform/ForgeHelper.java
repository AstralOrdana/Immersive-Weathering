package net.mehvahdjukaar.moonlight.api.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Helper class dedicated to platform forge specific methods. Usually fabric methods here just call vanilla stuff while forge have extra logic usually calling events
 */
public class ForgeHelper {

    @ExpectPlatform
    public static FinishedRecipe addRecipeConditions(FinishedRecipe originalRecipe, List<Object> conditions){
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean onProjectileImpact(Projectile improvedProjectileEntity, HitResult blockHitResult) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isCurativeItem(ItemStack stack, MobEffectInstance effect) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean canHarvestBlock(BlockState state, ServerLevel level, BlockPos pos, ServerPlayer player) {
        throw new AssertionError();
    }
    @ExpectPlatform
    public static float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        throw new AssertionError();
    }


}

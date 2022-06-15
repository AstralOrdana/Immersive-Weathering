package com.ordana.immersive_weathering.registry.blocks;

import com.ordana.immersive_weathering.registry.entities.ModDamageSource;
import com.ordana.immersive_weathering.registry.items.ModItems;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class QuicksandBlock extends PowderSnowBlock {
    private static final VoxelShape FALLING_SHAPE = VoxelShapes.fullCube();
    public QuicksandBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {

            if (Random.create().nextInt(25) == 0 && (new BlockPos(entity.getEyePos()).equals(pos) && entity.getEyeY() < pos.getY() + 1)) {
                entity.damage(ModDamageSource.QUICKSAND, 2.0F);
            }
            if (entity.isSneaking()) {
                Vec3d vec3d = entity.getVelocity();
                entity.slowMovement(state, new Vec3d(0.25D, 0.25D, 0.25D));
                entity.setVelocity(vec3d.x, Math.min(1.8D, vec3d.y + 0.1D), vec3d.z);
            } else entity.slowMovement(state, new Vec3d(0.25D, 0.25D, 0.25D));
        }
    }


    @Override
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
        if (!world.isClient()) {
            world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
        }
        return new ItemStack(ModItems.QUICKSAND_BUCKET);
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    public static boolean canWalkOnQuicksand(Entity entity) {
        if (entity.getType().isIn(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS) || entity.isSneaking()) {
            return true;
        } else {
            return entity instanceof LivingEntity && (((LivingEntity) entity).getEquippedStack(EquipmentSlot.FEET).isOf(Items.LEATHER_BOOTS));
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext) {
            Entity entity = entityShapeContext.getEntity();
            if (entity != null) {
                if (entity.fallDistance > 2.5F) {
                    return FALLING_SHAPE;
                }

                boolean bl = entity instanceof FallingBlockEntity;
                if (bl || canWalkOnQuicksand(entity) && context.isAbove(VoxelShapes.fullCube(), pos, false)) {
                    return VoxelShapes.fullCube();
                }
            }
        }

        return VoxelShapes.empty();
    }
}

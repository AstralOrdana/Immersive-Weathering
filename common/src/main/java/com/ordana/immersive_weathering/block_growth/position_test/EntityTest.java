package com.ordana.immersive_weathering.block_growth.position_test;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EntityTest implements PositionRuleTest {

    public static final String NAME = "entity_test";
    public static final Codec<EntityTest> CODEC = RecordCodecBuilder.create((i) -> i.group(
            RegistryCodecs.homogeneousList(Registry.ENTITY_TYPE_REGISTRY).fieldOf("targets").forGetter(EntityTest::getTargets),
            IntProvider.codec(0, 100).fieldOf("count").forGetter(EntityTest::getIntProvider),
            Codec.intRange(0, 64).fieldOf("radius").forGetter(EntityTest::getSize),
            Codec.BOOL.optionalFieldOf("less_than").forGetter(EntityTest::isLessThan),
            Direction.CODEC.optionalFieldOf("look_direction").forGetter(EntityTest::getDirection),
            Codec.INT.optionalFieldOf("offset").forGetter(EntityTest::getOffset)
    ).apply(i, EntityTest::new));

    static final Type<EntityTest> TYPE =
            new Type<>(EntityTest.CODEC, EntityTest.NAME);


    private final HolderSet<EntityType<?>> targets;
    private final IntProvider intProvider;
    private final boolean lessThan;
    private final int size;
    private final int offset;
    @Nullable
    private final Direction direction;


    private EntityTest(HolderSet<EntityType<?>> targets, IntProvider intProvider, int size, Optional<Boolean> lessThan,
                       Optional<Direction> direction, Optional<Integer> offset) {
        this.targets = targets;
        this.intProvider = intProvider;
        this.size = size;
        this.lessThan = lessThan.orElse(false);
        this.direction = direction.orElse(null);
        this.offset = offset.orElse(0);
    }

    public IntProvider getIntProvider() {
        return intProvider;
    }

    public HolderSet<EntityType<?>> getTargets() {
        return targets;
    }

    public int getSize() {
        return size;
    }

    public Optional<Boolean> isLessThan() {
        return Optional.of(lessThan);
    }

    public Optional<Integer> getOffset() {
        return Optional.of(offset);
    }

    public Optional<Direction> getDirection() {
        return Optional.ofNullable(direction);
    }

    @Override
    public boolean test(Holder<Biome> biome, BlockPos pos, Level level) {

        AABB aabb = new AABB(pos).inflate(size);
        if (this.direction != null && this.offset != 0) {
            aabb = aabb.move(direction.getStepX() * offset, direction.getStepY() * offset, direction.getStepZ() * offset);
        }

        var list = level.getEntities((Entity) null, aabb, this::isEntityValid);

        int sampled = intProvider.sample(level.random);
        if (lessThan) {
            return list.size() <= sampled;
        } else {
            return list.size() >= sampled;
        }
    }

    public boolean isEntityValid(Entity e) {
        return targets.contains(e.getType().builtInRegistryHolder());
    }

    @Override
    public Type<?> getType() {
        return TYPE;
    }
}


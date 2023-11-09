package com.ordana.immersive_weathering.data.rute_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.data.block_growths.Operator;
import com.ordana.immersive_weathering.reg.ModRuleTests;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class BlockPropertyTest extends RuleTest {

    public static final Codec<BlockPropertyTest> CODEC = PropPredicate.CODEC.listOf().fieldOf("properties")
            .xmap(BlockPropertyTest::new, (t) -> t.propPredicates).codec();

    private final List<PropPredicate> propPredicates;

    private BlockPropertyTest(List<PropPredicate> propPredicates) {
        this.propPredicates = propPredicates;
    }

    @Override
    public boolean test(BlockState state, RandomSource random) {
        for(var p : propPredicates){
            if(!p.test(state))return false;
        }
        return true;
    }

    @Override
    protected RuleTestType<BlockPropertyTest> getType() {
        return ModRuleTests.BLOCK_PROPERTY_TEST.get();
    }


    private static final class PropPredicate implements Predicate<BlockState> { //

        public static Codec<PropPredicate> CODEC =
                BuiltInRegistries.BLOCK.byNameCodec().partialDispatch("from_block", b -> DataResult.success(b.getFromBlock()),
                        (block) -> {
                            BlockState state = block.defaultBlockState();
                            if (state.getValues().isEmpty()) {
                                return DataResult.error(() -> "Target Block has no properties");
                            }
                            Codec<PropPredicate> c = propertyCodec(state).partialDispatch("property",
                                    b -> DataResult.success(b.getProperty()), (property) -> {

                                        Codec<PropPredicate> c1 = RecordCodecBuilder.create(i -> i.group(
                                                valueCodec(property).optionalFieldOf("value").forGetter(PropPredicate::getTargetValue),
                                                Operator.CODEC.optionalFieldOf("operator", Operator.EQUAL).forGetter(PropPredicate::getOperator)
                                        ).apply(i, (v, o) -> new PropPredicate(block, property, v, o)));

                                        return DataResult.success(c1);
                                    });
                            return DataResult.success(c);
                        });

        private final Block fromBlock;
        private final Property<?> property;
        private final Operator operator;

        @Nullable
        private final Comparable<?> targetValue;
        private final Integer intValue;


        public PropPredicate(Block fromBlock, Property<?> property, Optional<Comparable<?>> value, Operator operator) {
            this.property = property;
            this.targetValue = value.orElse(null);
            this.fromBlock = fromBlock;
            this.operator = operator;
            if (property instanceof IntegerProperty && operator != null && targetValue instanceof Integer i) {
                intValue = i;
            } else intValue = null;
        }



        public Block getFromBlock() {
            return fromBlock;
        }

        public Optional<Comparable<?>> getTargetValue() {
            return Optional.ofNullable(targetValue);
        }

        public Property<?> getProperty() {
            return property;
        }

        public Operator getOperator() {
            return operator;
        }

        @Override
        public boolean test(BlockState state) {
            var val = state.getOptionalValue(property);
            if (val.isPresent()) {
                if (intValue != null) {
                    return operator.apply((Integer)val.get(),  intValue);
                }
                return targetValue == null || val.get() == targetValue;
            }
            return false;
        }

    }

    protected static Codec<Comparable<?>> valueCodec(Property<?> property) {
        return Codec.STRING.flatXmap(string -> property.getValue(string).map(DataResult::success)
                        .orElseGet(() -> DataResult.error(() -> "Unknown Property value" + string + " in " + property)),
                value -> DataResult.success(value.toString())
        );
    }

    protected static Codec<Property<? extends Comparable<?>>> propertyCodec(BlockState state) {
        return Codec.STRING.flatXmap(string -> {
                    for (var p : state.getProperties()) {
                        if (p.getName().equals(string)) {
                            return DataResult.success(p);
                        }
                    }
                    return DataResult.error(() -> "Unknown Property " + string + " in " + state);
                },
                property1 -> DataResult.success(property1.getName())
        );
    }




}



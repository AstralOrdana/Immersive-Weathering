package com.ordana.immersive_weathering.data.rute_tests;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ordana.immersive_weathering.data.block_growths.Operator;
import com.ordana.immersive_weathering.reg.ModRuleTests;
import com.ordana.immersive_weathering.util.StrOpt;
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

        public static final MapCodec<BlockPropertyTest> CODEC = PropPredicate.CODEC.listOf().fieldOf("properties")
            .xmap(BlockPropertyTest::new, (t) -> t.propPredicates);

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

        private static final Codec<SerializedPredicate> SERIALIZED_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("from_block").forGetter(serialized -> serialized.fromBlock),
            Codec.STRING.fieldOf("property").forGetter(serialized -> serialized.property),
            Codec.STRING.optionalFieldOf("value").forGetter(serialized -> serialized.value),
            Operator.CODEC.optionalFieldOf("operator", Operator.EQUAL).forGetter(serialized -> serialized.operator)
        ).apply(instance, (fromBlock, property, value, operator) -> new SerializedPredicate(fromBlock, property, value, operator)));

        public static final Codec<PropPredicate> CODEC = SERIALIZED_CODEC.flatXmap(PropPredicate::fromSerialized, predicate ->
            DataResult.success(new SerializedPredicate(
                predicate.fromBlock,
                predicate.property.getName(),
                predicate.getTargetValue().map(Object::toString),
                predicate.operator)));

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

        private static DataResult<PropPredicate> fromSerialized(SerializedPredicate serialized) {
            BlockState state = serialized.fromBlock().defaultBlockState();
            Property<?> property = null;
            for (Property<?> candidate : state.getProperties()) {
                if (candidate.getName().equals(serialized.property())) {
                    property = candidate;
                    break;
                }
            }
            if (property == null) {
                return DataResult.error(() -> "Unknown Property " + serialized.property() + " in " + state);
            }

            Optional<Comparable<?>> value = Optional.empty();
            if (serialized.value().isPresent()) {
                Property<?> matchedProperty = property;
                var parsedValue = matchedProperty.getValue(serialized.value().get());
                if (parsedValue.isEmpty()) {
                    return DataResult.error(() -> "Unknown Property value" + serialized.value().get() + " in " + matchedProperty);
                }
                value = Optional.of(parsedValue.get());
            }

            return DataResult.success(new PropPredicate(serialized.fromBlock(), property, value, serialized.operator()));
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

        private record SerializedPredicate(Block fromBlock, String property, Optional<String> value, Operator operator) {
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



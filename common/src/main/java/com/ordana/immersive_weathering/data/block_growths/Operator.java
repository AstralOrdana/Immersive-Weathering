package com.ordana.immersive_weathering.data.block_growths;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.Locale;

public enum Operator {
    LESS("<"),
    LESS_EQUAL("<="),
    EQUAL("=="),
    GREATER_EQUAL(">="),
    GREATER(">");

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public boolean apply(int first, int second){
        return switch (this){
            case LESS -> first < second;
            case LESS_EQUAL -> first <= second;
            case EQUAL -> first == second;
            case GREATER_EQUAL -> first >= second;
            case GREATER -> first > second;
        };
    }

    public static final Codec<Operator> CODEC = Codec.STRING.flatXmap(string -> {
                for (var o : Operator.values()) {
                    if (o.name().toLowerCase(Locale.ROOT).equals(string) || o.symbol.equals(string)) {
                        return DataResult.success(o);
                    }
                }
                return DataResult.error("Unknown Operator " + string);
            },
            o -> DataResult.success(o.symbol)
    );
}
package com.kafeine.utils.functionnaljava;

import java.util.function.Function;

public final class ChainStart {

    private ChainStart() {}

    public static ChainStart chain() {
        return new ChainStart();
    }

    public <A, R> ChainLink<A, R> link(final Function<A, R> nextLink) {
        return new ChainLink<>(nextLink);
    }
}

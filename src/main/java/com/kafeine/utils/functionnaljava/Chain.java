package com.kafeine.utils.functionnaljava;

import java.util.function.Function;

public final class Chain {

    private Chain() {}

    public static Chain start() {
        return new Chain();
    }

    public <A, R> ChainLink<A, R> link(final Function<A, R> nextLink) {
        return new ChainLink<>(nextLink);
    }
}

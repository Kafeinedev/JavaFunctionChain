package com.kafeine.utils.functionnaljava;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class ExceptionLink<A, E extends Exception, R> {

    private final Function<A, R> triedFunction;
    private final Class<E> exceptionsType;

    ExceptionLink(final Function<A, R> triedFunction, final Class<E> exceptionsType) {
        this.triedFunction = triedFunction;
        this.exceptionsType = exceptionsType;
    }

    public ChainLink<A, R> handleWith(final BiFunction<A, E, R> resolveFunction) {
        return new ChainLink<>((final A arg) -> {
            try {
                return triedFunction.apply(arg);
            } catch (final Exception exception) {
                if (exceptionsType.isInstance(exception)) {
                    return resolveFunction.apply(arg, exceptionsType.cast(exception));
                }
                throw exception;
            }
        });
    }
}

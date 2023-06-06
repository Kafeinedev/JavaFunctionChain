package com.kafeine.utils.functionnaljava;

import java.util.function.Function;

public final class ChainLink<A, I> {
    private final Function<A, I> link;

    ChainLink(final Function<A, I> link) {
        this.link = link;
    }

    public <R> ChainLink<A, R> link(final Function<I, R> newlink) {
        return new ChainLink<>(newlink.compose(link));
    }

    public ExceptionLink<A, Exception, I> onException() {
        return new ExceptionLink<>(link, Exception.class);
    }

    public <E extends Exception> ExceptionLink<A, E, I> onException(final Class<E> exception) {
        return new ExceptionLink<>(link, exception);
    }

    public Function<A, I> end() {
        return link;
    }
}

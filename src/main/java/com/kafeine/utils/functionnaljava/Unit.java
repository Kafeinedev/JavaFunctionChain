package com.kafeine.utils.functionnaljava;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Base class for chaining function.
 *
 * @param <T> Type of the value contained by the unit.
 */
public final class Unit<T> {

    private final T value;

    /**
     * Private because I don't like new, fight me !
     * @param value to be contained by the unit
     */
    private Unit(final T value) {
        this.value = value;
    }

    /**
     * @return the value of the unit
     */
    public T result() {
        return value;
    }

    /**
     * Create an unit.
     * @param <T> Type of the value of the unit.
     * @param value to be passed to the unit.
     * @return a new unit containing the passed value.
     */
    public static <T> Unit<T> of(final T val) {
        return new Unit<>(val);
    }

    /**
     * Execute the given function.
     * @param <R> Type of the value of the Unit returned.
     * @param function to be executed with the value of the unit.
     * @return a new unit containing the return of the function.
     */
    public <R> Unit<R> execute(final Function<T, R> function) {
        return of(function.apply(value));
    }

    /**
     * Execute the given function. Any exception thrown during the treatment will be handled by the onException function.
     * @param <R> Type of the value of the Unit returned.
     * @param function to be executed with the value of the unit.
     * @param onException function to be executed with the value of the unit on any exception thrown.
     * @return a new unit containing the return of the function.
     */
    public <R> Unit<R> execute(final Function<T, R> function, final Function<T, R> onException) {
        try {
            return of(function.apply(value));
        } catch (final Exception e) {
            return of(onException.apply(value));
        }
    }

    /**
     * Execute the given function. Any exception that is an instance of the expected type will be handled by the onException function.
     * If the exception is not of the expected type then it is re-thrown.
     * @param <R> Type of the value of the Unit returned.
     * @param <E> Type of the expected exception.
     * @param function to be executed with the value of the unit.
     * @param onException function to be executed with the value of the unit and the expected exception.
     * @param expectedType
     * @return a new unit containing the return of the function.
     */
    public <R, E> Unit<R> execute(
            final Function<T, R> function, final BiFunction<T, E, R> onException, final Class<E> expectedType) {
        try {
            return of(function.apply(value));
        } catch (final Exception e) {
            if (expectedType.isInstance(e)) {
                return of(onException.apply(value, expectedType.cast(e)));
            }
            throw e;
        }
    }
}

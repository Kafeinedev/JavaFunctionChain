package com.kafeine.utils.functionnaljava;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExceptionLinkTest {

    @Test
    void exceptionCaughtTest() {
        assertDoesNotThrow(() -> Chain.start()
                .link(s -> {
                    throw new RuntimeException();
                })
                .onException()
                .handleWith((s, e) -> s)
                .end()
                .apply("heyhey"));
    }

    @Test
    void particularExceptionCaughtTest() {
        assertDoesNotThrow(() -> Chain.start()
                .link(s -> {
                    throw new RuntimeException();
                })
                .onException(RuntimeException.class)
                .handleWith((s, e) -> s)
                .end()
                .apply("heyhey"));
    }

    @Test
    void exceptionNotCaughtTestWhenThrownAfterExceptionLink() {
        final var func = Chain.start()
                .link((final String s) -> s)
                .onException()
                .handleWith((s, e) -> s)
                .link(s -> {
                    throw new RuntimeException();
                })
                .end();
        assertThrows(RuntimeException.class, () -> func.apply("people"));
    }

    @Test
    void wrongExceptionTest() {
        final var func = Chain.start()
                .link(s -> {
                    throw new RuntimeException();
                })
                .onException(IllegalAccessException.class)
                .handleWith((s, e) -> s)
                .end();
        assertThrows(RuntimeException.class, () -> func.apply("people"));
    }

    @Test
    void nestedCatchTest() {
        final var func = Chain.start()
                .link((final String s) -> {
                    throw new RuntimeException();
                })
                .onException()
                .handleWith((final String s, final Exception e) -> {
                    throw new IllegalStateException();
                })
                .onException()
                .handleWith((t, u) -> t.replace("OFF", "ON"))
                .end();

        assertEquals("TEST-O-STER-ON", func.apply("TEST-O-STER-OFF"));
    }

    @Test
    void noExceptionTest() {
        final var func = Chain.start()
                .link((final String s) -> "kikoo")
                .onException()
                .handleWith((final String s, final Exception e) -> "lol")
                .end();

        assertEquals("kikoo", func.apply("nawak"));
    }
}

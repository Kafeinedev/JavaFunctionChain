package com.kafeine.utils.functionnaljava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;
import org.junit.jupiter.api.Test;

class ChainTest {

    @Test
    void chainStartTest() {
        assertTrue(Chain.start() instanceof Chain);
    }

    @Test
    void linkTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Function<String, String> f = (final String a) -> a;

        final var testLink = Chain.start().link(f);

        assertTrue(testLink instanceof ChainLink);
        final var field = testLink.getClass().getDeclaredField("link");
        field.setAccessible(true);
        assertEquals(f, field.get(testLink));
    }
}

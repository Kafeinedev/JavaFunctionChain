package com.kafeine.utils.functionnaljava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;
import org.junit.jupiter.api.Test;

class ChainStartTest {

    @Test
    void chainStartTest() {
        assertTrue(ChainStart.chain() instanceof ChainStart);
    }

    @Test
    void linkTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Function<String, String> f = (final String a) -> a;
        final var testLink = ChainStart.chain().link(f);
        assertTrue(testLink instanceof ChainLink);
        // No I wont load a library to remove two lines.
        final var field = testLink.getClass().getDeclaredField("link");
        field.setAccessible(true);
        assertEquals(f, field.get(testLink));
    }
}

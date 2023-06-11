package com.kafeine.utils.functionnaljava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Function;
import org.junit.jupiter.api.Test;

class ChainLinkTest {

    @Test
    void simpleLinkTest() {
        final var chain =
                Chain.start().link((final String nawak) -> nawak.toLowerCase()).end();

        assertEquals("test", chain.apply("TEST"));
    }

    @Test
    void multiLinkTest() {
        final var chain = Chain.start()
                .link((final String nawak) -> nawak.toLowerCase())
                .link((final String nawak) -> nawak.concat(nawak))
                .end();

        assertEquals("testtest", chain.apply("TEST"));
    }

    @Test
    void transformationTest() {
        final var chain = Chain.start()
                .link((final String nawak) -> nawak.trim())
                .link((final String nawak) -> Integer.parseInt(nawak))
                .end();

        assertEquals(25186, chain.apply("  		  25186		 "));
    }

    @Test
    void exceptionLinkTest()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Function<String, String> f = (final String a) -> a;

        final var testLink = Chain.start().link(f).onException(IllegalAccessException.class);

        assertTrue(testLink instanceof ExceptionLink<String, IllegalAccessException, String>);
        final var func = testLink.getClass().getDeclaredField("triedFunction");
        final var excep = testLink.getClass().getDeclaredField("exceptionsType");
        func.setAccessible(true);
        excep.setAccessible(true);
        assertEquals(f, func.get(testLink));
        assertEquals(IllegalAccessException.class, excep.get(testLink));
    }

    @Test
    void defaultExceptionLinkTest()
            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        final Function<String, String> f = (final String a) -> a;

        final var testLink = Chain.start().link(f).onException();

        assertTrue(testLink instanceof ExceptionLink<String, Exception, String>);
        final var func = testLink.getClass().getDeclaredField("triedFunction");
        final var excep = testLink.getClass().getDeclaredField("exceptionsType");
        func.setAccessible(true);
        excep.setAccessible(true);
        assertEquals(f, func.get(testLink));
        assertEquals(Exception.class, excep.get(testLink));
    }
}

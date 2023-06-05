package com.kafeine.utils.functionnaljava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ChainLinkTest {

    @Test
    void simpleLinkTest() {
        final var chain = ChainStart.chain()
                .link((final String nawak) -> nawak.toLowerCase())
                .end();

        assertEquals("test", chain.apply("TEST"));
    }

    @Test
    void multiLinkTest() {
        final var chain = ChainStart.chain()
                .link((final String nawak) -> nawak.toLowerCase())
                .link((final String nawak) -> nawak.concat(nawak))
                .end();

        assertEquals("testtest", chain.apply("TEST"));
    }

    @Test
    void transformationTest() {
        final var chain = ChainStart.chain()
                .link((final String nawak) -> nawak.trim())
                .link((final String nawak) -> Integer.parseInt(nawak))
                .end();

        assertEquals(25186, chain.apply("  		  25186		 "));
    }
}

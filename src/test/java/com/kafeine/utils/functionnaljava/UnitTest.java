package com.kafeine.utils.functionnaljava;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class UnitTest {

    @Test
    void unitCreationTest() {
        final Unit<String> string = Unit.of("test");
        assertEquals("test", string.result());
    }

    @Test
    void transformationTest() {
        final Unit<String> string = Unit.of(1L).execute(number -> "123");
        assertEquals("123", string.result());
    }

    @Test
    void multiplesBindTest() {
        final Unit<String> string = Unit.of("nawak")
                .execute(text -> text.concat("wahoo"))
                .execute(text -> text.replace("nawak", "	 	 actual text "))
                .execute(String::trim);
        assertEquals("actual text wahoo", string.result());
    }

    // those test titles are getting stupid
    @Test
    void onExceptionAllExceptionCaughtNoExceptionTest() {
        final Unit<String> string = Unit.of(1L).execute(num -> num.toString(), num -> "FAUX");

        assertEquals("1", string.result());
    }

    @Test
    void onExceptionAllExceptionCaughtWithExceptionTest() {
        final Unit<String> string = Unit.of(1L)
                .execute(
                        number -> {
                            throw new IllegalStateException();
                        },
                        number -> number.toString());
        assertEquals("1", string.result());
    }

    @Test
    void onExceptionParticularExceptionCaughtNotThrownTest() {
        final Unit<Long> number = Unit.of(1L);

        assertEquals(
                "123",
                number.execute(num -> "123", (num, e) -> "FAUX", Exception.class)
                        .result());
    }

    @Test
    void onExceptionParticularExceptionCaughtThrownButNotCaughtTest() {
        final Unit<Long> number = Unit.of(1L);

        assertThrows(
                IllegalStateException.class,
                () -> number.execute(
                        num -> {
                            throw new IllegalStateException();
                        },
                        (num, e) -> num,
                        NumberFormatException.class));
    }

    @Test
    void onExceptionParticularExceptionCaughtThrownCaughtTest() {
        final Unit<Long> number = Unit.of(1L);

        assertEquals(
                "1",
                number.execute(
                                num -> {
                                    throw new NumberFormatException();
                                },
                                (n, e) -> n.toString(),
                                NumberFormatException.class)
                        .result());
    }
}

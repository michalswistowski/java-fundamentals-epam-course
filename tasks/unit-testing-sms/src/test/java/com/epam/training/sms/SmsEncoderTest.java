package com.epam.training.sms;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmsEncoderTest {

    private static SmsEncoder smsEncoder;

    @BeforeAll
    static void setup() {
        smsEncoder = new SmsEncoder();
    }

    @Test
    @DisplayName("Should throw when invalid input")
    void shouldThrowEncodeWhenInvalidInput() {

        assertThrows(IllegalArgumentException.class, () -> smsEncoder.encode("é"));
    }

    @Test
    @DisplayName("Should throw decode when invalid input")
    void shouldThrowDecodeWhenInvalidInput() {

        assertThrows(IllegalArgumentException.class, () -> smsEncoder.decode("a"));
    }

    @Test
    @DisplayName("Encode should return empty output when empty input")
    void encodeShouldReturnEmptyOutputWhenEmptyInput() {

        String expected = "";
        assertEquals(expected, smsEncoder.encode(""));
    }

    @Test
    @DisplayName("Decode should return empty output when empty input")
    void decodeShouldReturnEmptyOutputWhenEmptyInput() {

        String expected = "";
        assertEquals(expected, smsEncoder.decode(""));
    }

    @Test
    @DisplayName("Encode should return a single key")
    void encodeShouldReturnASingleKey() {

        String expected = "2";
        assertEquals(expected, smsEncoder.encode("a"));
    }

    @Test
    @DisplayName("Should return a character when decoding single key")
    void shouldReturnACharacterWhenDecodingSingleKey() {

        String expected = "a";
        assertEquals(expected, smsEncoder.decode("2"));
    }

    @Test
    @DisplayName("Should return a double key when encoding char")
    void shouldReturnADoubleKeyWhenEncodingChar() {

        String expected = "22";
        assertEquals(expected, smsEncoder.encode("b"));
    }

    @Test
    @DisplayName("Should return a character when decoding double key")
    void shouldReturnACharacterWhenDecodingDoubleKey() {

        String expected = "b";
        assertEquals(expected, smsEncoder.decode("22"));
    }

    @Test
    @DisplayName("Should return text when encoding multiple characters")
    void shouldReturnTextWhenEncodingMultipleCharacters() {

        String expected1 = "4666";
        String expected2 = "528882";

        assertAll(() -> {
            assertEquals(expected1, smsEncoder.encode("go"));
            assertEquals(expected2, smsEncoder.encode("java"));
        });
    }

    @Test
    @DisplayName("Should return multiple characters when decoding text")
    void shouldReturnMultipleCharactersWhenDecodingText() {

        String expected1 = "go";
        String expected2 = "java";

        assertAll(() -> {
            assertEquals(expected1, smsEncoder.decode("4666"));
            assertEquals(expected2, smsEncoder.decode("528882"));
        });
    }

    @Test
    @DisplayName("Should return cipher with a space when encoding chars with the same key")
    void shouldReturnCipherWithASpaceWhenEncodingCharsWithTheSameKey() {

        String expected = "22 2777";
        assertEquals(expected, smsEncoder.encode("bar"));
    }

    @Test
    @DisplayName("Should return text when decoding chars with the same key")
    void shouldReturnTextWhenDecodingCharsWithTheSameKey() {

        String expected = "bar";
        assertEquals(expected, smsEncoder.decode("22 2777"));
    }
}
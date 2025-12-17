package com.sirkaue.creditcardpointsengine.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void shouldCreateCardWhenAllFieldsAreValid() {
        // arrange
        String id = "card-123";
        CardType type = CardType.GOLD;
        String owner = "Kauê";

        // act
        Card card = new Card(id, type, owner);

        // assert
        assertEquals(id, card.getId());
        assertEquals(type, card.getType());
        assertEquals(owner, card.getOwner());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> new Card(null, CardType.GOLD, "Kauê"));

        assertEquals("Card id cannot be null or blank", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenIdIsBlank() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> new Card("   ", CardType.GOLD, "Kauê"));

        assertEquals("Card id cannot be null or blank", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTypeIsNull() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> new Card("card-123", null, "Kauê"));

        assertEquals("Card type cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOwnerIsNull() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> new Card("card-123", CardType.GOLD, null));

        assertEquals("Card owner cannot be null or blank", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOwnerIsBlank() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> new Card("card-123", CardType.GOLD, "  "));

        assertEquals("Card owner cannot be null or blank", exception.getMessage());
    }
}

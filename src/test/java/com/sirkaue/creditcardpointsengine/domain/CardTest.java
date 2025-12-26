package com.sirkaue.creditcardpointsengine.domain;

import com.sirkaue.creditcardpointsengine.domain.exception.InvalidCardException;
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
        assertEquals(id, card.id());
        assertEquals(type, card.type());
        assertEquals(owner, card.owner());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        NullPointerException exception =
                assertThrows(NullPointerException.class,
                        () -> new Card(null, CardType.GOLD, "Kauê"));

        assertEquals("Card id cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenIdIsBlank() {
        InvalidCardException exception =
                assertThrows(InvalidCardException.class,
                        () -> new Card("   ", CardType.GOLD, "Kauê"));

        assertEquals("Card id cannot be blank", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTypeIsNull() {
        NullPointerException exception =
                assertThrows(NullPointerException.class,
                        () -> new Card("card-123", null, "Kauê"));

        assertEquals("Card type cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOwnerIsNull() {
        NullPointerException exception =
                assertThrows(NullPointerException.class,
                        () -> new Card("card-123", CardType.GOLD, null));

        assertEquals("Card owner cannot be null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOwnerIsBlank() {
        InvalidCardException exception =
                assertThrows(InvalidCardException.class,
                        () -> new Card("card-123", CardType.GOLD, "  "));

        assertEquals("Card owner cannot be blank", exception.getMessage());
    }
}

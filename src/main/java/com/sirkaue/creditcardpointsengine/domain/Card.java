package com.sirkaue.creditcardpointsengine.domain;

import com.sirkaue.creditcardpointsengine.domain.exception.InvalidCardException;

import java.util.Objects;

public record Card(String id, CardType type, String owner) {

    public Card(String id, CardType type, String owner) {
        this.id = validateId(id);
        this.type = Objects.requireNonNull(type, "Card type cannot be null");
        this.owner = validateOwner(owner);
    }

    private String validateId(String id) {
        Objects.requireNonNull(id, "Card id cannot be null");

        if (id.isBlank()) {
            throw new InvalidCardException("Card id cannot be blank");
        }

        return id;
    }

    private String validateOwner(String owner) {
        Objects.requireNonNull(owner, "Card owner cannot be null");

        if (owner.isBlank()) {
            throw new InvalidCardException("Card owner cannot be blank");
        }

        return owner;
    }
}

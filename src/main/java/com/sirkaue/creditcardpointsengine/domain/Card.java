package com.sirkaue.creditcardpointsengine.domain;

public final class Card {

    private final String id;
    private final CardType type;
    private final String owner;

    public Card(String id, CardType type, String owner) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Card id cannot be null or blank");
        if (type == null) throw new IllegalArgumentException("Card type cannot be null");
        if (owner == null || owner.isBlank()) throw new IllegalArgumentException("Card owner cannot be null or blank");
        this.id = id;
        this.type = type;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

    public String getOwner() {
        return owner;
    }
}

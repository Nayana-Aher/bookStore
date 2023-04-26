package com.example.bookstore.model;

public enum Category {
    Technical, Literature,
    HUMOUR,
    POETRY,
    SCIENCE_FICTION;

    public String getType() {
        return this.name().toLowerCase();
    }
}

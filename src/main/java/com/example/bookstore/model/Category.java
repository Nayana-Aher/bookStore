package com.example.bookstore.model;

public enum Category {
    TECHNICAL,
    LITERATURE,
    HUMOUR,
    POETRY,
    SCIENCE_FICTION;

    public String getType() {
        return this.name().toLowerCase();
    }
}

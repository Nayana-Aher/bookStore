package com.example.bookstore.model;

public enum Category {
    Technical, Literature, Humour, Poetry, Science_friction;

    public String getType() {
        return this.name().toLowerCase();
    }
}

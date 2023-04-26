package com.example.bookstore.model;

public enum Category {
    Technical, Literature, Humour, Poetry, Science_friction;

    public static boolean isValid(String category) {
        for (Category c : values()) {
            if (c.name().equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }
    public String getType() {
        return this.name().toLowerCase();
    }
}

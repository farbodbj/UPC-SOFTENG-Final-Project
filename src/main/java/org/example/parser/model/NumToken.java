package org.example.parser.model;

public class NumToken extends Token {
    private final int number;
    public NumToken(String stringRepr) {
        this.number = Integer.parseInt(stringRepr);
    }

    public int getNumber() {
        return this.number;
    }
}

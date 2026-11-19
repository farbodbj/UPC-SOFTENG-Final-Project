package org.example.parser.model;

public class NumToken extends Token {
    private final int number;
    public NumToken(String stringRepr) {
        this.number = Integer.parseInt(stringRepr);
    }

    public int getNumber() {
        return this.number;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NumToken)) return false;
        else return ((NumToken) obj).number == this.number;
    }
}

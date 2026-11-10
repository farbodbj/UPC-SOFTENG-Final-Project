package org.example.model.Cell.errors;

public class InvalidValue extends RuntimeException {
    public InvalidValue() {
        super("The value entered for this cell is not valid");
    }
}

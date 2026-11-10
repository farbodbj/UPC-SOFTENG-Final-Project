package org.example.model.SpreadSheet;

public class InvalidModificationException extends RuntimeException {
    public InvalidModificationException() {
        super("This modification contains invalid input");
    }
}

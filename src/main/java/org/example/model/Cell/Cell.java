package org.example.model.Cell;

import org.example.model.Cell.errors.InvalidValue;

public abstract class Cell {
    String content;

    // column, row
    // column: A, AA, BZ etc.
    // row idx starts from 1
    Pair<String, Integer> location;

    protected Cell(String content) {
        this.content = content;
    }

    public void setLocation(Pair<String, Integer> loc) {
        this.location = loc;
    }

    public Pair<String, Integer> getLocation() {
        return this.location;
    }

    public String getContent() {
        return content;
    }

    public abstract void validate() throws InvalidValue;
}

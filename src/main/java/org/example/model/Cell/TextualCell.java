package org.example.model.Cell;

import org.example.model.Cell.errors.InvalidValue;

public class TextualCell extends Cell {

    public TextualCell(String content) {
        super(content);
    }

    @Override
    public void validate() throws InvalidValue {}
}

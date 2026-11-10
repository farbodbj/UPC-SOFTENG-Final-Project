package org.example.model.Cell;

import org.example.model.Cell.errors.InvalidValue;

public class FormulaCell extends Cell {

    public FormulaCell(String content) {
        super(content);
    }

    @Override
    public void validate() throws InvalidValue {
        // just naive validation
        if(!getContent().startsWith("=")) {
            throw new InvalidValue();
        }
    }
}

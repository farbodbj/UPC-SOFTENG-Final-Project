package org.example.model.Cell;

import org.example.model.Cell.errors.InvalidValue;

import java.text.NumberFormat;
import java.text.ParseException;

public class NumericCell extends Cell {
    private final NumberFormat numberFormatParser = NumberFormat.getInstance();

    public NumericCell(String content) {
        super(content);
    }

    @Override
    public void validate() throws InvalidValue {
        try {
            numberFormatParser.parse(getContent());
        } catch (NumberFormatException | ParseException e) {
            throw new InvalidValue();
        }
    }
}

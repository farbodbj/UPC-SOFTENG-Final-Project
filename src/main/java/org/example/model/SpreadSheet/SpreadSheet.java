package org.example.model.SpreadSheet;

import org.example.model.Cell.Cell;
import org.example.model.Cell.Pair;
import org.example.model.Cell.errors.InvalidValue;

public class SpreadSheet {
    private Cell[][] cells = new Cell[0][0];

    public Cell getCell(Pair<String, Integer> location) {
        int row = getRowIndex(location);
        int column = getColumnIndex(location);
        if (row >= cells.length || column >= cells[row].length) {
            return null;
        }
        return cells[row][column];
    }

    public void modifyCell(Pair<String, Integer> location, Cell newValue) throws InvalidModificationException {
        int row = getRowIndex(location);
        int column = getColumnIndex(location);
        try {
            newValue.validate();
        } catch (InvalidValue e) {
            throw new InvalidModificationException();
        }
        newValue.setLocation(location);
        ensureCapacity(row + 1, column + 1);
        cells[row][column] = newValue;
    }

    private int getRowIndex(Pair<String, Integer> location) {
        if (location.getFirst() == null || location.getSecond() == null || location.getSecond() < 1) {
            throw new IllegalArgumentException("Enter a valid cell location");
        }
        return location.getSecond() - 1;
    }

    private int getColumnIndex(Pair<String, Integer> location) {
        if (location.getFirst() == null) {
            throw new IllegalArgumentException("Enter a valid cell location");
        }
        String column = location.getFirst().toUpperCase();
        if (!column.matches("[A-Z]+")) {
            throw new IllegalArgumentException("Enter a valid cell location");
        }
        int index = 0;
        for (char letter : column.toCharArray()) {
            index = index * 26 + letter - 'A' + 1;
        }
        return index - 1;
    }

    private void ensureCapacity(int requiredRows, int requiredColumns) {
        Cell[][] expanded = new Cell[requiredRows][requiredColumns];
        for (int row = 0; row < cells.length; row++) {
            System.arraycopy(cells[row], 0, expanded[row], 0, cells[row].length);
        }
        cells = expanded;
    }
}

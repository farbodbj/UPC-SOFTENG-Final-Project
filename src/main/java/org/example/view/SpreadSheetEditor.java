package org.example.view;

import org.example.model.Cell.Cell;
import org.example.model.Cell.FormulaCell;
import org.example.model.Cell.NumericCell;
import org.example.model.Cell.Pair;
import org.example.model.Cell.TextualCell;
import org.example.model.Cell.errors.InvalidValue;
import org.example.model.Range.Range;
import org.example.model.SpreadSheet.InvalidModificationException;
import org.example.model.SpreadSheet.SpreadSheet;

import java.util.Scanner;

public class SpreadSheetEditor {
    private final SpreadSheet spreadSheet = new SpreadSheet();

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Spreadsheet editor. Commands: view, edit, quit");

        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                return;
            }
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "view" -> viewRange(scanner);
                case "edit" -> editCell(scanner);
                case "quit", "exit" -> {
                    return;
                }
                default -> System.out.println("Enter view, edit, or quit.");
            }
        }
    }

    private void viewRange(Scanner scanner) {
        System.out.print("Range (for example A1:B3): ");
        if (!scanner.hasNextLine()) {
            return;
        }

        try {
            Range range = Range.parse(scanner.nextLine().trim());
            int previousRow = -1;
            for (Pair<String, Integer> location : range.getCellLocations()) {
                if (previousRow != -1) {
                    System.out.print(previousRow == location.getSecond()
                            ? "\t" : System.lineSeparator());
                }
                Cell cell = spreadSheet.getCell(location);
                String value = cell == null ? "" : cell.getContent();
                System.out.printf("%s%d: %s", location.getFirst(), location.getSecond(), value);
                previousRow = location.getSecond();
            }
            System.out.println();
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid range: " + e.getMessage());
        }
    }

    private void editCell(Scanner scanner) {
        System.out.print("Cell location (for example A1): ");
        if (!scanner.hasNextLine()) {
            return;
        }
        String location = scanner.nextLine().trim();

        System.out.print("New value: ");
        if (!scanner.hasNextLine()) {
            return;
        }
        String value = scanner.nextLine();

        try {
            Pair<String, Integer> cellLocation = Range.parseCellReference(location);
            spreadSheet.modifyCell(cellLocation, createCell(value));
            System.out.println("Cell updated.");
        } catch (IllegalArgumentException | InvalidModificationException e) {
            System.out.println("Could not update cell: " + e.getMessage());
        }
    }

    private Cell createCell(String value) {
        if (value.startsWith("=")) {
            return new FormulaCell(value);
        }

        NumericCell numericCell = new NumericCell(value);
        try {
            numericCell.validate();
            return numericCell;
        } catch (InvalidValue e) {
            return new TextualCell(value);
        }
    }
}

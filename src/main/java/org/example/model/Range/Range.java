package org.example.model.Range;

import org.example.model.Cell.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Range {

    // verfifies cell pattern: AA12, AZ24, B2
    private static final Pattern CELL_REFERENCE = Pattern.compile("([A-Za-z]+)([1-9][0-9]*)");

    private final Pair<String, Integer> start;
    private final Pair<String, Integer> end;

    public Range(Pair<String, Integer> start, Pair<String, Integer> end) {
        this.start = start;
        this.end = end;
        if (columnIndex(start.getFirst()) > columnIndex(end.getFirst())
                || start.getSecond() > end.getSecond()) {
            throw new IllegalArgumentException("Range start must be above and to the left of its end");
        }
    }

    public static Range parse(String reference) {
        String[] endpoints = reference.split(":", -1);
        if (endpoints.length != 2) {
            throw new IllegalArgumentException("Enter a range such as A1:B3");
        }
        return new Range(parseCellReference(endpoints[0]), parseCellReference(endpoints[1]));
    }

    public static Pair<String, Integer> parseCellReference(String reference) {
        Matcher matcher = CELL_REFERENCE.matcher(reference);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Enter a cell location such as A1");
        }
        return new Pair<>(matcher.group(1).toUpperCase(), Integer.parseInt(matcher.group(2)));
    }

    public Pair<String, Integer> getStart() {
        return start;
    }

    public Pair<String, Integer> getEnd() {
        return end;
    }

    public List<Pair<String, Integer>> getCellLocations() {
        List<Pair<String, Integer>> locations = new ArrayList<>();
        for (int row = start.getSecond(); row <= end.getSecond(); row++) {
            for (int column = columnIndex(start.getFirst()); column <= columnIndex(end.getFirst()); column++) {
                locations.add(new Pair<>(columnName(column), row));
            }
        }
        return locations;
    }


    // write a utility for this later
    private static int columnIndex(String column) {
        int index = 0;
        for (char letter : column.toUpperCase().toCharArray()) {
            index = index * 26 + letter - 'A' + 1;
        }
        return index;
    }

    private static String columnName(int index) {
        StringBuilder name = new StringBuilder();
        while (index > 0) {
            index--;
            name.insert(0, (char) ('A' + index % 26));
            index /= 26;
        }
        return name.toString();
    }
}

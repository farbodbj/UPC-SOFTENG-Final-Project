package org.example.parser;

import org.example.parser.model.CellToken;
import org.example.parser.model.NumToken;
import org.example.parser.model.OpToken;
import org.example.parser.model.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ParserTest {

    private final Parser parser = new Parser();

    @Test
    void parse_respects_operator_precedence() {
        CellToken cell = new CellToken("A1");
        List<Token> parsed = parser.parse(List.of(
                new NumToken("2"), new OpToken("+"), new NumToken("3"),
                new OpToken("*"), cell));

        Assertions.assertEquals(List.of(
                new NumToken("2"), new NumToken("3"), cell,
                new OpToken("*"), new OpToken("+")), parsed);
    }

    @Test
    void parse_keeps_associativity() {
        List<Token> parsed = parser.parse(List.of(
                new NumToken("8"), new OpToken("/"), new NumToken("4"),
                new OpToken("/"), new NumToken("2")));

        Assertions.assertEquals(List.of(
                new NumToken("8"), new NumToken("4"), new OpToken("/"),
                new NumToken("2"), new OpToken("/")), parsed);
    }
}

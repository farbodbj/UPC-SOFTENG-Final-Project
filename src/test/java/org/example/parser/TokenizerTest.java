package org.example.parser;

import org.example.parser.model.NumToken;
import org.example.parser.model.OpToken;
import org.example.parser.model.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class TokenizerTest {

    private Tokenizer tokenizer;

    @BeforeEach
    void setUp() {
        tokenizer = new Tokenizer();
    }

    @Test
    void tokenizer_test_single_digit_numeric_addition() {
        List<Token> tokens = tokenizer.tokenize("2+2");
        List<Token> expected = List.of(new NumToken("2"), new OpToken("+"), new NumToken("2"));

        Assertions.assertIterableEquals(expected, tokens);
    }

    @Test
    void tokenizer_test_multidigit_numeric_multiplication() {
        List<Token> tokens = tokenizer.tokenize("8798*24531");
        List<Token> expected = List.of(new NumToken("8798"), new OpToken("*"), new NumToken("24531"));

        Assertions.assertIterableEquals(expected, tokens);
    }


    @Test
    void tokenizer_test_multidigit_multioperation() {
        List<Token> tokens = tokenizer.tokenize("24/12+13-125");
        List<Token> expected = List.of(
                new NumToken("24"),
                new OpToken("/"),
                new NumToken("12"),
                new OpToken("+"),
                new NumToken("13"),
                new OpToken("-"),
                new NumToken("125")
        );

        Assertions.assertIterableEquals(expected, tokens);
    }
}
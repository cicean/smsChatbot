package com.lambdanum.smsbackend.nlp;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TokenizerServiceTest {

    private TokenizerService tokenizerService = new TokenizerService();

    private String EXAMPLE_MESSAGE = "say hello 5 times";

    @Test
    public void testExampleTokenizeAndStem() {
        List<String> expected = Arrays.asList("say", "hello", "5", "time");

        List<String> tokenizedMessage = tokenizerService.tokenizeAndStem(EXAMPLE_MESSAGE);

        assertEquals(expected, tokenizedMessage);
    }

}

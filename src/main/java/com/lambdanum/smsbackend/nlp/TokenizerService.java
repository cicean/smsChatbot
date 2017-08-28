package com.lambdanum.smsbackend.nlp;

import opennlp.tools.stemmer.snowball.SnowballStemmer;
import opennlp.tools.tokenize.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TokenizerService {

    private Tokenizer tokenizer;
    private SnowballStemmer snowballStemmer;

    @Autowired
    public TokenizerService(Tokenizer tokenizer, SnowballStemmer snowballStemmer) {
        this.tokenizer = tokenizer;
        this.snowballStemmer = snowballStemmer;
    }

    public List<String> tokenizeAndStem(String input) {
        return stem(tokenize(input));
    }

    private String[] tokenize(String input) {
        return tokenizer.tokenize(input);
    }

    private List<String> stem(String[] tokens) {
        List<String> stems = new ArrayList<>();
        for (String token : tokens) {
            stems.add(snowballStemmer.stem(token).toString().toLowerCase());
        }
        return stems;
    }

}

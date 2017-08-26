package com.lambdanum.smsbackend.nlp;

import com.lambdanum.smsbackend.filesystem.ResourceRepository;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.stemmer.snowball.SnowballStemmer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TokenizerService {

    @Autowired
    private ResourceRepository resourceRepository;

    public List<String> tokenizeAndStem(String input) {
        return stem(tokenize(input));
    }

    private String[] tokenize(String input) {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            String[] tokens;
            TokenizerModel model = new TokenizerModel(resourceRepository.getResourceFileInputStream("en-token.bin"));
            Tokenizer tokenizer = new TokenizerME(model);
            tokens = tokenizer.tokenize(input);
            return tokens;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }

    private List<String> stem(String[] tokens) {
        List<String> stems = new ArrayList<>();
        SnowballStemmer stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH);
        for (String token : tokens) {
            stems.add(stemmer.stem(token).toString().toLowerCase());
        }
        return stems;
    }

}

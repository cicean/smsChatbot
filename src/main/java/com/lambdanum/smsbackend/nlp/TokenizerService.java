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
    private ResourceRepository resourceRepository = new ResourceRepository();

    public void foobar() {
        System.out.println("tokenizer foobar");

        String testString = "Remind me to pick up the kids at kindergarden at 5 PM.";

        System.out.println(testString);

        String[] tokens = null;
        String[] tags = null;
        List<String> stems = new ArrayList<>();

        ClassLoader classLoader = getClass().getClassLoader();
        //Tokenize
        try {
            TokenizerModel model = new TokenizerModel(resourceRepository.getResourceFileInputStream("en-token.bin"));
            Tokenizer tokenizer = new TokenizerME(model);
            tokens = tokenizer.tokenize(testString);
            System.out.println("Tokens:");
            printArray(tokens);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        //Tag POS

        POSTaggerME tagger;
        try {
            POSModel posModel = new POSModel(resourceRepository.getResourceFileInputStream("en-pos-maxent.bin"));
            tagger = new POSTaggerME(posModel);
            tags = tagger.tag(tokens);
            System.out.println("Tags:");
            printArray(tags);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Stemming
        SnowballStemmer stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH);
        for (String token : tokens) {
            stems.add(stemmer.stem(token).toString());
        }
        System.out.println("Stems: ");
        System.out.println(stems);

    }

    private void printArray(String[] array) {
        String output = "[";
        for (String string : array) {
            output += string + ",";
        }
        System.out.println(output.substring(0,output.length()-1) + "]");
    }

    public static void main(String[] args) {
        TokenizerService tokenizerService = new TokenizerService();
        tokenizerService.foobar();
    }

    public List<String> tokenizeAndStem(String input) {
        return stem(tokenize(input));
    }

    private String[] tokenize(String input) {
        ClassLoader classLoader = getClass().getClassLoader();
        //Tokenize
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

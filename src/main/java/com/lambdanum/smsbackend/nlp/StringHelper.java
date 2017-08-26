package com.lambdanum.smsbackend.nlp;

public class StringHelper {

    public static String stripPunctuation(String s) {
        return s.replace(".", "")
                        .replace(",", "")
                        .replace(":","")
                        .replace(";","")
                        .replace("!", "")
                        .replace("?","")
                        .replace("  ", " ");
    }

    public static String spacePunctuation(String s) {
        return s.replace(".", " . ")
                        .replace(",", " , ")
                        .replace(":"," : ")
                        .replace(";"," ; ")
                        .replace("!", " ! ")
                        .replace("?", " ? ")
                        .replace("  ", " ");
    }
}

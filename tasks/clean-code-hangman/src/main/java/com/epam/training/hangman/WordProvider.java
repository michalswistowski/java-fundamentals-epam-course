package com.epam.training.hangman;

import java.util.List;
import java.util.Random;

public class WordProvider {

    private final List<String> words = List.of("hangman", "apple", "bee", "clean", "computer", "office", "recursion");
    private final Random random = new Random();

    public String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }
}

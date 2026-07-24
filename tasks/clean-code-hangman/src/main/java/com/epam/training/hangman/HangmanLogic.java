package com.epam.training.hangman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HangmanLogic {
    private static final int MAX_WRONG_GUESSES = 7;
    private static final char MASK_CHAR = '_';
    private static final String VALID_WORD_REGEX = "^[a-z]+$";

    private final String targetWord;
    private final List<Character> guessedLetters;
    private int wrongGuessCount;
    private State state;

    public HangmanLogic(String targetWord) {
        validateTargetWord(targetWord);

        this.targetWord = targetWord;
        this.guessedLetters = new ArrayList<>();
        this.wrongGuessCount = 0;
        this.state = State.IN_PROGRESS;
    }

    public void guess(char letter) {
        validateGameIsInProgress();
        validateGuess(letter);

        recordGuess(letter);
        updateGameState();
    }

    private void recordGuess(char letter) {
        guessedLetters.add(letter);

        if (!isLetterInTargetWord(letter)) {
            wrongGuessCount++;
        }
    }

    private void updateGameState() {
        if (isWordFullyGuessed()) {
            state = State.WON;
        } else if (hasReachedMaxWrongGuesses()) {
            state = State.LOST;
        }
    }

    public String getDisplayedWord() {
        if (state == State.LOST) {
            return targetWord;
        }
        return buildMaskedWord();
    }

    public String getTargetWord() {
        return targetWord;
    }

    private String buildMaskedWord() {
        StringBuilder displayedWord = new StringBuilder();

        for (char letter : targetWord.toCharArray()) {
            if (guessedLetters.contains(letter)) {
                displayedWord.append(letter);
            } else {
                displayedWord.append(MASK_CHAR);
            }
        }

        return displayedWord.toString();
    }

    public State getState() {
        return state;
    }

    public List<Character> getLettersTried() {
        return List.copyOf(guessedLetters);
    }

    public int getWrongGuessesLeft() {
        return MAX_WRONG_GUESSES - wrongGuessCount;
    }

    private boolean isLetterInTargetWord(char letter) {
        return targetWord.indexOf(letter) >= 0;
    }

    private boolean isWordFullyGuessed() {
        for (char letter : targetWord.toCharArray()) {
            if (!guessedLetters.contains(letter)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasReachedMaxWrongGuesses() {
        return wrongGuessCount >= MAX_WRONG_GUESSES;
    }

    private void validateTargetWord(String word) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("Target word cannot be null or empty.");
        }
        if (!word.matches(VALID_WORD_REGEX)) {
            throw new IllegalArgumentException("Target word must contain only lowercase letters (a-z).");
        }
    }

    private void validateGameIsInProgress() {
        if (state != State.IN_PROGRESS) {
            throw new IllegalStateException("Cannot guess, game is already finished.");
        }
    }

    private void validateGuess(char letter) {
        if (letter < 'a' || letter > 'z') {
            throw new IllegalArgumentException("Guess must be a lowercase letter (a-z).");
        }
        if (guessedLetters.contains(letter)) {
            throw new IllegalArgumentException("Letter '" + letter + "' was already used.");
        }
    }
}
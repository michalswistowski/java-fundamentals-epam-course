package com.epam.training.hangman;

import java.util.List;
import java.util.Scanner;

public class Console {

    private final Scanner scanner;

    public Console(Scanner scanner) {
        this.scanner = scanner;
    }

    public char promptForLetter() {
        while (true) {
            System.out.print("Enter your guess: ");
            String input = scanner.next();
            System.out.println();

            if (input.length() == 1) {
                return input.charAt(0);
            }
            displayError("please enter a single character only!");
        }
    }

    public void displayState(String word, List<Character> usedLetters, int guessesLeft) {
        System.out.println("The word: " + word);
        System.out.println("Letters tried: " + usedLetters);
        System.out.println("Wrong guesses until game over: " + guessesLeft);
    }

    public void displayWelcomeMessage() {
        System.out.println("Welcome to the Hangman game!\n");
    }

    public void displayWinMessage(String targetWord) {
        System.out.println("Congratulations, you won!");
        System.out.println("The word was: " + targetWord);
    }

    public void displayLoss(String targetWord) {
        System.out.println("You have no more tries left, you lost the game");
        System.out.println("The word was: " + targetWord);
    }

    public void displayError(String error) {
        System.out.printf("Error: %s\n", error);
    }
}

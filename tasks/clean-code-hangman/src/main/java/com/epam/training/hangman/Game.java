package com.epam.training.hangman;

public class Game {

    private final WordProvider wordProvider;
    private final Console console;

    public Game(WordProvider wordProvider, Console console) {
        this.wordProvider = wordProvider;
        this.console = console;
    }

    public void start() {
        String word = wordProvider.getRandomWord();
        HangmanLogic logic = new HangmanLogic(word);

        console.displayWelcomeMessage();
        play(logic);
        displayResult(logic);
    }

    private void play(HangmanLogic logic) {
        System.out.println(logic.getState());
        while (logic.getState() == State.IN_PROGRESS) {
            console.displayState(logic.getDisplayedWord(), logic.getLettersTried(), logic.getWrongGuessesLeft());
            executeSingleTurn(logic);
        }
    }

    private void executeSingleTurn(HangmanLogic logic) {
        while (true) {
            char letter = console.promptForLetter();
            try {
                logic.guess(letter);
                return;
            } catch (Exception e) {
                console.displayError(e.getMessage());
            }
        }
    }

    public void displayResult(HangmanLogic logic) {
        if (logic.getState() == State.WON) {
            console.displayWinMessage(logic.getDisplayedWord());
        } else {
            console.displayLoss(logic.getTargetWord());
        }
    }
}

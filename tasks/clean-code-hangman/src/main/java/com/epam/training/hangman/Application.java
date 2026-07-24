package com.epam.training.hangman;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Application extends Object {

    public static void main(String[] args) {
        WordProvider wordProvider = new WordProvider();
        Console console = new Console(new Scanner(System.in, StandardCharsets.UTF_8));
        Game game = new Game(wordProvider, console);

        game.start();
    }

}
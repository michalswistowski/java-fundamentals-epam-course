package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Verse implements Text {
    private Part parent;
    private final int number;
    private final String content;
    private final static List<String> PUNCTUATION_MARKS_TO_OMIT = new ArrayList<>(List.of(",", ";", "."));

    public Verse(int number, String content) {
        this.number = number;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public int getNumber() {
        return number;
    }

    public void setParent(Part parent) {
        this.parent = parent;
    }

    public String format() {
        // To be implemented
        Part part = parent;
        Book book = part.getParent();

        return "%s %d,%d \"%s\"".formatted(book.getTitle(), part.getNumber(), getNumber(), content);
    }

    @Override
    public int getNumberOfWords() {
        String omitted = content;

        for (String c : PUNCTUATION_MARKS_TO_OMIT) {
            omitted = omitted.replace(c, "");
        }
        return (int) Arrays.stream(omitted.split("[\s]+")).count();
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {

        String omitted = content;

        for (String c : PUNCTUATION_MARKS_TO_OMIT) {
            omitted = omitted.replace(c, "");
        }
        boolean contains = Arrays.asList(omitted.split(" ")).contains(word);

        return contains ? List.of(this) : List.of();
    }
}

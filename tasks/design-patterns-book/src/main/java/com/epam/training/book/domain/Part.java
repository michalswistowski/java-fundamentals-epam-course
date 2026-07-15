package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.ArrayList;
import java.util.List;

public class Part implements Text {
    private Book parent;
    private final int number;
    private final List<Verse> verses = new ArrayList<>();

    public Part(int number) {
        this.number = number;
    }

    public void setParent(Book parent) {
        this.parent = parent;
    }

    public int getNumber() {
        return number;
    }

    public Book getParent() {
        return parent;
    }

    public void addVerse(Verse verse) {
        verse.setParent(this);
        verses.add(verse);
    }

    @Override
    public int getNumberOfWords() {
        return verses.stream().map(Verse::getNumberOfWords).reduce(0, Integer::sum);
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {
        return verses.stream().flatMap((verse) -> verse.getVersesContainingWord(word).stream()).toList();
    }
}

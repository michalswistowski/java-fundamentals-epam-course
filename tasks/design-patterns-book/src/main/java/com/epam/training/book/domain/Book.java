package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.ArrayList;
import java.util.List;

public class Book implements Text {
    private final String title;
    private final List<Part> parts = new ArrayList<>();

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void addPart(Part part) {
        part.setParent(this);
        parts.add(part);
    }

    @Override
    public int getNumberOfWords() {
        return parts.stream().map(Part::getNumberOfWords).reduce(0, Integer::sum);
    }

    @Override
    public List<Verse> getVersesContainingWord(String word) {
        return parts.stream().flatMap((verse) -> verse.getVersesContainingWord(word).stream()).toList();
    }
}

package com.epam.training.book.domain;

import com.epam.training.book.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AllBooks implements Text {
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public int getNumberOfWords() {
//        return books.stream().flatMapToInt(book -> IntStream.of(book.getNumberOfWords())).sum();
        return books.stream().map(Book::getNumberOfWords).reduce(0, Integer::sum);
    }

    public List<Verse> getVersesContainingWord(String searchWord) {
        return books.stream().flatMap((verse) -> verse.getVersesContainingWord(searchWord).stream()).toList();
    }
}

package com.epam.training.person;

import com.epam.training.person.domain.Person;

import java.time.LocalDate;
import java.util.function.Predicate;

public class PersonDateOfBirthPredicate implements Predicate<Person> {

    private LocalDate dateLimit;

    public PersonDateOfBirthPredicate(LocalDate dateLimit) {
        this.dateLimit = dateLimit;
    }

    @Override
    public boolean test(Person person) {
        return person.dateOfBirth().isBefore(dateLimit);
    }
}

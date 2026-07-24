package com.epam.training.person.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record Person(UUID id, String name, LocalDate dateOfBirth, Location placeOfBirth, Gender gender, List<Phone> phones) {

    public Person(UUID id, String name, LocalDate dateOfBirth, Location placeOfBirth, Gender gender, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.gender = gender;
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", placeOfBirth=" + placeOfBirth +
                ", gender=" + gender +
                ", phones=" + phones +
                '}';
    }
}

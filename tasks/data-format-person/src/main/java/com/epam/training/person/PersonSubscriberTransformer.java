package com.epam.training.person;

import com.epam.training.person.domain.Person;
import com.epam.training.person.domain.Subscriber;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class PersonSubscriberTransformer implements Transformer<List<Person>, List<Subscriber>> {

    private Predicate<Person> predicate;

    public PersonSubscriberTransformer(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public List<Subscriber> transform(List<Person> value) {
        Map<String, String> subMap = new LinkedHashMap<>();

        value.stream().filter(predicate).forEach(person -> {
            person.phones().forEach(phone -> {
                subMap.put(PhoneParser.parse(phone), person.name());
            });
        });

        return subMap.entrySet().stream()
                .map(entry -> new Subscriber(entry.getKey(), entry.getValue()))
                .toList();
    }

}

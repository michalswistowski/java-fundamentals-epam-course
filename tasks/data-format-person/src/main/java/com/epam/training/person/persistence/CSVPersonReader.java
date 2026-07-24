package com.epam.training.person.persistence;

import com.epam.training.person.PhoneParser;
import com.epam.training.person.domain.Gender;
import com.epam.training.person.domain.Location;
import com.epam.training.person.domain.Person;
import com.epam.training.person.domain.Phone;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.*;

public class CSVPersonReader implements DataReader<List<Person>> {

    private final Scanner scanner;

    public CSVPersonReader(InputStream in) {
        this.scanner = new Scanner(in);
    }

    @Override
    public List<Person> read() {

        List<Person> people = new ArrayList<>();

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            String[] values = line.split(",");

            UUID id = UUID.fromString(values[0]);
            String name = values[1];
            LocalDate birthDate = LocalDate.parse(values[2]);
            String iso = values[3];
            String country = values[4];
            String zip = values[5];
            String city = values[6];
            Gender gender = Gender.valueOf(values[7].toUpperCase());

            List<Phone> phones = new ArrayList<>();

            for (int i = 8; i <= 10; i++) {
                System.out.println(values[i]);
                if (values[i].equals("-")) {
                    continue;
                }
                phones.add(PhoneParser.parse(values[i]));
            }

            Location location = new Location(iso, country, zip, city);

            Person person = new Person(id, name, birthDate, location, gender, phones);
            people.add(person);
        }
        return people;
    }

    @Override
    public void close() throws Exception {
        scanner.close();
    }
}

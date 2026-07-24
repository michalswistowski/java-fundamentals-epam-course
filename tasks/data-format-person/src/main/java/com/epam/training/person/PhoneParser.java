package com.epam.training.person;

import com.epam.training.person.domain.Phone;

import java.util.regex.Pattern;

public class PhoneParser {

    public static Phone parse(String value) throws IllegalArgumentException {

        if (Pattern.matches("^[0-9]{10}$", value)) {
            String area = value.substring(0, 3);
            String region = value.substring(3, 6);
            String local = value.substring(6, 10);
            return new Phone(area, region, local);
        } else if (Pattern.matches("^[0-9]{3}-[0-9]{3}-[0-9]{4}$", value)) {
            String[] values = value.split("-");

            String area = values[0];
            String region = values[1];
            String local = values[2];

            return new Phone(area, region, local);
        }
        throw new IllegalArgumentException();
    }

    public static String parse(Phone phone) {
        return phone.area() + "-" + phone.region() + "-" + phone.local();
    }

}

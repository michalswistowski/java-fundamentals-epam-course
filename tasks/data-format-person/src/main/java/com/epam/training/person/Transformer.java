package com.epam.training.person;

public interface Transformer<FROM, TO> {

    TO transform(FROM value);
}

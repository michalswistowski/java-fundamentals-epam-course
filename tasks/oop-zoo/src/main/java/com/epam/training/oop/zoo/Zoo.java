package com.epam.training.oop.zoo;

import com.epam.training.oop.zoo.animals.Animal;

import java.util.Arrays;

public class Zoo {

    private final Zookeeper[] zookeepers;
    private final Animal[] animals;

    public Zoo(Zookeeper[] zookeepers, Animal[] animals) {
        this.zookeepers = zookeepers;
        this.animals = animals;
    }

    public void feedtime() {
        Arrays.stream(zookeepers).forEach(zookeeper -> zookeeper.feed(animals));
    }
}

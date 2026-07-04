package com.epam.training.oop.zoo.animals;

import com.epam.training.oop.zoo.Consumption;

public class Zebra extends Animal {
    public Zebra(String name) {
        super(name, Consumption.HERBIVORES);
    }

    @Override
    public void makeSound() {
        System.out.print("brays");
    }
}

package com.epam.training.oop.zoo.animals;

import com.epam.training.oop.zoo.Consumption;

public class Antelope extends Animal {
    public Antelope(String name) {
        super(name, Consumption.HERBIVORES);
    }

    @Override
    public void makeSound() {
        System.out.print("snorts");
    }
}

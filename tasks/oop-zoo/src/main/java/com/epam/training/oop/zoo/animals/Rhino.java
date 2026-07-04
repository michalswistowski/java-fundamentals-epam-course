package com.epam.training.oop.zoo.animals;

import com.epam.training.oop.zoo.Consumption;

public class Rhino extends Animal {
    public Rhino(String name) {
        super(name, Consumption.HERBIVORES);
    }

    @Override
    public void makeSound() {
        System.out.print("moos");
    }
}

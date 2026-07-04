package com.epam.training.oop.zoo;

import com.epam.training.oop.zoo.animals.Animal;

import java.util.Arrays;

public class Zookeeper {
    private final String name;
    private final Consumption[] consumption;

    public Zookeeper(String name, Consumption... consumption) {
        this.name = name;
        this.consumption = consumption;
    }


    public void feed(Animal[] animals) {
        Arrays.stream(consumption).forEach(consumption -> {
            Arrays.stream(animals)
                    .filter(animal ->
                            animal.getConsumption().equals(consumption))
                    .forEach(animal -> {
                        System.out.print(animal.getName() + " the " + animal.getClass().getSimpleName() + " ");
                        animal.makeSound();
                        System.out.print("\n");
                        System.out.println(name + " is feeding " + animal.getName() + " the " + animal.getClass().getSimpleName());
                    });
        });
    }

    public String getName() {
        return name;
    }
}

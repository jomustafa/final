package com.example.messagingstompwebsocket.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomGenerator<E> {

    private final Random generator;
    private final List<E> choices;

    public RandomGenerator(List<E> choices) {
        generator = new Random();
        this.choices = choices;
    }

    public RandomGenerator(E[] choices) {
        generator = new Random();
        this.choices = Arrays.asList(choices);
    }

    public E getRandomElement() {
        int index = generator.nextInt(choices.size());
        return choices.get(index);
    }
}


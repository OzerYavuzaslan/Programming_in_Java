package com.ozeryavuzaslan;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class PlayingWithOptional {
    public static void main(String[] args) {
        List<String> fruits = List.of("apple", "banana", "mango", "cherry");
        Predicate<? super String> predicate = fruit -> fruit.startsWith("c");
        Optional<String> optional = fruits.stream().filter(predicate).findFirst();
        System.err.println(optional);
        System.err.println(optional.isEmpty());
        System.err.println(optional.isPresent());
        System.err.println(optional.get());

        Optional<String> fruit = Optional.of("banana");
        Optional<String> empty = Optional.empty();

        System.err.println(fruit);
        System.err.println(empty);
    }
}
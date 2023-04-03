package com.ozeryavuzaslan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FP01Functional {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(1,4,6,8,9,11,22,33,44,11,1,4,6,66,9));
        ArrayList<String> arrayList2 = new ArrayList<>(Arrays.asList("Spring", "Spring Boot", "API", "Microservices",
                "AWS", "PCF", "AZURE", "DOCKER", "KUBERNETES"));

    //    printAllNumbersInListStructured(arrayList);
    //    printEvenNumbers(arrayList);
     //   printEvenNumbers2(arrayList);
    //    printSquaresOfEvenNumbers(arrayList);
        printCourses(arrayList2);
    }

    private static void print(int num){
        System.out.println(num);
    }

    private static void printAllNumbersInListStructured(List<Integer> numList){
        numList.stream().forEach(FP01Functional::print);
        numList.stream().forEach(System.out::println);
    }

    private static Boolean isEven(int num){
        return num % 2 == 0;
    }

    private static void printEvenNumbers(List<Integer> integerList){
        integerList
                .stream()
                .filter(FP01Functional::isEven)
                .forEach(System.out::println);
    }

    private static void printEvenNumbers2(List<Integer> integerList){
        List<Integer> newList = integerList
                .stream()
                .filter(i -> i % 2 == 0).toList();

        newList.stream().forEach(System.out::println);
    }

    private static void printSquaresOfEvenNumbers(List<Integer> integerList){
        integerList
                .stream()
                .filter(num -> num % 2 == 0)
                .map(num -> num * num)
                .forEach(System.out::println);
    }

    private static void printCourses(List<String> stringList){
        stringList.stream().filter(str -> str.length() > 7).forEach(System.out::println);
    }
}

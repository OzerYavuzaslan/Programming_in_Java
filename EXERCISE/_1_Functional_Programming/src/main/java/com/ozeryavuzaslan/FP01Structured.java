package com.ozeryavuzaslan;

import java.util.List;

public class FP01Structured {
    public static void main(String[] args) {
        printAllNumbersInListStructured(List.of(1,4,6,8,9,11,22,33,44,11,1,4,6,66,9));
    }

    private static void printAllNumbersInListStructured(List<Integer> numList){
        for (Integer integer : numList)
            System.out.println(integer);
    }
}

package ru.vinogor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStreams {
    public static void main(String[] args) {
        int[] array = {1, 4, 5, 9, 2, 5, 1, 1, 1, 1, 6, 4, 3, 5, 6};
        //     int[] array = {};
        System.out.println(minValue(array));

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);

        System.out.println(list);
        System.out.println(oddOrEven(list));
    }

    private static int minValue(int[] values) {
        return IntStream
                .of(values)
                .distinct()
                .sorted()
                .reduce((s1, s2) -> s1 * 10 + s2)
                .orElse(0);
    }


    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> oddAndEven =
                integers
                .stream()
                .collect(Collectors.partitioningBy((p) -> p % 2 == 0));
        return oddAndEven.get(oddAndEven.get(false).size() % 2 != 0);
    }
}
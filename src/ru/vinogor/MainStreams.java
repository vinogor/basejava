package ru.vinogor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStreams {
    public static void main(String[] args) {
        int[] array = {1, 4, 5, 9, 2, 5, 1, 1, 1, 1, 6, 4, 3, 5, 6};
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
                .getAsInt();
    }


    private static List<Integer> oddOrEven(List<Integer> integers) {

        List<Integer> notEven = integers   // notEven - все не чётные
                .stream()
                .filter(o -> (o % 2) != 0)
                .collect(Collectors.toList());

        integers.removeAll(notEven); // integers - все чётные

        if (notEven.size() %2 != 0) {    // то сумма чисел НЕ чётная, возвращаем чётные
            return integers;
        } else {
            return notEven;
        }
    }
}
package ru.Chernoviki.ZhirniToni;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Program {

    public static void main(String[] args) {

        System.out.println(new Incrementer() {
            @Override
            public int increment(int x) {
                return x + 1;
            }
        });

// ужали то, что было выше до лямбды:

        System.out.println((Incrementer) x -> x + 1);

// ==============================

//        Incrementer incrementer = new Incrementer() {
//            @Override
//            public int increment(int x) {
//                return x + 1;
//            }
//        };

     // тут типы не указываем т.к. они уже есть в интерфейсе

        Incrementer incrementer = x -> x + 1;
        System.out.println(incrementer.increment(1));

// ==============================

// что касается перебора элементов списка и ссылок на метод:

        // создали и заполнили список циферками
        ArrayList<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ints.add(3);

        // вывели все элементы, прибавляя к каждому +1
        ints.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                // а вот тут писать что проделывать с каждым эл-ом:
                System.out.println(integer + 1);
            }
        });

        // вышеописанное можно ужать используя Лямбду:

        ints.forEach(integer -> System.out.println(integer + 1));

// ==============================

        // а если и там и там одинаковый параметр и притом единственный,
        // то можно юзать ссылку на метод ::

        // было
        ints.forEach(integer -> System.out.println(integer));

        // стало (Класс, где лежит метод :: имя метода)
        ints.forEach(System.out::println);
    }
}

package org.kaczucha;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {

        List<String> list = List.of("wolf", "tiger", "turtle", "tortoise", "puma", "wolverine", "", "Adrian", "Agata");

        tests(list);


        System.out.println("task1:");
        task1(list);
        System.out.println("\ntask2:");
        task2(list);
        System.out.println("\ntask3:");
        task3(list);
        System.out.println("\ntask4:");
        task4(list);
        System.out.println("\ntask5:");
        task5(list);
        System.out.println("\ntask6:");
        task6(list);
        System.out.println("\ntask7:");
        task7(list);
        System.out.println("\ntask8:");
        task8(list);
        System.out.println("\ntask9:");
        task9(list);
        System.out.println("\ntask10:");
        task10(list);
        System.out.println("\ntask11:");
        task11(list);
        System.out.println("\ntask12:");
        task12(list);
    }

    private static void tests(List<String> list) {
        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.contains("a");
            }
        };

        Predicate<String> predicate1 = s -> {
            String newS = s + "elo";
            return newS.contains("a");
        };

        Predicate<String> predicate2 = s -> s.equals("a");

        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };

        Function<String, String> function = s -> s + "elo";
        Function<String, String> functionToLC = String::toLowerCase;

        BiFunction<String, String, String> biFunction = (s1, s2) -> s1 + s2;


        List<String> strings = list.stream()
                .map(String::toLowerCase)
                .filter(s -> s.contains("a"))
                .peek(consumer)
                .map(function)
                .collect(Collectors.toList());

        System.out.println(strings);
    }

    public static void task1(List<String> list) {
        //print all elements
    }

    public static void task2(List<String> list) {
        //print all elements in upper case
    }

    public static void task3(List<String> list) {
        //print only elements with 4 letters
    }

    public static void task4(List<String> list) {
        //print all elements sorted by number of letters
    }

    public static void task5(List<String> list) {
        //print all elements starting with 't'
    }

    public static void task6(List<String> list) {
        //print number of all elements starting with w
    }

    public static void task7(List<String> list) {
        //print "true" if any of the elements is empty, "false" otherwise
    }

    public static void task8(List<String> list) {
        //print "true" if all elements are empty, "false otherwise
    }

    public static void task9(List<String> list) {
        //print number of elements with more than 4 letters

    }

    public static void task10(List<String> list) {
        //print highest number of letters
    }

    public static void task11(List<String> list) {
        //print lowest number of letters
    }

    public static void task12(List<String> list) {
        //print sum of all numbers of letters
    }
}

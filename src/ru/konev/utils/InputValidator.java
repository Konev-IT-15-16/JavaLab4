package ru.konev.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputValidator {
    private final Scanner scanner;

    public InputValidator() {
        this.scanner = new Scanner(System.in);
    }

    // Ввод одного целого числа с проверкой
    public int getInt(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Ошибка! Введите целое число.");
            scanner.next(); // пропуск некорректного ввода
            System.out.print(message);
        }
        int result = scanner.nextInt();
        scanner.nextLine(); // очистка буфера
        return result;
    }

    // Ввод строки
    public String getString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    // Новые методы для ввода списков

    public List<Integer> getIntegerList(String prompt) {
        System.out.println(prompt);
        int count = getInt("Введите количество чисел: ");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(getInt("Введите число №" + (i + 1) + ": "));
        }
        return list;
    }

    public List<String> getStringList(String prompt) {
        System.out.println(prompt);
        int count = getInt("Введите количество строк: ");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(getString("Введите строку №" + (i + 1) + ": "));
        }
        return list;
    }
}
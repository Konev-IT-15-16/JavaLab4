package ru.konev.Main;

import java.util.*;
import ru.konev.model.Box;
import ru.konev.utils.InputValidator;
import ru.konev.utils.LabUtils;

public class Main {
    private static final InputValidator input = new InputValidator();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\nГЛАВНОЕ МЕНЮ");
            System.out.println("1. Задача 1.1: Интерактивная Коробка");
            System.out.println("2. Задача 2.2: Поиск максимума (N коробок)");
            System.out.println("3. Задача 3.1: Map (Функция)");
            System.out.println("4. Задача 3.2: Filter (Фильтр)");
            System.out.println("5. Задача 3.3: Reduce (Сокращение)");
            System.out.println("6. Задача 3.4: Collect (Коллекционирование)");
            System.out.println("0. Выход");

            int choice = input.getInt("Выберите пункт: ");

            switch (choice) {
                case 1 -> interactiveBox();
                case 2 -> interactiveMaxBoxes();
                case 3 -> demoMap();
                case 4 -> demoFilter();
                case 5 -> demoReduce();
                case 6 -> demoCollect();
                case 0 -> running = false;
                default -> System.out.println("Неверный пункт меню.");
            }
        }
    }

    // Задача 1: Интерактивная коробка
    private static void interactiveBox() {
        System.out.println("\n--- Режим работы с Коробкой (Integer) ---");
        Box<Integer> box = new Box<>();
        boolean inBoxMenu = true;

        while (inBoxMenu) {
            System.out.println("\n1. Положить число: ");
            System.out.println("2. Извлечь число: ");
            System.out.println("3. Посмотреть состояние: ");
            System.out.println("0. Вернуться в главное меню");

            int action = input.getInt("Действие: ");

            try {
                switch (action) {
                    case 1 -> {
                        int val = input.getInt("Введите значение: ");
                        box.put(val);
                        System.out.println("Число " + val + " положено в коробку.");
                    }
                    case 2 -> {
                        Integer val = box.get(); // Если пусто, вернет null (или логика Box может отличаться)
                        if (val == null) {
                            System.out.println("Коробка пуста.");
                        } else {
                            System.out.println("Извлечено значение: " + val);
                        }
                    }
                    case 3 -> System.out.println("Состояние: " + box);
                    case 0 -> inBoxMenu = false;
                    default -> System.out.println("Неверная команда.");
                }
            } catch (IllegalStateException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    // Задача 2: Поиск максимума
    private static void interactiveMaxBoxes() {
        System.out.println("\n Задача 2.2: Поиск Максимума");

        int count = input.getInt("Введите количество коробок: ");
        if (count <= 0) {
            System.out.println("Количество должно быть больше 0.");
            return;
        }

        List<Box<? extends Number>> boxes = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Box<Double> b = new Box<>();
            double val = (double) input.getInt("Введите число для коробки №" + (i + 1) + ": ");
            b.put(val);
            boxes.add(b);
        }

        // Вызываем метод из LabUtils
        double max = LabUtils.findMax(boxes);
        System.out.println("Максимальное значение среди " + count + " коробок: " + max);
    }

    // Задача 3.1: Map (С вводом данных)
    private static void demoMap() {
        System.out.println("\n Задача 3.1: Map");

        // Сценарий 1: Работа со строками
        System.out.println("[Тест 1] Преобразование строк в их длины");
        List<String> strings = input.getStringList("Введите список строк:");
        if (!strings.isEmpty()) {
            List<Integer> lengths = LabUtils.map(strings, String::length);
            System.out.println("Результат (длины): " + lengths);
        }

        // Сценарий 2: Работа с числами
        System.out.println("\n[Тест 2] Преобразование чисел в их модули");
        List<Integer> nums = input.getIntegerList("Введите список чисел (можно отрицательные):");
        if (!nums.isEmpty()) {
            List<Integer> absNums = LabUtils.map(nums, Math::abs);
            System.out.println("Результат: " + absNums);
        }
    }

    // Задача 3.2: Filter (С вводом данных)
    private static void demoFilter() {
        System.out.println("\n Задача 3.2: Filter");

        System.out.println("[Тест 1] Оставить строки длиннее 3 символов");
        List<String> strings = input.getStringList("Введите список строк:");
        List<String> filteredStrs = LabUtils.filter(strings, s -> s.length() > 3);
        System.out.println("Результат: " + filteredStrs);

        System.out.println("\n[Тест 2] Оставить только отрицательные числа");
        List<Integer> nums = input.getIntegerList("Введите список чисел:");
        List<Integer> negatives = LabUtils.filter(nums, n -> n < 0);
        System.out.println("Результат: " + negatives);
    }

    // Задача 3.3: Reduce (С вводом данных)
    private static void demoReduce() {
        System.out.println("\n Задача 3.3: Reduce");

        System.out.println("[Тест 1] Объединение всех строк в одну");
        List<String> strings = input.getStringList("Введите строки:");
        String resultStr = LabUtils.reduce(strings, (s1, s2) -> s1 + s2, "");
        System.out.println("Результат: " + resultStr);

        System.out.println("\n[Тест 2] Сумма всех чисел");
        List<Integer> nums = input.getIntegerList("Введите числа:");
        int sum = LabUtils.reduce(nums, Integer::sum, 0);
        System.out.println("Сумма: " + sum);
    }

    // Задача 3.4: Collect (С вводом данных)
    private static void demoCollect() {
        System.out.println("\n Задача 3.4: Collect");

        System.out.println("[Тест 1] Разбить числа на положительные и отрицательные");
        List<Integer> nums = input.getIntegerList("Введите числа (смешанные):");

        Map<Boolean, List<Integer>> splitMap = LabUtils.collect(
                nums,
                HashMap::new,
                (map, item) -> {
                    // Ключ true для положительных (>0), false для остальных
                    map.computeIfAbsent(item > 0, k -> new ArrayList<>()).add(item);
                }
        );
        System.out.println("Положительные (>0): " + splitMap.getOrDefault(true, Collections.emptyList()));
        System.out.println("Остальные (<=0): " + splitMap.getOrDefault(false, Collections.emptyList()));
    }
}
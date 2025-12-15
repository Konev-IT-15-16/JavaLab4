package ru.konev.utils;

import ru.konev.model.Box;
import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class LabUtils {

    // Задание 2.2: Поиск максимума

    //Находит максимальное значение среди коробок с числами.
    public static double findMax(List<Box<? extends Number>> boxes) {
        double max = Double.NEGATIVE_INFINITY;
        for (Box<? extends Number> box : boxes) {
            if (box.isFull()) {
                // Извлекаем значение
                double val = box.get().doubleValue();
                if (val > max) {
                    max = val;
                }
            }
        }
        return max;
    }

    // Задание 3: Обобщенные методы

    // 3.1 Функция (Map). Применяет функцию к каждому элементу списка.
    public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        List<R> result = new ArrayList<>();
        for (T item : list) {
            result.add(mapper.apply(item));
        }
        return result;
    }

    // 3.2 Фильтр. Возвращает список только с элементами, прошедшими проверку.
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    // 3.3 Сокращение (Reduce). Сводит список к одному значению.
    // identity - начальное значение (чтобы не возвращать null для пустого списка).
    public static <T> T reduce(List<T> list, BinaryOperator<T> accumulator, T identity) {
        T result = identity;
        for (T item : list) {
            result = accumulator.apply(result, item);
        }
        return result;
    }

    // 3.4 Коллекционирование.
    // Создает коллекцию типа P и заполняет её данными из списка.
    public static <T, P> P collect(List<T> list, Supplier<P> collectionFactory, BiConsumer<P, T> accumulator) {
        P collection = collectionFactory.get(); // Создаем пустую коллекцию
        for (T item : list) {
            accumulator.accept(collection, item); // Передаем значение
        }
        return collection;
    }
}
package ru.konev.model;

// Интерфейс для сравнения объектов.
public interface MyComparable<T> {
    int compare(T other);
}
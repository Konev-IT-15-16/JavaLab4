package ru.konev.model;

// Обобщенная коробка, хранящая один объект.
public class Box<T> {
    private T item;

    // Положить объект в коробку.
    public void put(T item) {
        if (this.item != null) {
            throw new IllegalStateException("Коробка уже заполнена!");
        }
        this.item = item;
    }

    // Извлечь объект из коробки.
    public T get() {
        T value = this.item;
        this.item = null; // Важно: обнуляем ссылку
        return value;
    }

    // Проверка на заполненность.
    public boolean isFull() {
        return this.item != null;
    }

    @Override
    public String toString() {
        return "Box {" + item + '}';
    }
}
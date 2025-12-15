```markdown
<h1>Лабораторная работа №4</h1>
<h2>
  <p>Конов Михаил</p>
  <p>ПР-7</p>
  <p>2 вариант</p>
</h2>

<h2>Цель работы</h2>
<p>Освоение работы с обобщенными типами (generics) в Java, включая создание параметризованных классов, интерфейсов, методов с ограничениями типов, а также реализация типизированных утилитарных методов для обработки коллекций.</p>

<h2>Задание 1.1: Обобщенная коробка</h2>
<h3>Условие</h3>
<p>Создать обобщенный класс <code>Box</code>, который может хранить один объект произвольного типа. Реализовать методы для размещения и извлечения объекта с проверкой состояния коробки.</p>

<h3>Решение</h3>
<p>Класс <code>Box</code> содержит:</p>
<ul>
    <li>Приватное поле <code>T item</code> для хранения объекта</li>
    <li>Метод <code>put(T item)</code> – размещает объект, если коробка пуста, иначе выбрасывает исключение</li>
    <li>Метод <code>T get()</code> – извлекает объект и обнуляет ссылку</li>
    <li>Метод <code>isFull()</code> – возвращает true, если коробка содержит объект</li>
    <li>Переопределенный <code>toString()</code> для вывода состояния</li>
</ul>

<h3>Исходный код</h3>
<pre><code>package ru.konev.model;

public class Box<T> {
    private T item;

    public void put(T item) {
        if (this.item != null) {
            throw new IllegalStateException("Коробка уже заполнена!");
        }
        this.item = item;
    }

    public T get() {
        T value = this.item;
        this.item = null;
        return value;
    }

    public boolean isFull() {
        return this.item != null;
    }

    @Override
    public String toString() {
        return "Box {" + item + '}';
    }
}</code></pre>

<h3>Пример использования</h3>
<pre><code>Box<Integer> box = new Box<>();
box.put(3);                         // Успешно
System.out.println(box.get());      // 3
System.out.println(box.isFull());   // false

box.put(5);
box.put(10);                        // IllegalStateException: "Коробка уже заполнена!"</code></pre>

<div align="center">
  <img src="./screenshots/Box.png" alt="Демонстрация работы с коробкой" width="600"/>
</div>

<h2>Задание 1.3: Сравнимое</h2>
<h3>Условие</h3>
<p>Создать интерфейс <code>MyComparable&lt;T&gt;</code> с методом <code>compare(T other)</code>, возвращающим целое число.</p>

<h3>Решение</h3>
<pre><code>package ru.konev.model;

public interface MyComparable<T> {
    int compare(T other);
}</code></pre>

<h3>Пример реализации</h3>
<pre><code>public class Student implements MyComparable<Student> {
    private double averageGrade;
    
    @Override
    public int compare(Student other) {
        return Double.compare(this.averageGrade, other.averageGrade);
    }
}</code></pre>

<h2>Задание 2.2: Поиск максимума</h2>
<h3>Условие</h3>
<p>Создать метод, принимающий набор <code>Box</code> из задачи 1.1 и возвращающий максимальное из их значений в формате <code>double</code>. Коробки могут быть параметризованы любыми типами чисел.</p>

<h3>Решение</h3>
<p>Метод реализован в классе <code>LabUtils</code>:</p>
<pre><code>package ru.konev.utils;

import ru.konev.model.Box;
import java.util.List;

public class LabUtils {
    public static double findMax(List<Box<? extends Number>> boxes) {
        double max = Double.NEGATIVE_INFINITY;
        for (Box<? extends Number> box : boxes) {
            if (box.isFull()) {
                double val = box.get().doubleValue();
                if (val > max) {
                    max = val;
                }
            }
        }
        return max;
    }
}</code></pre>

<h3>Пример использования</h3>
<pre><code>Box<Double> box1 = new Box<>();
Box<Integer> box2 = new Box<>();
Box<Float> box3 = new Box<>();

box1.put(15.7);
box2.put(42);
box3.put(3.14f);

List<Box<? extends Number>> boxes = List.of(box1, box2, box3);
double max = LabUtils.findMax(boxes);  // 42.0</code></pre>

<div align="center">
  <img src="./screenshots/FindMax.png" alt="Поиск максимума" width="600"/>
</div>

<h2>Задание 3.1: Функция (Map)</h2>
<h3>Условие</h3>
<p>Разработать метод, который принимает список значений типа <code>T</code> и функцию <code>apply</code>, применяет её к каждому элементу и возвращает новый список значений типа <code>R</code>.</p>

<h3>Решение</h3>
<pre><code>public static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
    List<R> result = new ArrayList<>();
    for (T item : list) {
        result.add(mapper.apply(item));
    }
    return result;
}</code></pre>

<h3>Примеры использования</h3>
<pre><code>// 1. Строки → длины
List<String> strings = List.of("qwerty", "asdfg", "zx");
List<Integer> lengths = LabUtils.map(strings, String::length);
// Результат: [6, 5, 2]

// 2. Числа → модули
List<Integer> nums = List.of(1, -3, 7);
List<Integer> absValues = LabUtils.map(nums, Math::abs);
// Результат: [1, 3, 7]

// 3. Массивы → максимальные значения
List<int[]> arrays = List.of(new int[]{1,2,3}, new int[]{-1,0,5});
List<Integer> maxValues = LabUtils.map(arrays, 
    arr -> Arrays.stream(arr).max().orElse(0));
// Результат: [3, 5]</code></pre>

<h2>Задание 3.2: Фильтр</h2>
<h3>Условие</h3>
<p>Разработать метод, принимающий список значений типа <code>T</code> и предикат <code>test</code>, возвращающий новый список только с элементами, прошедшими проверку.</p>

<h3>Решение</h3>
<pre><code>public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
    List<T> result = new ArrayList<>();
    for (T item : list) {
        if (predicate.test(item)) {
            result.add(item);
        }
    }
    return result;
}</code></pre>

<h3>Примеры использования</h3>
<pre><code>// 1. Строки длиннее 2 символов
List<String> strings = List.of("qwerty", "asdfg", "zx");
List<String> longStrings = LabUtils.filter(strings, s -> s.length() > 2);
// Результат: ["qwerty", "asdfg"]

// 2. Отрицательные числа
List<Integer> nums = List.of(1, -3, 7);
List<Integer> negatives = LabUtils.filter(nums, n -> n < 0);
// Результат: [-3]

// 3. Массивы без положительных элементов
List<int[]> arrays = List.of(new int[]{-1,-2}, new int[]{1,-3});
List<int[]> noPositive = LabUtils.filter(arrays, 
    arr -> Arrays.stream(arr).allMatch(n -> n <= 0));
// Результат: [[-1,-2]]</code></pre>

<h2>Задание 3.3: Сокращение (Reduce)</h2>
<h3>Условие</h3>
<p>Разработать метод, сводящий список значений типа <code>T</code> к одному значению того же типа с помощью операции аккумуляции. Метод должен корректно обрабатывать пустые списки.</p>

<h3>Решение</h3>
<pre><code>public static <T> T reduce(List<T> list, BinaryOperator<T> accumulator, T identity) {
    T result = identity;
    for (T item : list) {
        result = accumulator.apply(result, item);
    }
    return result;
}</code></pre>

<h3>Примеры использования</h3>
<pre><code>// 1. Конкатенация строк
List<String> strings = List.of("qwerty", "asdfg", "zx");
String concatenated = LabUtils.reduce(strings, (s1, s2) -> s1 + s2, "");
// Результат: "qwertyasdfgzx"

// 2. Сумма чисел
List<Integer> nums = List.of(1, -3, 7);
int sum = LabUtils.reduce(nums, Integer::sum, 0);
// Результат: 5

// 3. Общее количество элементов во вложенных списках
List<List<Integer>> nested = List.of(
    List.of(1,2,3), 
    List.of(4,5), 
    List.of(6,7,8,9)
);
int totalCount = LabUtils.reduce(
    LabUtils.map(nested, List::size),  // Сначала получаем длины
    Integer::sum, 
    0
);
// Результат: 9</code></pre>

<h2>Задание 3.4: Коллекционирование</h2>
<h3>Условие</h3>
<p>Разработать метод, который создает и возвращает коллекцию типа <code>P</code>, заполняя её значениями из исходного списка типа <code>T</code> с помощью заданных функций.</p>

<h3>Решение</h3>
<pre><code>public static <T, P> P collect(List<T> list,
                                Supplier<P> collectionFactory,
                                BiConsumer<P, T> accumulator) {
    P collection = collectionFactory.get();
    for (T item : list) {
        accumulator.accept(collection, item);
    }
    return collection;
}</code></pre>

<h3>Примеры использования</h3>
<pre><code>// 1. Разделение чисел на положительные и отрицательные
List<Integer> nums = List.of(1, -3, 7, -2, 0);
Map<Boolean, List<Integer>> split = LabUtils.collect(
    nums,
    HashMap::new,
    (map, item) -> map.computeIfAbsent(item > 0, k -> new ArrayList<>()).add(item)
);
// Результат: {true=[1, 7], false=[-3, -2, 0]}

// 2. Группировка строк по длине
List<String> strings = List.of("qwerty", "asdfg", "zx", "qw");
Map<Integer, List<String>> byLength = LabUtils.collect(
    strings,
    HashMap::new,
    (map, item) -> map.computeIfAbsent(item.length(), k -> new ArrayList<>()).add(item)
);
// Результат: {6=["qwerty"], 5=["asdfg"], 2=["zx", "qw"]}

// 3. Удаление дубликатов через Set
List<String> withDuplicates = List.of("qwerty", "asdfg", "qwerty", "qw");
Set<String> unique = LabUtils.collect(
    withDuplicates,
    HashSet::new,
    Set::add
);
// Результат: ["qwerty", "asdfg", "qw"] (порядок может быть другим)</code></pre>

<h2>Реализация пользовательского интерфейса</h2>
<h3>Класс InputValidator</h3>
<p>Для удобного ввода данных создан класс <code>InputValidator</code> с методами:</p>
<pre><code>public int getInt(String message)                    // ввод целого числа
public String getString(String message)              // ввод строки
public List<Integer> getIntegerList(String prompt)   // ввод списка чисел
public List<String> getStringList(String prompt)     // ввод списка строк</code></pre>

<h3>Главное меню</h3>
<pre><code>ГЛАВНОЕ МЕНЮ
1. Задача 1.1: Интерактивная Коробка
2. Задача 2.2: Поиск максимума (N коробок)
3. Задача 3.1: Map (Функция)
4. Задача 3.2: Filter (Фильтр)
5. Задача 3.3: Reduce (Сокращение)
6. Задача 3.4: Collect (Коллекционирование)
0. Выход</code></pre>

<div align="center">
  <img src="./screenshots/MainMenu.png" alt="Главное меню" width="600"/>
</div>

<h2>Вывод</h2>
<p>В ходе выполнения лабораторной работы были успешно реализованы:</p>
<ol>
    <li><strong>Обобщенный класс Box</strong> – для типобезопасного хранения одиночных объектов</li>
    <li><strong>Интерфейс MyComparable</strong> – для определения контракта сравнения объектов</li>
    <li><strong>Метод findMax</strong> – работающий с коробками любых числовых типов через ограничение wildcard</li>
    <li><strong>Четыре обобщенных утилитарных метода</strong> (map, filter, reduce, collect) для функциональной обработки коллекций</li>
    <li><strong>Интерактивный интерфейс</strong> с проверкой ввода для демонстрации всех возможностей</li>
</ol>

<p>Все задачи варианта 2 выполнены в полном объеме с соблюдением требований типизации, обработки граничных случаев и обеспечения удобства использования.</p>
```

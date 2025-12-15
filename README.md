<h1>Лабораторная работа №4</h1>
<h2>
  <p>Конов Михаил</p>
  <p>ПР-7</p>
  <p>2 вариант</p>
</h2>

<h2>Задание 1.1: Обобщенная коробка</h2>
<h3>Условие</h3>
<p>Создать обобщенный класс <code>Box</code>, который может хранить один объект произвольного типа. Реализовать методы для размещения и извлечения объекта с проверкой состояния коробки.</p>

<h3>Решение</h3>
<p>Класс <code>Box</code> содержит:</p>
<ul>
    <li>Поле <code>item</code> типа <code>T</code></li>
    <li>Метод <code>put(T item)</code> – размещает объект, если коробка пуста</li>
    <li>Метод <code>T get()</code> – извлекает объект и обнуляет ссылку</li>
    <li>Метод <code>isFull()</code> – проверяет, заполнена ли коробка</li>
    <li>Переопределенный <code>toString()</code></li>
</ul>

<h3>Пример использования</h3>
<pre><code>Box&lt;Integer&gt; box = new Box&lt;&gt;();
box.put(3);
System.out.println(box.get()); // 3
System.out.println(box.isFull()); // false</code></pre>

<h2>Задание 1.3: Сравнимое</h2>
<h3>Условие</h3>
<p>Создать интерфейс <code>MyComparable&lt;T&gt;</code> с методом <code>compare(T other)</code>, возвращающим целое число.</p>

<h3>Решение</h3>
<pre><code>public interface MyComparable&lt;T&gt; {
    int compare(T other);
}</code></pre>

<h2>Задание 2.1: Сдвинуть линию</h2>
<h3>Условие</h3>
<p>Создать метод, принимающий объект <code>Line</code> (из задачи 2.6.3) и сдвигающий начальную точку на 10 единиц по оси X.</p>

<h3>Решение</h3>
<pre><code>public static void shiftLine(Line&lt;?&gt; line) {
    // Предполагается, что у Line есть метод getStart() с координатой x
    // и метод setStart() для обновления
    Point start = line.getStart();
    start.setX(start.getX() + 10);
}</code></pre>

<h2>Задание 2.2: Поиск максимума</h2>
<h3>Условие</h3>
<p>Реализовать метод, принимающий список <code>Box</code> с числами и возвращающий максимальное значение в формате <code>double</code>.</p>

<h3>Решение</h3>
<p>В классе <code>LabUtils</code> реализован метод:</p>
<pre><code>public static double findMax(List&lt;Box&lt;? extends Number&gt;&gt; boxes) {
    double max = Double.NEGATIVE_INFINITY;
    for (Box&lt;? extends Number&gt; box : boxes) {
        if (box.isFull()) {
            double val = box.get().doubleValue();
            if (val > max) max = val;
        }
    }
    return max;
}</code></pre>

<h3>Пример использования</h3>
<pre><code>List&lt;Box&lt;? extends Number&gt;&gt; boxes = Arrays.asList(box1, box2, box3);
double maxValue = LabUtils.findMax(boxes);
System.out.println("Максимальное значение: " + maxValue);</code></pre>

<h2>Задание 3.1: Функция (Map)</h2>
<h3>Условие</h3>
<p>Реализовать обобщенный метод <code>map</code>, применяющий заданную функцию к каждому элементу списка и возвращающий новый список.</p>

<h3>Решение</h3>
<pre><code>public static &lt;T, R&gt; List&lt;R&gt; map(List&lt;T&gt; list, Function&lt;T, R&gt; mapper) {
    List&lt;R&gt; result = new ArrayList&lt;&gt;();
    for (T item : list) {
        result.add(mapper.apply(item));
    }
    return result;
}</code></pre>

<h3>Пример использования</h3>
<pre><code>List&lt;String&gt; strings = Arrays.asList("qwerty", "asdfg", "zx");
List&lt;Integer&gt; lengths = LabUtils.map(strings, String::length);
// Результат: [6, 5, 2]</code></pre>

<h2>Задание 3.2: Фильтр</h2>
<h3>Условие</h3>
<p>Реализовать обобщенный метод <code>filter</code>, возвращающий список элементов, удовлетворяющих условию.</p>

<h3>Решение</h3>
<pre><code>public static &lt;T&gt; List&lt;T&gt; filter(List&lt;T&gt; list, Predicate&lt;T&gt; predicate) {
    List&lt;T&gt; result = new ArrayList&lt;&gt;();
    for (T item : list) {
        if (predicate.test(item)) {
            result.add(item);
        }
    }
    return result;
}</code></pre>

<h3>Пример использования</h3>
<pre><code>List&lt;Integer&gt; nums = Arrays.asList(1, -3, 7);
List&lt;Integer&gt; negatives = LabUtils.filter(nums, n -> n < 0);
// Результат: [-3]</code></pre>

<h2>Задание 3.3: Сокращение (Reduce)</h2>
<h3>Условие</h3>
<p>Реализовать метод <code>reduce</code>, сводящий список к одному значению с помощью аккумулятора.</p>

<h3>Решение</h3>
<pre><code>public static &lt;T&gt; T reduce(List&lt;T&gt; list, BinaryOperator&lt;T&gt; accumulator, T identity) {
    T result = identity;
    for (T item : list) {
        result = accumulator.apply(result, item);
    }
    return result;
}</code></pre>

<h3>Пример использования</h3>
<pre><code>List&lt;String&gt; strings = Arrays.asList("qwerty", "asdfg", "zx");
String concatenated = LabUtils.reduce(strings, (s1, s2) -> s1 + s2, "");
// Результат: "qwertyasdfgzx"</code></pre>

<h2>Задание 3.4: Коллекционирование (Collect)</h2>
<h3>Условие</h3>
<p>Реализовать метод <code>collect</code>, преобразующий список в коллекцию произвольного типа.</p>

<h3>Решение</h3>
<pre><code>public static &lt;T, P&gt; P collect(List&lt;T&gt; list,
                                Supplier&lt;P&gt; collectionFactory,
                                BiConsumer&lt;P, T&gt; accumulator) {
    P collection = collectionFactory.get();
    for (T item : list) {
        accumulator.accept(collection, item);
    }
    return collection;
}</code></pre>

<h3>Пример использования</h3>
<pre><code>List&lt;Integer&gt; nums = Arrays.asList(1, -3, 7);
Map&lt;Boolean, List&lt;Integer&gt;&gt; split = LabUtils.collect(
    nums,
    HashMap::new,
    (map, item) -> map.computeIfAbsent(item > 0, k -> new ArrayList<>()).add(item)
);
// Результат: {true=[1, 7], false=[-3]}</code></pre>

<h2>Реализация ввода и интерфейса</h2>
<p>Для удобного взаимодействия с пользователем реализован класс <code>InputValidator</code> с методами:</p>
<ul>
    <li><code>getInt(String message)</code> – ввод целого числа с проверкой</li>
    <li><code>getString(String message)</code> – ввод строки</li>
    <li><code>getIntegerList(String prompt)</code> – ввод списка чисел</li>
    <li><code>getStringList(String prompt)</code> – ввод списка строк</li>
</ul>

<h2>Главное меню</h2>
<p>В классе <code>Main</code> реализовано интерактивное меню для демонстрации всех задач:</p>
<pre><code>1. Задача 1.1: Интерактивная Коробка
2. Задача 2.2: Поиск максимума (N коробок)
3. Задача 3.1: Map (Функция)
4. Задача 3.2: Filter (Фильтр)
5. Задача 3.3: Reduce (Сокращение)
6. Задача 3.4: Collect (Коллекционирование)
0. Выход</code></pre>

<h2>Вывод</h2>
<p>В ходе выполнения лабораторной работы были изучены и применены обобщенные типы в Java, реализованы параметризованные классы и методы, а также создан удобный интерактивный интерфейс для демонстрации функциональности. Все задачи варианта 2 выполнены в соответствии с требованиями.</p>

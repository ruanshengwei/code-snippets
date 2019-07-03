package com.github.ruanshengwei.javalang.java8.stream;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Intermediate：一个流可以后面跟随零个或多个 intermediate 操作。其目的主要是打开流，做出某种程度的数据映射/过滤，
 * 然后返回一个新的流，交给下一个操作使用。这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
 *
 * 【map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered】
 *
 *
 * Terminal：一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。
 * 所以这必定是流的最后一个操作。Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。
 *
 * 【forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator】
 *
 * short-circuiting
 * 对于一个 intermediate 操作，如果它接受的是一个无限大（infinite/unbounded）的 Stream，但返回一个有限的新 Stream。
 * 对于一个 terminal 操作，如果它接受的是一个无限大的 Stream，但能在有限的时间计算出结果。
 *
 * 【anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit】
 *
 */
public class SimpleStreamDemo {

    @Test
    public void ConstructorStream(){
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");
        stream.forEach(System.out::println);
        // 2. Arrays
        String [] strArray = new String[] {"a", "b", "c"};
        stream = Stream.of(strArray);
        stream.forEach(System.out::println);
        stream = Arrays.stream(strArray);
        stream.forEach(System.out::println);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();

        stream.forEach(System.out::println);
    }

    @Test
    public void NumberConstructorStream(){
        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
    }

    @Test
    public void StreamConvert(){
        // 1. Array
        Stream stream = Stream.of("one","two","three");
        String[] strings = (String[]) stream.toArray(String[]::new);
        System.out.println(strings.length);

        // 2. Collection
        stream = Stream.of("one","two","three");
        List<String> list1 = (List<String>) stream.collect(Collectors.toList());
        System.out.println("list1:"+list1);

        stream = Stream.of("one","two","three");
        List<String> list2 = (List<String>) stream.collect(Collectors.toCollection(ArrayList::new));
        System.out.println("list2:"+list2);

        stream = Stream.of("one","two","three");
        Set set1 = (Set) stream.collect(Collectors.toSet());
        System.out.println("set1:"+set1);

        stream = Stream.of("one","two","three");
        Stack stack1 = (Stack) stream.collect(Collectors.toCollection(Stack::new));
        System.out.println("stack1:"+stack1);

        // 3. String
        stream = Stream.of("one","two","three");
        String str = stream.collect(Collectors.joining()).toString();
        System.out.println("str:"+str);
    }

    @Test
    public void mapAndFlatMap(){
        List<String> wordList = Arrays.asList("one","two","three");

        wordList.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        List<Integer> numberList = Arrays.asList(1,2,3);
        numberList.stream()
                .map(n->n*n)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );

        inputStream.forEach(System.out::println);

        inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );

        //一对多 flatMap 把 input Stream 中的层级结构扁平化，就是将最底层元素抽出来放到一起，最终 output 的新 Stream 里面已经没有 List 了，都是直接的数字。
        inputStream.flatMap((childList) -> childList.stream()).forEach(System.out::println);
    }

    @Test
    public void filter(){
        Integer[] sixNums = {1,2,3,4,5,6};
        Stream.of(sixNums).filter(n->n%2==0).forEach(System.out::println);

        List<String> list = Arrays.asList("one","two","three");
        Stream.of(list)
                .flatMap((childList) -> childList.stream())
                .filter(strings -> strings.equals("two")||strings.equals("three"))
                .forEach(System.out::println);
    }

    @Test
    public void forEach(){
        List<String> list = Arrays.asList("one","two","three");

        Stream.of(list).forEach(e->{
            System.out.println("doSomethings");
            System.out.println(e.size());
        });
    }

    @Test
    public void peek(){
        String[] strings = new String[]{"one","two","three"};

        //如果只有 Intermediate 是不会有输出的,因为没有通过 Terminal 开始流真正的遍历
        Stream.of(strings)
                .peek(e->System.out.println(e));

        Stream.of(strings)
                .peek(e->System.out.println(e))
                .collect(Collectors.toList());
    }

    @Test
    public void findFirst(){
        String[] strings = new String[]{"one","two","three"};
        Optional<String> optionalS = Stream.of(strings).findFirst();

        System.out.println(optionalS.get());
    }

    @Test
    public void reduce(){
        String[] strings = new String[]{"one","two","three"};

        String result = Stream.of(strings)
                .reduce("BEGIN",(a,b)->a+b);
        System.out.println(result);

        // 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        // 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        // 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        // 过滤，字符串连接，concat = "ace"
        String concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);
        System.out.println(concat);
    }

    @Test
    public void limitAndSkip(){
        testLimitAndSkip();
        //依旧是比较了很多次。并没有起到limit(2)的作用。
        testLimitAndSkip2();
        //把limit(2) 提到前面就有效果了
        testLimitAndSkip3();
    }

    private void testLimitAndSkip3() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<Person> personList2 = persons.stream().limit(2).sorted((p1, p2) ->
                p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
        System.out.println(personList2);
    }

    private void testLimitAndSkip2() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 5; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<Person> personList2 = persons.stream().sorted((p1, p2) ->
                p1.getName().compareTo(p2.getName())).limit(2).collect(Collectors.toList());
        System.out.println(personList2);
    }

    public void testLimitAndSkip() {
        List<Person> persons = new ArrayList();
        for (int i = 1; i <= 10000; i++) {
            Person person = new Person(i, "name" + i);
            persons.add(person);
        }
        List<String> personList2 = persons.stream().
                map(Person::getName).limit(10).skip(3).collect(Collectors.toList());
        System.out.println(personList2);
    }
    private class Person {
        public int no;
        private String name;
        private int age;
        public Person (int no, String name,int age) {
            this.no = no;
            this.name = name;
            this.age = age;
        }
        public Person (int no, String name) {
            this.no = no;
            this.name = name;
        }
        public String getName() {
            System.out.println(name);
            return name;
        }

        public int getNo() {
            return no;
        }

        public int getAge() {
            return age;
        }
    }

    @Test
    public void minMaxDistinct(){

    }

    @Test
    public void match(){
        List<Person> persons = new ArrayList();
        persons.add(new Person(1, "name" + 1, 10));
        persons.add(new Person(2, "name" + 2, 21));
        persons.add(new Person(3, "name" + 3, 34));
        persons.add(new Person(4, "name" + 4, 6));
        persons.add(new Person(5, "name" + 5, 55));
        boolean isAllAdult = persons.stream().
                allMatch(p -> p.getAge() > 18);
        System.out.println("All are adult? " + isAllAdult);
        boolean isThereAnyChild = persons.stream().
                anyMatch(p -> p.getAge() < 12);
        System.out.println("Any child? " + isThereAnyChild);

    }

    @Test
    public void collectorsListToMap(){
        List<Person> persons = new ArrayList();
        persons.add(new Person(1, "name" + 1, 10));
        persons.add(new Person(2, "name" + 2, 21));
        persons.add(new Person(3, "name" + 3, 34));
        persons.add(new Person(4, "name" + 4, 6));
        persons.add(new Person(5, "name" + 5, 55));

        Map<String,Person> map = persons.stream().collect(Collectors.toMap(a->{
            return String.valueOf(a.getNo());
        },a->a));

        System.out.println(map);
    }
}

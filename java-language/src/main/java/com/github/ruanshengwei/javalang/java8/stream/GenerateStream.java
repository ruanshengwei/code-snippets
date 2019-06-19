package com.github.ruanshengwei.javalang.java8.stream;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GenerateStream {

    @Test
    public void random(){
        Random seed = new Random();
        Supplier<Integer> random = seed::nextInt;
        Stream.generate(random).limit(10).forEach(System.out::println);
        //Another way
        IntStream.generate(() -> (int) (System.nanoTime() % 100)).
                limit(10).forEach(System.out::println);
    }

    @Test
    public void mySupplier(){
        Stream.generate(new PersonSupplier()).
                limit(10).
                forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));
    }

    private class PersonSupplier implements Supplier<Person> {
        private int index = 0;
        private Random random = new Random();
        @Override
        public Person get() {
            return new Person(index++, "StormTestUser" + index, random.nextInt(100));
        }
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

    //iterate 跟 reduce 操作很像，接受一个种子值，和一个 UnaryOperator（例如 f）。
    // 然后种子值成为 Stream 的第一个元素，f(seed) 为第二个，f(f(seed)) 第三个，以此类推。
    @Test
    public void iterate(){
        Stream.iterate(0, n -> n + 3).limit(10). forEach(x -> System.out.println(x + " "));
    }

    @Test
    public void collectorsReduction(){
        Map<Integer, List<Person>> personGroups = Stream.generate(new PersonSupplier()).
                limit(100).
                collect(Collectors.groupingBy(Person::getAge));
        Iterator it = personGroups.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, List<Person>> persons = (Map.Entry) it.next();
            System.out.println("Age " + persons.getKey() + " = " + persons.getValue().size());
        }

        Map<Boolean, List<Person>> children = Stream.generate(new PersonSupplier()).
                limit(100).
                collect(Collectors.partitioningBy(p -> p.getAge() < 18));
        System.out.println("Children number: " + children.get(true).size());
        System.out.println("Adult number: " + children.get(false).size());
    }
}

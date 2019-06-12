package com.github.ruanshengwei.javalang.reference;

import java.lang.ref.WeakReference;

/**
 * WeakReference 测试
 */
public class WeakReferenceDemo {

    public static void main(String[] args) {
        Car car = new Car(22000,"silver");
        WeakReference<Car> weakCar = new WeakReference<Car>(car);
        int i=0;
        while(true){
            // 即使有 car 引用指向对象, 且 car 是一个strong reference, weak reference weakCar指向的对象仍然被回收了.
            // 线程执行到此时。栈桢中已经抛出该引用
            if(weakCar.get()!=null){
                i++;
                System.out.println("Object is alive for "+i+" loops - "+weakCar);
            }else{
                System.out.println("Object has been collected.");
                break;
            }
        }
    }

    static class Car {
        private double price;
        private String colour;

        public Car(double price, String colour) {
            this.price = price;
            this.colour = colour;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        @Override
        public String toString() {
            return colour + "car costs $" + price;

        }
    }
}

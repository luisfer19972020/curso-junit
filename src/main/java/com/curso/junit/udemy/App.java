package com.curso.junit.udemy;

import java.math.BigDecimal;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        String a = "a";
        String b = "b";
        String c = "c";
        System.out.println("Comparo B con A y da:");
        System.out.println(b.compareTo(a));
        System.out.println("Comparo B con B y da:");
        System.out.println(b.compareTo(b));
        System.out.println("Comparo B con C y da:");
        System.out.println(b.compareTo(c));
       
        BigDecimal num1 = new BigDecimal("1");
        BigDecimal num2 = new BigDecimal("2");
        BigDecimal num3 = new BigDecimal("3");
        System.out.println("Comparo 2 con 1 y da:");
        System.out.println(num2.compareTo(num1));
        System.out.println("Comparo 2 con 2 y da:");
        System.out.println(num2.compareTo(num2));
        System.out.println("Comparo 2 con 3 y da:");
        System.out.println(num2.compareTo(num3));
       
       
    }
}

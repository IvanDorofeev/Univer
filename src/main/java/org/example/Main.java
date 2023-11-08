package org.example;

public class Main {
    public static void main(String[] args) {
        try{
            System.out.println(new Calculator("-57.675+ 78*( ((56+1) + 1) /2 +5)-2").calculate());

            System.out.println(new Calculator("57.675+ 78*( ((56+1) + 1) /2 +5)-2").calculate());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
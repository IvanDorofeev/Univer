package org.example;

public class Main {
    public static void main(String[] args) {
        try{
            System.out.println(new Calculator("-57.675+ 78*( ((56+1) + 1) /2 +5)-2").calculate());

            System.out.println(new Calculator("57.675+ 78*( ((56+1) + 1) /2 +5)-2").calculate());
            System.out.println(new Calculator("1.23+ 79* ((39+1) -5) -3").calculate());

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
package org.example;

import static org.junit.Assert.*;

public class CalculatorTest {

    @org.junit.Test
    public void standardView() {
        Calculator calculator = new Calculator();
        String actual = calculator.standardView("-57.675+ 78*( ((56+1) + 1) /2 +5)-2");
        String expected = "-57.675 + (78 * ((((56 + 1 ) + 1 ) / 2 ) + 5 ) ) - 2";
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void isCorrect() {
        Calculator calculator = new Calculator("-57.675+ 78*( ((56+1) + 1) /2 +5)-2");
        boolean actual = calculator.isCorrect();
        boolean expected = true;
        assertEquals(expected, actual);

        Calculator calculator1 = new Calculator("-57.675+ 78*( ((56+1)) + 1) /2 +5)-2");
        boolean actual1 = calculator1.isCorrect();
        boolean expected1 = false;
        assertEquals(expected1, actual1);
    }

    @org.junit.Test
    public void parser() {
        Calculator calculator = new Calculator();
        Float actual = calculator.parser("-57.675 + 7")[0];
        Float expected = (float) -57.675;
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void calculate() {
        Calculator calculator = new Calculator("-57.675+ 78*( ((56+1) + 1) /2 +5)-2");
        try{
            Float actual = calculator.calculate();
            Float expected = (float) 2592.325;
            assertEquals(expected, actual);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        Calculator calculator1 = new Calculator("1.23+ 79* ((39+1) -5) -3");
        try{
            Float actual = calculator1.calculate();
            Float expected = (float) 2763.23;
            assertEquals(expected, actual);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @org.junit.Test
    public void fillVariables() {
        Calculator calculator = new Calculator("-57.675*x+ 78*( ((56+1) + 1) /2 +5)-2");
        char[] mas = {'3'};
        String actual = calculator.fillVariables(mas);
        String expected = "-(57.675 * 3 ) + (78 * ((((56 + 1 ) + 1 ) / 2 ) + 5 ) ) - 2";
        assertEquals(expected, actual);
    }
}
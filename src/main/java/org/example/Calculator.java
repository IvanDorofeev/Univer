package org.example;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is for calculating math expressions
 * @author Ivan Dorofeev
 */
public class Calculator {
    String expression;

    public Calculator() {
        this.expression = "" ;
    }

    public Calculator(String expression) {
        String expr = standardView(expression);
        this.expression = expr;
    }

    public void initialize(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        String expr = standardView(expression);
        this.expression = expr;
    }

    public void print() {
        System.out.println(this.expression);
    }

    /**
     * Rewrite math expression in normal view (for calculating)
     * @param expr string expression
     * @return expression in normal view
     */
    public String standardView (String expr){
        StringBuilder newExpr = new StringBuilder();
        for (int i = 0; i < expr.length(); i++) {
            char el = expr.charAt(i);
            if (el != ' '){
                newExpr.append(el);
            }
        }

        for (int i = 0; i < newExpr.length(); i++) {
            char el = newExpr.charAt(i);
            if (el == '*' || el == '/'){
                int j = 1;
                int prevEl = newExpr.charAt(i - j);
                int k = 0;
                while (!((prevEl == '+' || prevEl == '-' || prevEl == '(') && k <= 0)) {
                    if (prevEl == ')') {
                        k += 1;
                    } else if (prevEl == '(' && k > 0) {
                        k -= 1;
                    }
                    j += 1;
                    prevEl = newExpr.charAt(i - j);
                }
                newExpr.insert(i - j + 1, '(');
                i += 1;
                j = 1;
                int nextEl = newExpr.charAt(i + j);
                k = 0;
                while (!((nextEl == '+' || nextEl == '-' || nextEl == ')') && k <= 0)) {
                    if (nextEl == '(') {
                        k += 1;
                    } else if (nextEl == ')' && k > 0) {
                        k -= 1;
                    }
                    j += 1;
                    nextEl = newExpr.charAt(i + j);
                }
                newExpr.insert(i + j, ')');
                i += 1;
            }
        }

        StringBuilder resultExpr = new StringBuilder();
        for (int i = 0; i < newExpr.length(); i++) {
            char el = newExpr.charAt(i);
            if (el == '+' || el == '*' || el == '/'){
                resultExpr.append(" ").append(el).append(" ");
            } else if (el == ')') {
                resultExpr.append(" ").append(el);
            } else if (el == '-') {
                if (i == 0){                                                              /*тут здесь*/
                    resultExpr.append(el);
                }
                else {
                    char prevEl = newExpr.charAt(i - 1);
                    if (((prevEl - '0') >= 0 && (prevEl - '0') <= 9) || prevEl == ')') {
                        resultExpr.append(" ").append(el).append(" ");
                    } else {
                        resultExpr.append(" ").append(el);
                    }
                }
            } else {
                resultExpr.append(el);
            }
        }
        return String.valueOf(resultExpr);
    }

    /**
     * Find any variables in math expression
     * @return string with variables
     */
    public String checkForVariables() {
        StringBuilder retStr = new StringBuilder("");
        String expr = this.expression;
        for (int i = 0; i < expr.length(); i++) {
            char el = expr.charAt(i);
            int val = el - 'a';
            if (val >= 0 && val <= 25) {
                String strEl = String.valueOf(el);
                char el1 = expr.charAt(i + 1);
                String strEl1 = String.valueOf(el1);
                int val1 = el1 - 'a';
                char el2 = expr.charAt(i + 2);
                String strEl2 = String.valueOf(el2);
                int val2 = el1 - 'a';
                String express = strEl + strEl1 + strEl2;
                if (!express.equals("cos") && !express.equals("sin") && !express.equals("pow")) {
                    retStr.append(el).append(' ');
                }
            }
        }
        return String.valueOf(retStr);
    }

    /**
     * Replace variables by their values
     * @param mas array of default variables values
     * @return expression after replacing
     */
    public String fillVariables (char[] mas) {
        StringBuilder expr = new StringBuilder(this.expression);
        String variables = checkForVariables();
        String[] masVar = variables.split(" ");
        Scanner in = new Scanner(System.in);
        int len = masVar.length;
        char[] values = new char[len + mas.length];
        int i = 0;
        for (; i < len; i++) {
            values[i] = mas[i];
        }
        for (; i < len; i++) {
            values[i] = (char) in.nextInt();
        }
        int k = 0;
        for (i = 0; i < expr.length(); i++) {
            char el = expr.charAt(i);
            for (String s : masVar) {
                if (String.valueOf(el).equals(s)) {
                    expr.setCharAt(i, values[k]);
                    k += 1;

                }
            }
        }
        return String.valueOf(expr);
    }

    public boolean isCorrect() {
        return isCorrect(this.expression);
    }

    /**
     * Checking math expression for correctness
     * @param expr math expression
     * @return expression is correct or not (boolean)
     */
    public boolean isCorrect(String expr) {
        boolean flag = true;
        ArrayList<String> parentheses = new ArrayList<>();
        int i = 0;
        if (expr.charAt(0) == '-'){
            char next = expr.charAt(1);
            if (next - '0' < 0 || next - '0' > 9) {
                flag = false;
            }
            i = 2;
        }
        while (i < expr.length() && flag) {
            char el = expr.charAt(i);
            if (el == '(') {
                parentheses.add(String.valueOf(el));
            } else if (el == ')') {
                int index = parentheses.size() - 1;
                if (index >= 0) {
                    parentheses.remove(index);
                }
                else {
                    flag = false;
                    System.out.println("Wrong sequence of parentheses");
                }
            } else if (el != ' ' && el != '.' && el != '+' && el != '-' && el != '*' && el != '/' && ((el - '0') < 0 || (el - '0') > 9)) {
                flag = false;
                System.out.println("Wrong symbol");
            } else if (el == '+' || el == '-' || el == '*' || el == '/') {
                char prev = expr.charAt(i - 2);
                char next = expr.charAt(i + 2);
                if (((next - '0') < 0 || (next - '0') > 9 || (prev - '0') < 0 || (prev - '0') > 9) && prev != ')' && next != '(') {
                    flag = false;
                    System.out.println("Wrong sequence of symbols");
                }
            }
            i += 1;
        }
        if (parentheses.size() > 0) {
            flag = false;
        }
        return flag;
    }

    /**
     * Parse the string into float value until string contain non-numeric symbols (first number in string)
     * @param expr string with some number
     * @return first number in string
     */
    public Float[] parser(String expr) {

        boolean isNegative = false;
        int i = 0;
        int val10 = 0;
        float val01 = 0;

        char el = expr.charAt(i);

        if (el == '-'){
            isNegative = true;
            i += 1;
            el = expr.charAt(i);
        }

        while (el != ' ') {

            if (el == '.') {

                i += 1;
                el = expr.charAt(i);
                float coeff = (float) 0.1;

                while (el != ' ') {

                    val01 += (el - '0') * coeff;
                    coeff *= (float) 0.1;
                    i += 1;

                    if (i < expr.length()){
                        el = expr.charAt(i);
                    }
                    else {
                        break;
                    }
                }
                break;
            }

            val10 *= 10;
            val10 += (el - '0');
            i += 1;

            if (i < expr.length()){
                el = expr.charAt(i);
            }
            else {
                break;
            }
        }

        Float result = val10 + val01;

        if (isNegative) {
            result *= -1;
        }
        return new Float[]{result, (float) i};
    }

    /**
     * Calculate some level of parenthesis of expression
     * @param expr substring of expression
     * @return result of calculating of this level
     */
    private Float[] calc(String expr){
        int i = 0;
        Float result = (float) 0;
        Float[] res;

        if (expr.charAt(0) == '('){
            Float[] resCalc = calc(expr.substring(i + 1));
            result += resCalc[0];
            i += (int) (resCalc[1]/1) + 1;
        } else {
            res = parser(expr);
            result += res[0];
            i += (int) (res[1]/1);
        }

        while (i < expr.length()) {
            char el = expr.charAt(i);
            if (el == ' ') {
                i += 1;
                continue;
            } else if (el == ')') {
                return new Float[]{result, (float) i};
            } else if (el == '+') {
                if (expr.charAt(i + 2) == '('){
                    Float[] resCalc = calc(expr.substring(i + 3));
                    result += resCalc[0];
                    i += (int) (resCalc[1]/1) + 3;
                }
                else {
                    res = parser(expr.substring(i + 2));
                    result += res[0];
                    i += (int) (res[1] / 1) + 3;
                }
            } else if (el == '*') {
                if (expr.charAt(i + 2) == '('){
                    Float[] resCalc = calc(expr.substring(i + 3));
                    result *= resCalc[0];
                    i += (int) (resCalc[1]/1) + 3;
                }
                else {
                    res = parser(expr.substring(i + 2));
                    result *= res[0];
                    i += (int) (res[1] / 1) + 3;
                }
            } else if (el == '-') {
                if (expr.charAt(i + 2) == '('){
                    Float[] resCalc = calc(expr.substring(i + 3));
                    result -= resCalc[0];
                    i += (int) (resCalc[1]/1) + 3;
                }
                else {
                    res = parser(expr.substring(i + 2));
                    result -= res[0];
                    i += (int) (res[1] / 1) + 3;
                }
            } else if (el == '/') {
                if (expr.charAt(i + 2) == '('){
                    Float[] resCalc = calc(expr.substring(i + 3));
                    result /= resCalc[0];
                    i += (int) (resCalc[1]/1) + 3;
                }
                else {
                    res = parser(expr.substring(i + 2));
                    result /= res[0];
                    i += (int) (res[1] / 1) + 3;
                }
            }
            i += 1;
        }
        Float[] ret = {result,  (float) i};
        return ret;
    }

    /**
     * Calculate math expression
     * If met the parentheses, call the {@link #calc(String)}
     * @return result of calculating
     * @throws Exception
     */
    public Float calculate() throws Exception {
        String expr = this.getExpression();
        System.out.println(this.standardView(this.expression));
        if (this.isCorrect()) {
            return calc(expr)[0];
        }
        else {
            throw new Exception("Incorrect expression");
        }
    }
}

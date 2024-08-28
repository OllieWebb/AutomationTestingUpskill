package com.sparta.ow;

import java.util.List;

public class Calculator {
    private int num1;
    private int num2;
    private List<Integer> list;

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public int getNum2() {
        return num2;
    }

    public Integer add() {
        return num1 + num2;
    }

    public Integer subtract() {
        return num1 - num2;
    }

    public Integer multiply() {
        return num1 * num2;
    }

    public Integer divide() {
        return  num1 / num2;
    }
    public Integer addList(){
        int result = 0;
        for(Integer i : list){
            if(i % 2 == 0){
                result += i;
            }
        }
        return result;
    }

    public void setList(List<Integer> numbers) {
        this.list = numbers;
    }
}

package com.symbol.shoppinglistv2.Components;

//Category class
public class Category {

    private int color;
    private String name;

    public Category(){}

    public Category(String name, int color) {
        this.color = color;
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class Person {
    private String name;
    private int height;
    private int weight;
    private Size size;

    public Person(String name, int height, int weight, Size size) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public Size getSize() {
        return size;
    }

    @Override
    public String toString() {
        return name + " (height=" + height + ", weight=" + weight + ", size=" + size.toString() + ")";
    }
    
}

package com.ozer.datastructure;

public class Main
{
    public static void main(String[] args)
    {
    /* 
        ITree<Integer> bst = new BinarySearchTree<>();   
        
        bst.insert(10);
        bst.insert(5);
        bst.insert(15);
        bst.insert(20);
        
        System.out.println("Max value: " + bst.getMaxValue());
        System.out.println("Min value: " + bst.getMinValue() + "\n");
        
        bst.traversal();
        System.out.println();
        
        bst.delete(15);
        bst.traversal();
    */
        ITree<Person> bst = new BinarySearchTree<>();
        
        bst.insert(new Person("Ozer", 30));
        bst.insert(new Person("Ahsen", 53));
        bst.insert(new Person("Sefer", 59));
        bst.insert(new Person("Nadire", 73));
        bst.insert(new Person("Murat", 51));
        bst.insert(new Person("Elif", 19));
        
        bst.traversal();
    }
}

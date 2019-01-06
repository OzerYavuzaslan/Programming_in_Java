package com.ozeryavuzaslan.avl;

public class Main
{
    public static void main(String[] args)
    {
        ITree<Integer> avl_object = new AVLTree<>();
        
        avl_object.insert(1);
        avl_object.insert(2);
        avl_object.insert(3);
        avl_object.insert(6);
        avl_object.insert(7);
        avl_object.insert(0);
        
        avl_object.traverseInorder();
        
        avl_object.delete(3);
        
        avl_object.traverseInorder();
    }
}

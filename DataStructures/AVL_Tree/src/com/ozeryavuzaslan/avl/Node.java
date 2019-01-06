package com.ozeryavuzaslan.avl;

public class Node<T>
{
    private T data;
    private int height;
    private Node<T> left;
    private Node<T> right;

    public Node(T data){
        this.data = data;
    }
    
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }
    
    @Override
    public String toString()
    {
        return this.data.toString();
    }
}

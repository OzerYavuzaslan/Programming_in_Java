package com.ozeryavuzaslan.avl;

public interface ITree<T>
{
    public void insert(T new_data);
    public void traverseInorder();
    public void delete(T data);
}

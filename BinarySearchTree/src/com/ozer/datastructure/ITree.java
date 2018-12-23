package com.ozer.datastructure;

public interface ITree<T>
{
    public void traversal();
    public void insert(T new_data);
    public void delete(T new_data);
    public T getMaxValue();
    public T getMinValue();
}

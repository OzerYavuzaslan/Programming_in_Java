package com.ozeryavuzaslan;

public interface INodeList {
    void setRoot(ListItem item);
    ListItem getRoot();
    boolean addItem(ListItem item);
    boolean removeItem(ListItem item);
    void traverse(ListItem root);
}

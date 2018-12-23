package com.ozer.datastructure;

public class BinarySearchTree<T extends Comparable<T>> implements ITree<T>
{
    private Node<T> rootNode;
    
    @Override
    public void insert(T new_data)
    {
        if(rootNode == null)
            rootNode = new Node<T>(new_data);
        else
            insertNode(new_data, rootNode);
    }
    
    @Override
    public T getMinValue()
    {
        if(rootNode == null)
            return null;
        
        return getMin(rootNode);
    }
    
    @Override
    public T getMaxValue()
    {
        if(rootNode == null)
            return null;
        
        return getMax(rootNode);
    }

    private void insertNode(T new_data, Node<T> nodePtr)
    {
        if(new_data.compareTo(nodePtr.getData()) < 0)
        {
            if(nodePtr.getLeft() != null)
                insertNode(new_data, nodePtr.getLeft());
            else
            {
                Node<T> newNode = new Node<T>(new_data);
                nodePtr.setLeft(newNode);
            }
        }
        else
        {
            if(nodePtr.getRight() != null)
                insertNode(new_data, nodePtr.getRight());
            else
            {
                Node<T> newNode = new Node<T>(new_data);
                nodePtr.setRight(newNode);
            }
        }
    }

    private T getMax(Node<T> nodePtr)
    {
        if(nodePtr.getRight() != null)
            return getMax(nodePtr.getRight());
        
        return nodePtr.getData();
    }

    private T getMin(Node<T> nodePtr)
    {
        if(nodePtr.getLeft() != null)
            return getMin(nodePtr.getLeft());
        
        return nodePtr.getData();
    }
    
    @Override
    public void traversal()
    {
        if(rootNode != null)
            InOrderTraversal(rootNode);
    }
    
    private void InOrderTraversal(Node<T> rootNode)
    {
       if(rootNode.getLeft() != null)
           InOrderTraversal(rootNode.getLeft());
       
        System.out.print(rootNode + ", ");
        
        if(rootNode.getRight() != null)
            InOrderTraversal(rootNode.getRight());
    }
    
    private Node<T> deleteNode(Node<T> targetNode, T data)
    {
        if(targetNode == null)
            return targetNode;
        
        if(data.compareTo(targetNode.getData()) < 0)
            targetNode.setLeft(deleteNode(targetNode.getLeft(), data));
        else if(data.compareTo(targetNode.getData()) > 0)
            targetNode.setRight(deleteNode(targetNode.getRight(), data));
        else
        {//we have found the target node we want to remove
            if(targetNode.getLeft() == null && targetNode.getRight() == null)
            {
                System.out.println("Removing a leaf node...");
                return null;
            }
            if(targetNode.getLeft() == null)
            {
                System.out.println("Removing a right child...");
                Node<T> tempNode = targetNode.getRight();
                targetNode = null;
                return tempNode;
            }
            else if(targetNode.getRight() == null)
            {
                System.out.println("Removing a left child...");
                Node<T> tempNode = targetNode.getLeft();
                targetNode = null;
                return tempNode;
            }
            //This is the target with two children case
            System.out.println("Removing item with two children...");
            Node<T> tempNode = getPredecessor(targetNode.getLeft());
            targetNode.setData(tempNode.getData());
            targetNode.setLeft(deleteNode(targetNode.getLeft(), tempNode.getData()));
        }
        return targetNode;
    }
    
    private Node<T> getPredecessor(Node<T> targetNode)
    {
        if(targetNode.getRight() != null)
            return getPredecessor(targetNode.getRight());
        
        System.out.println("Predecessor is: " + targetNode);
        return targetNode;
    }
    
    @Override
    public void delete(T data)
    {
        if(rootNode != null)
            rootNode = deleteNode(rootNode, data);
    }
}

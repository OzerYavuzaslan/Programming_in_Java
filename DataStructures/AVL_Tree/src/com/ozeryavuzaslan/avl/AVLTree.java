package com.ozeryavuzaslan.avl;

public class AVLTree<T extends Comparable<T>> implements ITree<T>
{
    private Node root_node;

    @Override
    public void insert(T new_data)
    {
        root_node = insertNewNode(root_node, new_data);
    }
    
    private Node insertNewNode(Node<T> nodePtr, T new_data)
    {
        if(nodePtr == null)
            return new Node(new_data);
        
        if(new_data.compareTo(nodePtr.getData()) < 0)
            nodePtr.setLeft(insertNewNode(nodePtr.getLeft(), new_data));
        else
            nodePtr.setRight(insertNewNode(nodePtr.getRight(), new_data));
        
        nodePtr.setHeight(Math.max(heightMethod(nodePtr.getLeft()), heightMethod(nodePtr.getRight())) + 1);
        return settleViolation(new_data, nodePtr);
    }
    
    @Override
    public void delete(T data)
    {
	root_node = deleteMethod(root_node, data);
    }
    
    private Node<T> deleteMethod(Node<T> nodePtr, T data)
    {
	if (nodePtr == null)
            return nodePtr;

	// first we have to look for the node we want to get rid of
	if (data.compareTo(nodePtr.getData()) < 0 )
            nodePtr.setLeft(deleteMethod(nodePtr.getLeft(), data));
	else if (data.compareTo(nodePtr.getData()) > 0 )
            nodePtr.setRight(deleteMethod(nodePtr.getRight(), data));
	else
        {  // we have found the node we want to remove !!!
            if (nodePtr.getLeft() == null && nodePtr.getRight() == null)
            {
		System.out.println("Removing a leaf node...");
		return null;
            }

            if (nodePtr.getLeft() == null)
            {
		System.out.println("Removing the right child...");
		Node<T> tempNode = nodePtr.getRight();
		nodePtr = null;
                return tempNode;
            }
            else if (nodePtr.getRight() == null)
            {
		System.out.println("Removing the left child...");
		Node<T> tempNode = nodePtr.getLeft();
		nodePtr = null;
		return tempNode;
            }

            // this is the node with two children case !!!
            System.out.println("Removing item with two children...");
            Node<T> tempNode = getPredecessor(nodePtr.getLeft());

            nodePtr.setData(tempNode.getData());
            nodePtr.setLeft(deleteMethod(nodePtr.getLeft(), tempNode.getData()));
	}

	nodePtr.setHeight((Math.max(heightMethod(nodePtr.getLeft()), heightMethod(nodePtr.getRight())) + 1));

	// have to check on every delete operation whether the tree has become unbalanced or not !!!
        return settleDeletion(nodePtr);
    }

    private Node<T> settleDeletion(Node<T> nodePtr)
    {
	int balance = getBalance(nodePtr);

	// OK, we know the tree is left heavy BUT it can be left-right heavy or left-left heavy
        if (balance > 1)
        {	
            // left right heavy situation: left rotation on parent + right rotation on grandparent
            if (getBalance(nodePtr.getLeft()) < 0)
                nodePtr.setLeft(leftRotation(nodePtr.getLeft()));

            // this is the right rotation on grandparent ( if left-left heavy, thats single right rotation is needed
            return rightRotation(nodePtr);
	}

	// OK, we know the tree is right heavy BUT it can be left-right heavy or right-right heavy
	if (balance < -1)
        {
            // right - left heavy so we need a right rotation (on parent) before left rotation
            if (getBalance(nodePtr.getRight()) > 0)
                nodePtr.setRight(rightRotation(nodePtr.getRight()));

            // left rotation on grand parent
            return leftRotation(nodePtr);
        }

        return nodePtr;
    }

    private Node<T> getPredecessor(Node<T> nodePtr)
    {

        Node<T> predecessor = nodePtr;

        while (predecessor.getRight() != null)
            predecessor = predecessor.getRight();

        return predecessor;
    }
    
    private int heightMethod(Node<T> node)
    {
        if(node == null)
            return -1;
        
        return node.getHeight();
    }
    
    private int getBalance(Node<T> node)
    {
        if(node == null)
            return 0;
        
        return (heightMethod(node.getLeft()) - heightMethod(node.getRight()));
    }
    
    private Node rightRotation(Node<T> node)
    {
        System.out.println("Rotating to the right on node: " + node);
        
        Node<T> temp_left_node = node.getLeft();
        Node<T> t = temp_left_node.getRight();
        
        temp_left_node.setRight(node);
        node.setLeft(t);
        
        node.setHeight( (Math.max(heightMethod(node.getLeft()), heightMethod(node.getRight())) + 1));
        temp_left_node.setHeight( (Math.max(heightMethod(temp_left_node.getLeft()), heightMethod(temp_left_node.getRight())) + 1));
        
        return temp_left_node;
    }
    
    private Node leftRotation(Node<T> node)
    {
        System.out.println("Rotating to the left on node: " + node);
        
        Node<T> temp_right_node = node.getRight();
        Node<T> t = temp_right_node.getLeft();
        
        temp_right_node.setLeft(node);
        node.setRight(t);
        
        node.setHeight( (Math.max(heightMethod(node.getLeft()), heightMethod(node.getRight())) + 1));
        temp_right_node.setHeight( (Math.max(heightMethod(temp_right_node.getLeft()), heightMethod(temp_right_node.getRight())) + 1));
        
        return temp_right_node;
    }

    private Node<T> settleViolation(T new_data, Node<T> nodePtr)
    {
        int balance = getBalance(nodePtr);
        
        //Doubly heavy Left Tree (Left Left case)
        if(balance > 1 && new_data.compareTo(nodePtr.getLeft().getData()) < 0)
        {
            System.out.println("Tree is left left heavy...");
            return rightRotation(nodePtr);
        }

        //Doubly heavy Right Tree (Right Right case)
        else if(balance < -1 && new_data.compareTo(nodePtr.getRight().getData()) > 0)
        {
            System.out.println("Tree is right right heavy...");
            return leftRotation(nodePtr);
        }

        //Left Right Case
        else if(balance > 1 && new_data.compareTo(nodePtr.getLeft().getData()) > 0)
        {
            System.out.println("Tree is left right heavy...");
            nodePtr.setLeft(leftRotation(nodePtr.getLeft()));
            return rightRotation(nodePtr);
        }
            
        //Right Left Case
        else if(balance < -1 && new_data.compareTo(nodePtr.getRight().getData()) < 0)
        {
            System.out.println("Tree is right left heavy...");
            nodePtr.setRight(rightRotation(nodePtr.getRight()));
            return leftRotation(nodePtr);
        }
        
        return nodePtr;
    }

    @Override
    public void traverseInorder()
    {
        if(root_node == null)
            return;
        
        inOrderTraversal(root_node);
    }

    private void inOrderTraversal(Node<T> root_node)
    {
        if(root_node != null)
        {
            inOrderTraversal(root_node.getLeft());
            System.out.print(root_node + " -> ");
            inOrderTraversal(root_node.getRight());
        }
    }
}
package com.ozeryavuzaslan;

public class MyLinkedList implements INodeList {

    private ListItem root = null;

    public MyLinkedList(ListItem root) {
        setRoot(root);
    }


    @Override
    public void setRoot(ListItem root) {
        this.root = root;
    }

    @Override
    public ListItem getRoot() {
        return this.root;
    }

    @Override
    public boolean addItem(ListItem newItem) {
        if (this.root == null) {
            // The list was empty, so this item becomes the head of the list
            this.root = newItem;
            return true;
        }

        ListItem currentItem = getRoot();

        while (currentItem != null) {
            int comparison = (currentItem.compareTo(newItem));

            if (comparison < 0) {
                // newItem is greater, move right if possible
                if (currentItem.next() != null)
                    currentItem = currentItem.next();
                else {
                    // there is no next, so insert at end of list
                    currentItem.setNext(newItem).setPrevious(currentItem);
                    return true;
                }
            } else if (comparison > 0) {
                // newItem is less, insert before
                if (currentItem.previous() != null) {
                    currentItem.previous().setNext(newItem).setPrevious(currentItem.previous());
                    newItem.setNext(currentItem).setPrevious(newItem);
                } else {
                    // the node with a previous is the root
                    newItem.setNext(getRoot()).setPrevious(newItem);
                    setRoot(newItem);
                }

                return true;
            } else {
                // equal
                System.out.println(newItem.getValue() + " is already in the list, not added.");
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean removeItem(ListItem item) {
        if (item != null)
            System.out.println("Deleting item " + item.getValue());

        ListItem currentItem = getRoot();

        while (currentItem != null){
            int comparison = currentItem.compareTo(item);

            if (comparison == 0){
                //found the item
                if (currentItem == getRoot())
                    setRoot(currentItem.next());
                else {
                    currentItem.previous().setNext(currentItem.next());

                    if (currentItem.next() != null)
                        currentItem.next().setPrevious(currentItem.previous());
                }

                return true;
            } else if (comparison < 0)
                currentItem = currentItem.next();
            else // we are at an item greater than the one to be deleted, so the item is not in the list.
                return false;
        }

        return false;
    }

    @Override
    public void traverse(ListItem root) {
        if (root == null)
            System.out.println("The list is empty");
        else {
            while (root != null) {
                System.out.println(root.getValue());
                root = root.next();
            }
        }
    }
}
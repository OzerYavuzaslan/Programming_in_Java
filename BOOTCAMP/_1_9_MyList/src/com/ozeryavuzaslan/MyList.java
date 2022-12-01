package com.ozeryavuzaslan;

public class MyList {
    private String[] arrayStr;
    private int currentElementNumber = 0;
    private static final char LETTER_C = 'C';

    public MyList(){
        setArrayStr(new String[5]);
    }

    private void increaseSize(){
        int newArraySize = getArrayStr().length + 7;
        String[] newArrayStr = new String[newArraySize];

        for (int i = 0; i < getArrayStr().length; i++)
            newArrayStr[i] = getArrayStr()[i];

        setArrayStr(newArrayStr);
    }

    private void decreaseSize(){
        int newArraySize;
        int currentArraySize = getArrayStr().length;
        final int minArraySize = 5;

        if (currentArraySize - 7 > minArraySize)
            newArraySize = getArrayStr().length - 7;
        else
            newArraySize = minArraySize;

        String[] newArrayStr = new String[newArraySize];

        for (int i = 0; i < getCurrentElementNumber(); i++)
            newArrayStr[i] = getArrayStr()[i];

        setArrayStr(newArrayStr);
    }

    public void delete(int index){
        if (index > getArrayStr().length - 1){
            System.out.println("Size of the array is " + getArrayStr().length + ", but the index that you've entered is out of bound the array. Your index input: " + index);
            System.out.println("This is why the delete operation cannot be proceeded.\n");
        }
        else {
            getArrayStr()[index] = null;
            setCurrentElementNumber(getCurrentElementNumber() - 1);
            System.out.println("The deletion request is successfully done.\n");
            decreaseSize();
        }
    }

    private int findElement(String str){
        int index = -1;

        for (int i = 0; i < getArrayStr().length; i++){
            if (getArrayStr()[i] != null && getArrayStr()[i].equalsIgnoreCase(str)){
                index = i;
                break;
            }
        }

        return index;
    }

    //overloading
    public void delete(String str){
        int index = findElement(str);

        if (index == -1)
            System.out.println(str + " value is not in the array.\n");
        else
            delete(index);
    }

    public boolean add(String str){
        String tmpStr = str.toUpperCase();
        char ch = tmpStr.charAt(0);

        if (ch == LETTER_C){
            if (getArrayStr().length == getCurrentElementNumber())
                increaseSize();

            for (int i = 0; i < getArrayStr().length; i++) {
                if (getArrayStr()[i] == null) {
                    getArrayStr()[i] = str;
                    break;
                }
            }

            setCurrentElementNumber(getCurrentElementNumber() + 1);
            return true;
        }
        else
            return false;
    }

    public void printElements(){
        for (int i = 0; i < getArrayStr().length; i++)
            System.out.println((i + 1) + ". Element: " + getArrayStr()[i]);

        System.out.println();
    }

    public void printNumberOfElementsAndArraySize(){
        System.out.println("Number of current elements: " + getCurrentElementNumber() + " | " + "Array's current size: " + getArrayStr().length + "\n");
    }

    public String[] getArrayStr() {
        return arrayStr;
    }

    private void setArrayStr(String[] arrayStr) {
        this.arrayStr = arrayStr;
    }

    public int getCurrentElementNumber() {
        return currentElementNumber;
    }

    private void setCurrentElementNumber(int currentElementNumber) {
        this.currentElementNumber = currentElementNumber;
    }

    public static void printMenu(){
        System.out.println("1) Enter a string");
        System.out.println("2) Delete an element by index");
        System.out.println("3) Delete an element by element value. If there are duplicated values in the array, it is going to delete the first value of these duplicated values.");
        System.out.println("4) Print the array elements");
        System.out.println("5) Print number of elements and array size");
        System.out.println("6) Exit the program");
        System.out.print("Choice: ");
    }
}

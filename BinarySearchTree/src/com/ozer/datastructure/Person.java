package com.ozer.datastructure;

public class Person<T> implements Comparable<Person>
{
    private String name;
    private int age;

    public Person(String name, int age)
    {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public String toString()
    {
        return this.name + " - " + this.age;
    }

    @Override
    public int compareTo(Person person)
    {
        //return name.compareTo(person.getName()); //According to person's name
        //return age - person.getAge(); //According to person's age
        return -(age - person.getAge()); //According to person's age in reverse order
    }
}

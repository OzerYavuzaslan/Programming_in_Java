package com.ozeryavuzaslan;

import java.util.ArrayList;
import java.util.List;

public class Monster implements ISaveable {
    private String name;
    private int hitPoints;
    public int strength;

    public Monster(String name, int hitPoints, int strength) {
        setName(name);
        setHitPoints(hitPoints);
        setStrength(strength);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "name='" + name + '\'' +
                ", hitPoints=" + hitPoints +
                ", strength=" + strength +
                '}';
    }

    @Override
    public List<String> write() {
       ArrayList<String> values = new ArrayList<String>();
        values.add(0, getName());
        values.add(1, String.valueOf(getHitPoints()));
        values.add(2, String.valueOf(getStrength()));
        return values;
    }

    @Override
    public void read(List<String> savedValues) {
        if (savedValues != null && savedValues.size() > 0){
            setName(savedValues.get(0));
            setHitPoints(Integer.parseInt(savedValues.get(1)));
            setStrength(Integer.parseInt(savedValues.get(2)));
        }
    }
}

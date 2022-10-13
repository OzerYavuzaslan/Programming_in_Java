package com.ozeryavuzaslan;

public class Main {
    public static void main(String[] args) {
        Player ozer = new Player("Ozer", 10, 15);
        System.out.println(ozer.toString());
        Menu.saveObject(ozer);
        ozer.setHitPoints(8);
        System.out.println(ozer);
        ozer.setWeapon("Knife");
        Menu.saveObject(ozer);
    //    Menu.loadObject(ozer);
        System.out.println(ozer);

        ISaveable werewolf = new Monster("Werewolf", 20, 40);
        System.out.println(werewolf);
     //   System.out.println("Strength = " + ((Monster) werewolf).getStrength());
        Menu.saveObject(werewolf);
    //    Menu.loadObject(werewolf);

        Menu.closeScanner();
    }
}
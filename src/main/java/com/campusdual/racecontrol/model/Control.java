package com.campusdual.racecontrol.main.model;

public class Control {

    public static void main(String[] args) {
        menu();
    }

    private static void menu(){
        //c'mon do smthing
        int option = 0;
        do{
            System.out.println("========================================");
            System.out.println("              Race control              ");
            System.out.println("========================================");
            System.out.println("Select an option:");
            System.out.println("1.- Scoreboard.");
            System.out.println("2.- Tournaments.");
            System.out.println("3.- Races.");
            System.out.println("4.- Garages.");
            System.out.println("5.- Cars.");
            System.out.println("0.- Exit");
            option = Input.integer();



        } while (true);
    }
}

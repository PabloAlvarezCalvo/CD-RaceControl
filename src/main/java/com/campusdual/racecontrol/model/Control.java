package com.campusdual.racecontrol.model;

import com.campusdual.racecontrol.model.Race.EliminationRace;
import com.campusdual.racecontrol.model.Race.Race;
import com.campusdual.racecontrol.model.Race.StandardRace;
import com.campusdual.racecontrol.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Control {
    private static final List<Garage> garages = new ArrayList<>();
    private static final List<ScoreCar> cars = new ArrayList<>();
    private static final List<Race> races = new ArrayList<>();
    private static final List<Tournament> tournaments = new ArrayList<>();

    public static void main(String[] args) {
        startUp();
        menu();
    }

    private static void startUp(){
        //Load everything from file
    }

    private static void menu(){
        int option;
        do{
            System.out.println("========================================");
            System.out.println("|              Race control            |");
            System.out.println("========================================");
            System.out.println("Select an option:");
            System.out.println("1.- Tournaments.");
            System.out.println("2.- Races.");
            System.out.println("3.- Garages.");
            System.out.println("4.- Cars.");
            System.out.println("0.- Exit");
            System.out.println("========================================");
            option = Utils.integer();

            switch (option){
                case 1: menuTournaments();
                break;

                case 2: menuRaces();
                break;

                case 3: menuGarages();
                break;

                case 4: menuCars();
                break;

                case 0: //exit
                break;

                default:
                break;
            }

        } while (option != 0);
    }

    private static void menuTournaments() {
        int option;
        do {
            System.out.println("========================================");
            System.out.println("|              Tournaments             |");
            System.out.println("========================================");
            System.out.println("1.- Select tournament.");
            System.out.println("2.- Add tournament.");
            System.out.println("0.- Exit");
            System.out.println("========================================");
            option = Utils.integer();

            switch (option){
                case 1: selectTournament();
                break;

                case 2: addTournament();
                break;

                case 0: //exit
                break;

                default:
                break;
            }

        } while (option != 0);
    }

    private static void selectTournament(){
        List<Tournament> selection = Utils.showAndSelectFromList(tournaments, true, false);
        Tournament tournament = selection.get(0);
        int option = -1;

        do {
            if (tournament != null) {
                System.out.println("\n========================================");
                System.out.println(tournament.getId() + ", " + tournament.getName());
                System.out.println("========================================");
                System.out.println("1.- Scoreboard.");
                System.out.println("2.- Run tournament.");
                System.out.println("3.- Rename.");
                System.out.println("4.- Add/remove races.");
                System.out.println("5.- Add/remove garages.");
                System.out.println("6.- Delete.");
                System.out.println("0.- Exit.");
                option = Utils.integer();

                switch (option) {
                    case 1:
                        scoreboard(tournament);
                    break;

                    case 2:
                        runTournament(tournament);
                    break;

                    case 3:
                        modifyTournament(tournament);
                    break;

                    case 4:
                        addRemoveRaces(tournament);
                    break;

                    case 5:
                        addRemoveGarages(tournament);
                    break;

                    case 6:
                        if(Utils.string("Are you sure you want to remove this tournament? Y/N\n").toUpperCase().startsWith("Y")){
                            tournaments.remove(tournament);
                        }
                    break;

                    case 0: //exit
                    break;

                    default:
                    break;
                }
            }
        } while (option != 0);
    }

    private static Tournament findTournamentById(int id){
        for (Tournament t : tournaments) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    private static void addTournament(){
        long nextId = tournaments.get(tournaments.size() - 1).getId() + 1;

        boolean exists;

        do {
            String tournamentName = Utils.string("Specify the tournament name:\n");
            exists = false;
            for (Tournament t : tournaments) {
                if (t.getName().equalsIgnoreCase(tournamentName)) {
                    exists = true;
                    System.out.println("Name already in use, choose a different one.");
                    break;
                } else {
                    Tournament newTournament = new Tournament(nextId, tournamentName);
                    tournaments.add(newTournament);
                }
            }
        } while (exists);
    }

    private static void scoreboard(Tournament tournament){
        System.out.println("Scoreboard:");
        System.out.println(tournament.getScoreboard());
    }

    private static void runTournament(Tournament tournament){
        for (Race r : tournament.getRaces()){
            r.startRace();
        }

        System.out.println("Tournament ended. Results:");
        System.out.println(tournament.getScoreboard());
    }

    private static void modifyTournament(Tournament tournament){
        System.out.println("\n----------------------------------------");
        System.out.printf("[%d] %s\n", tournament.getId(), tournament.getName());
        System.out.println("----------------------------------------");

        boolean validName = true;

        do {
            String newName = Utils.string("Type a new name:\n");
            for (Tournament t: tournaments){
                if (t.getName().equalsIgnoreCase(newName)){
                    System.out.println("Name already exists, choose a different one.");
                    validName = false;
                    break;
                }
            }
            if (validName) {
                tournament.setName(newName);
            }
        } while (!validName);
    }

    private static void addRemoveRaces(Tournament tournament){
        int option;

        do {
            System.out.println("\n========================================");
            System.out.printf("[%d] %s\n", tournament.getId(), tournament.getName());
            System.out.println("========================================");
            Utils.showFromList(tournament.getRaces(), true);
            System.out.println("========================================");

            System.out.println("1.- Add race.");
            System.out.println("2.- Remove race.");
            System.out.println("3.- Start race.");
            System.out.println("0.- Exit.");

            option = Utils.integer();

            switch (option){
                case 1:
                    System.out.println("----------------------------------------");
                    System.out.println("Available races to add:");
                    System.out.println("----------------------------------------");
                    List<Race> availableRaces = races;
                    availableRaces.removeAll(tournament.getRaces());

                    List<Race> selection = Utils.showAndSelectFromList(availableRaces, true, false);
                    Race raceToAdd = selection.get(0);

                    if (Utils.string("Add selected race to the tournament? Y/N\n").toUpperCase().startsWith("Y")) {
                        tournament.getRaces().add(raceToAdd);
                    }
                break;

                case 2:
                    System.out.println("----------------------------------------");
                    System.out.println("Races to remove:");
                    System.out.println("----------------------------------------");
                    List<Race> selection2 = Utils.showAndSelectFromList(tournament.getRaces(), true);
                    Race raceToRemove = selection2.get(0);

                    if (Utils.string("Add selected race to the tournament? Y/N\n").toUpperCase().startsWith("Y")) {
                        tournament.getRaces().remove(raceToRemove);
                    }
                break;

                case 3:
                break;

                default:
                    System.out.println("Invalid option. Please try again.");
                break;
            }
        } while (option != 0);
    }

    private static void addRemoveGarages(Tournament tournament){
        /*TODO*/
        //Is this really necessary?
    }

    private static void addRemoveGarages(Race race){
        int option;

        do {
            System.out.println("\n========================================");
            System.out.printf("[%d] %s\n", race.getId(), race.getName());
            System.out.println("========================================");
            Utils.showFromList(race.getGarages(), true);
            System.out.println("========================================");

            System.out.println("1.- Add garage.");
            System.out.println("2.- Remove garage.");
            System.out.println("0.- Exit.");

            option = Utils.integer();

            switch (option){
                case 1:
                    System.out.println("----------------------------------------");
                    System.out.println("Available garages to add:");
                    System.out.println("----------------------------------------");
                    List<Garage> avaliableGarages = garages;
                    avaliableGarages.removeAll(race.getGarages());

                    Garage garageToAdd = null;
                    do {
                        List<Garage> selected = Utils.showAndSelectFromList(avaliableGarages, true, false);

                        garageToAdd = selected.get(0);
                        if (garageToAdd == null){
                            System.out.println("Invalid ID, please try again.");
                        }
                    } while(garageToAdd == null);

                    if (Utils.string("Add selected garage to the race? Y/N\n").toUpperCase().startsWith("Y")) {
                        race.getGarages().add(garageToAdd);
                    }
                    break;

                case 2:
                    System.out.println("----------------------------------------");
                    System.out.println("Garages to remove:");
                    System.out.println("----------------------------------------");
                    List<Garage> selection = Utils.showAndSelectFromList(race.getGarages(), true, false);
                    Garage garageToRemove = selection.get(0);

                    if (Utils.string("Remove selected garage from the race? Y/N\n").toUpperCase().startsWith("Y")) {
                        race.getGarages().remove(garageToRemove);
                    }
                    break;

                case 3:
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (option != 0);
    }

    private static Garage findGarageById(long id){
        for (Garage g : garages){
            if (g.getId() == id){
                return g;
            }
        }
        return null;
    }
    private static void addRemoveCars(Race race){

    }

    private static void addRemoveCars(Garage garage){

    }

    private static Race findRaceById(int id){
        for (Race r : races) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    private static void menuRaces(){
        int option;
        do {
            System.out.println("========================================");
            System.out.println("|                 Races                |");
            System.out.println("========================================");
            System.out.println("1.- Select race.");
            System.out.println("2.- Add race.");
            System.out.println("0.- Exit");
            System.out.println("========================================");
            option = Utils.integer();

            switch (option){
                case 1: selectRace();
                    break;

                case 2: addRace();
                    break;

                case 0: //exit
                    break;

                default:
                    break;
            }

        } while (option != 0);
    }

    private static void selectRace(){
        System.out.println();
        List<Race> selection = Utils.showAndSelectFromList(races, true, false);
        Race race = selection.get(0);

        int option = -1;

        do {
            if (race != null) {
                System.out.println("\n========================================");
                System.out.println(race.getId() + ", " + race.getName());
                System.out.println("========================================");
                System.out.println("1.- Start race.");
                System.out.println("2.- See podium.");
                System.out.println("3.- Modify race");
                System.out.println("4.- Add/remove garages.");
                System.out.println("5.- Add/remove cars.");
                System.out.println("6.- Delete.");
                System.out.println("0.- Exit.");
                option = Utils.integer();

                switch (option) {
                    case 1:
                        startRace(race);
                    break;

                    case 2:
                        checkPodium(race);
                    break;

                    case 3:
                        modifyRace(race);
                    break;

                    case 4:
                        addRemoveGarages(race);
                    break;

                    case 5:
                        addRemoveCars(race);
                    break;

                    case 6:
                        if(Utils.string("Are you sure you want to remove this race? Y/N\n").toUpperCase().startsWith("Y")){
                            races.remove(race);
                        }
                        break;

                    case 0: //exit
                        break;

                    default:
                        break;
                }
            }
        } while (option != 0);
    }

    private static void startRace(Race race){
        race.startRace();
    }

    private static void checkPodium(Race race){
        System.out.println(race.checkPodium());
    }

    private static void modifyRace(Race race){
        int option;
        if (race instanceof StandardRace){
            do {
                System.out.println("\n----------------------------------------");
                System.out.printf("[%d] %s\n", race.getId(), race.getName());
                System.out.println("Type: Standard race.");
                int duration = ((StandardRace)race).getDuration();
                System.out.printf("Duration: %2d:%2d.\n", duration / 60, duration % 60);
                System.out.println("----------------------------------------");
                System.out.println("1.- Change name.");
                System.out.println("2.- Change duration.");
                System.out.println("3.- Change garages.");
                System.out.println("4.- Change cars.");
                System.out.println("0.- Exit.");
                option = Utils.integer();

                switch (option){
                    case 1:
                        changeRaceName(race);
                    break;

                    case 2:
                        changeRaceDuration(race);
                    break;

                    case 3:
                        addRemoveGarages(race);
                    break;

                    case 4:
                        addRemoveCars(race);
                    break;

                    case 0: //exit
                    break;
                }
            } while (option != 0);


        } else if (race instanceof EliminationRace) {
            do {
                System.out.println("\n----------------------------------------");
                System.out.printf("[%d] %s\n", race.getId(), race.getName());
                System.out.println("Type: Elimination race.");
                System.out.println("----------------------------------------");
                System.out.println("1.- Change name.");
                System.out.println("2.- Change warm-up time.");
                System.out.println("3.- Change garages.");
                System.out.println("4.- Change cars.");
                System.out.println("0.- Exit.");
                option = Utils.integer();

                switch (option){
                    case 1:
                        changeRaceName(race);
                    break;

                    case 2:
                        changeRaceDuration(race);
                    break;

                    case 3:
                        addRemoveGarages(race);
                    break;

                    case 4:
                        addRemoveCars(race);
                    break;

                    case 0: //exit
                    break;
                }
            } while (option != 0);
        }
    }

    private static void changeRaceName(Race race){
        boolean isValidName = false;

        String newName;
        do {
            newName = Utils.string("Type the new name for the race:\n");
            for (Race r : races){
                if (r.getName().equalsIgnoreCase(newName)){
                    System.out.println("Name already in use, choose another one.");
                    break;
                } else {
                    isValidName = true;
                }
            }
        } while (!isValidName);

        race.setName(newName);
        System.out.printf("Name changed to: %s.\n", race.getName());
    }

    private static void changeRaceDuration(Race race){
        if (race instanceof StandardRace){
            System.out.printf("Current duration: %d minutes.\n", ((StandardRace) race).getDuration());
            ((StandardRace) race).setDuration(Utils.integer("Type the new duration in minutes:\n"));
        } else if (race instanceof EliminationRace) {
            System.out.printf("Current warm-up time: %d minutes.\n", ((EliminationRace) race).getWarmUpTime());
            ((EliminationRace) race).setWarmUpTime(Utils.integer("Type the new duration in minutes:\n"));
        }
    }

    private static void addRace(){

    }

    private static void menuGarages(){
        int option;
        do {
            System.out.println("========================================");
            System.out.println("|                Garages               |");
            System.out.println("========================================");
            System.out.println("1.- Select garage.");
            System.out.println("2.- Add garage.");
            System.out.println("0.- Exit");
            System.out.println("========================================");
            option = Utils.integer();

            switch (option){
                case 1: selectGarage();
                    break;

                case 2: addGarage();
                    break;

                case 0: //exit
                    break;

                default:
                    break;
            }

        } while (option != 0);
    }

    private static void selectGarage(){
        System.out.println();
        List<Garage> selection = Utils.showAndSelectFromList(garages, true, false);
        Garage garage = selection.get(0);

        int option = -1;

        do {
            if (garage != null) {
                System.out.println("\n========================================");
                System.out.println(garage.getId() + ", " + garage.getName());
                System.out.println("========================================");
                System.out.println("1.- Rename.");
                System.out.println("2.- Add/remove cars.");
                System.out.println("3.- Delete.");
                System.out.println("0.- Exit.");
                option = Utils.integer();

                switch (option) {
                    case 1:
                        renameGarage(garage);
                    break;

                    case 2:
                        addRemoveCars(garage);
                        break;

                    case 3:
                        if(Utils.string("Are you sure you want to remove this garage? Y/N\n").toUpperCase().startsWith("Y")){
                            garages.remove(garage);
                        }
                        break;

                    case 0: //exit
                        break;

                    default:
                        break;
                }
            }
        } while (option != 0);
    }

    private static void renameGarage(Garage garage){
        boolean isValidName = false;

        String newName;
        do {
            newName = Utils.string("Type the new name for the garage:\n");
            for (Garage g : garages){
                if (g.getName().equalsIgnoreCase(newName)){
                    System.out.println("Name already in use, choose another one.");
                    break;
                } else {
                    isValidName = true;
                }
            }
        } while (!isValidName);

        garage.setName(newName);
        System.out.printf("Name changed to: %s.\n", garage.getName());
    }

    private static void addGarage(){
        Garage newGarage = new Garage(Utils.string("Type the name of the garage:\n"));
        garages.add(newGarage);
    }

    private static void menuCars(){
        int option;
        do {
            System.out.println("========================================");
            System.out.println("|                 Cars                 |");
            System.out.println("========================================");
            System.out.println("1.- Select car.");
            System.out.println("2.- Add car.");
            System.out.println("0.- Exit");
            System.out.println("========================================");
            option = Utils.integer();

            switch (option){
                case 1: selectCar();
                    break;

                case 2: addCar();
                    break;

                case 0: //exit
                    break;

                default:
                    break;
            }

        } while (option != 0);
    }

    private static void selectCar(){
        System.out.println();
        List<ScoreCar> selected = Utils.showAndSelectFromList(cars, true, false);
        ScoreCar car = selected.get(0);

        int option = -1;

        do {
            if (car != null) {
                System.out.println("\n========================================");
                System.out.println(car.getId() + ", " + car.getBrand() + ", " + car.getModel());
                System.out.println("========================================");
                System.out.println("1.- Modify brand.");
                System.out.println("2.- Modify model.");
                System.out.println("3.- Delete.");
                System.out.println("0.- Exit.");
                option = Utils.integer();

                switch (option) {
                    case 1:
                        car.setBrand(Utils.string("Type the new name for the brand:\n"));
                        break;

                    case 2:
                        car.setModel(Utils.string("Type the new name for the model:\n"));
                        break;

                    case 3:
                        if(Utils.string("Are you sure you want to remove this car? Y/N\n").toUpperCase().startsWith("Y")){
                            cars.remove(car);
                        }
                        break;

                    case 0: //exit
                        break;

                    default:
                        break;
                }
            }
        } while (option != 0);
    }

    private static ScoreCar findCarById(int id){
        for (ScoreCar c : cars) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    private static void addCar(){
        ScoreCar car = new ScoreCar(Utils.string("Type the brand of the car:\n"), Utils.string("Type teh model of the car:\n"));

        List<Garage> selection = Utils.showAndSelectFromList(garages, true, false);
        Garage garage = selection.get(0);

        car.setGarageName(garage.getName());
        cars.add(car);
    }
}

package com.campusdual.racecontrol.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Garage {
    private static final String GARAGE_ID = "id";
    private static final String GARAGE_NAME = "name";
    private static final String GARAGE_CARS = "cars";
    private static long idCount = 0;
    private long id;
    private String name;
    private List<ScoreCar> cars = new ArrayList<>();

    public Garage(String name) {
        this.id = generateId();
        this.name = name;
    }

    public Garage(String name, List<ScoreCar> cars) {
        this.id = generateId();
        this.name = name;
        this.cars = cars;
    }

    private static long generateId(){
        return Garage.idCount++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ScoreCar> getCars() {
        return cars;
    }

    public void setCars(List<ScoreCar> cars) {
        this.cars = cars;
    }

    public static Garage importGarage(JSONObject object){
        long id = (long)object.get(GARAGE_ID);
        String name = (String)object.get(GARAGE_NAME);
        Garage garage = new Garage(name);
        garage.generateId();

        return garage;
    }

    public JSONObject exportGarage(){
        JSONObject obj = new JSONObject();
        obj.put(GARAGE_ID, getId());
        obj.put(GARAGE_NAME, getName());

        JSONArray jCarsArray = new JSONArray();
        for (ScoreCar car : cars) {
            jCarsArray.add(car.exportScoreCar());
        }
        obj.put(GARAGE_CARS, jCarsArray);
        return obj;
    }

    public JSONArray exportGarages(ArrayList<Garage> garages){
        JSONArray jsonArray = new JSONArray();

        for (Garage g : garages){
            jsonArray.add(g.exportGarage());
        }

        return jsonArray;
    }

    public static void exportJSONToFile(ArrayList<Garage> garages, String filename){
        JSONArray jsonGarages = new JSONArray();

        for (Garage g : garages){
            jsonGarages.add(g.exportGarage());
        }
        try (
                FileWriter fw = new FileWriter(filename)
        ) {
            fw.write(jsonGarages.toJSONString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Garage garagePaco = new Garage("Garaje Paco");

        ScoreCar car1 = new ScoreCar("Seat", "Ibiza");
        ScoreCar car2 = new ScoreCar("Citroen", "Xsara");
        ScoreCar car3 = new ScoreCar("Opel", "Corsa");

        car1.setGarageName(garagePaco.getName());
        car2.setGarageName(garagePaco.getName());
        car3.setGarageName(garagePaco.getName());

        garagePaco.getCars().add(car1);
        garagePaco.getCars().add(car2);
        garagePaco.getCars().add(car3);

        Garage tallerManolo = new Garage("Taller Manolo");
        ScoreCar car4 = new ScoreCar("Volkswagen", "Polo");
        ScoreCar car5 = new ScoreCar("Volkswagen", "Golf");
        tallerManolo.getCars().add(car4);
        tallerManolo.getCars().add(car5);

        Garage escuderiaLoli = new Garage("Escuderia Loli");
        ScoreCar car6 = new ScoreCar("Ford", "Mustang");
        escuderiaLoli.getCars().add(car6);

        ArrayList<Garage> garages = new ArrayList<>();
        garages.add(garagePaco);
        garages.add(tallerManolo);
        garages.add(escuderiaLoli);

        exportJSONToFile(garages, "garages.json");

    }
}

package com.campusdual.racecontrol.model;

import com.campusdual.racecontrol.util.JsonUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class Garage {
    private static final String GARAGE_TAG = "garages";
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

    public static void exportGaragesToJSONFile(ArrayList<Garage> garages, String filename){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonGarages = new JSONArray();

        for (Garage g : garages){
            jsonGarages.add(g.exportGarage());
        }

        jsonObject.put(GARAGE_TAG, jsonGarages);

        JsonUtils.exportJsonObjectToFile(jsonObject, filename);
    }

    public static void main(String[] args) {
        List<ScoreCar> cars = new ArrayList<>();
        Garage garagePaco = new Garage("Garaje Paco");
        Garage tallerManolo = new Garage("Taller Manolo");
        Garage escuderiaLoli = new Garage("Escuderia Loli");

        JSONObject jsonObject = JsonUtils.importJSONFile("cars.json");

        assert jsonObject != null;
        JSONArray carJsonArray = (JSONArray) jsonObject.get("Cars");
        System.out.println(carJsonArray);

        for (Object o : carJsonArray) {
            cars.add(ScoreCar.importCar((JSONObject) o));
        }

        for (ScoreCar sc : cars){
            if (sc.getGarageName().equals(garagePaco.getName())){
                garagePaco.getCars().add(sc);
            } else if (sc.getGarageName().equals(tallerManolo.getName())){
                tallerManolo.getCars().add(sc);
            } else if (sc.getGarageName().equals(escuderiaLoli.getName())){
                escuderiaLoli.getCars().add(sc);
            }
        }

        System.out.println("Garage Paco:");
        System.out.println(garagePaco.getCars());

        System.out.println("Taller Manolo:");
        System.out.println(tallerManolo.getCars());

        System.out.println("Escuderia Loli:");
        System.out.println(escuderiaLoli.getCars());

        ArrayList<Garage> garages = new ArrayList<>();
        garages.add(garagePaco);
        garages.add(tallerManolo);
        garages.add(escuderiaLoli);

        exportGaragesToJSONFile(garages, "garages.json");

    }
}

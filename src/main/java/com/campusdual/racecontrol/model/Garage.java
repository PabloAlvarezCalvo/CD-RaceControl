package com.campusdual.racecontrol.main.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Garage {
    private static final String GARAGE_ID = "id";
    private static final String GARAGE_NAME = "name";
    private static final String GARAGE_CARS = "cars";
    private long id;
    private String name;
    private List<Car> cars = new ArrayList<>();

    public Garage(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Garage(long id, String name, List<Car> cars) {
        this.id = id;
        this.name = name;
        this.cars = cars;
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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public static Garage importGarage(JSONObject object){
        long id = (long)object.get(GARAGE_ID);
        String name = (String)object.get(GARAGE_NAME);
        return new Garage(id, name);
    }

    public JSONObject exportGarage(){
        JSONObject obj = new JSONObject();
        obj.put(GARAGE_ID, getId());
        obj.put(GARAGE_NAME, getName());

        JSONArray jCarsArray = new JSONArray();
        for (Car car : cars) {
            jCarsArray.add(car.exportCar());
        }
        obj.put(GARAGE_CARS, jCarsArray);
        return obj;
    }

    public static void exportJSONToFile(JSONObject object, String filename){
        try (
                FileWriter fw = new FileWriter(filename)
        ) {
            fw.write(object.toJSONString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Garage garagePaco = new Garage(1, "Garaje Paco");
        Car car1 = new Car(1, "Seat", "Ibiza", garagePaco);
        Car car2 = new Car(2, "Citroen", "Xsara", garagePaco);
        Car car3 = new Car(3, "Opel", "Corsa", garagePaco);

        garagePaco.cars.add(car1);
        garagePaco.cars.add(car2);
        garagePaco.cars.add(car3);

        Garage.exportJSONToFile(garagePaco.exportGarage(), "garage.json");
    }
}

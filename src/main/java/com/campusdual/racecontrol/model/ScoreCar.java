package com.campusdual.racecontrol.model;

import com.campusdual.racecontrol.util.JsonUtils;
import com.campusdual.racecontrol.util.RandomUtils;
import com.campusdual.racecontrol.util.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ScoreCar implements Comparable<ScoreCar> {
    private static final double MAX_SPEED = 200;
    private static final String CARS_TAG = "cars";
    public static final String CAR_ID = "id";
    public static final String CAR_BRAND = "brand";
    public static final String CAR_MODEL = "model";
    public static final String CAR_OWNER = "garage";

    private static long idCount = 0;
    private long id = 0;
    private String brand;
    private String model;
    private String garageName = "";
    private double speedometer = 0.0d; //In km/h

    private double distance = 0.0d; //In meters

    public ScoreCar() {
        this.id = generateId();
        this.brand = Utils.string("Type the brand of the car:\n");
        this.model = Utils.string("Type the model of the car:\n");
    }

    public ScoreCar(String brand, String model) {
        this.id = generateId();
        this.brand = brand;
        this.model = model;
    }

    public ScoreCar(String brand, String model, String garageName) {
        this.id = generateId();
        this.brand = brand;
        this.model = model;
        this.garageName = garageName;
    }

    public ScoreCar(long id, String brand, String model, String garageName) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.garageName = garageName;
    }

    private long generateId(){
        return ScoreCar.idCount++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand){
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model){
        this.model = model;
    }

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public double getSpeedometer() {
        return speedometer;
    }

    public double getDistance() {
        return distance;
    }

    public void speedUp(){
        if(this.speedometer < ScoreCar.MAX_SPEED){
            speedometer += 10;
        }
    }

    public void slowDown(){
        if (this.speedometer > 0){
            speedometer -= 10;
        }
    }

    public void drive(){
        int accelerate = RandomUtils.getRandomIntInRange(1, 3);

        if (accelerate == 1) {
            slowDown();
        } else {
            speedUp();
        }

        updateDistance();
    }

    private void updateDistance(){
        distance += speedometer * 1000 / 60;
    }

    public void restart() {
        this.speedometer = 0.0d;
        this.distance = 0.0d;
    }

    public static ScoreCar importScoreCar(JSONObject object){
        String brand = (String)object.get(ScoreCar.CAR_BRAND);
        String model = (String)object.get(ScoreCar.CAR_MODEL);
        String garageName = (String)object.get(ScoreCar.CAR_OWNER);
        return new ScoreCar(brand, model, garageName);
    }

    public JSONObject exportScoreCar(){
        JSONObject obj = new JSONObject();
        obj.put(CAR_ID, getId());
        obj.put(CAR_BRAND, getBrand());
        obj.put(CAR_MODEL, getModel());
        obj.put(CAR_OWNER, getGarageName());
        return obj;
    }

    public static ArrayList<ScoreCar> importScoreCarsFromJsonFile(String filename){
        ArrayList<ScoreCar> cars = new ArrayList<>();

        JSONObject object = JsonUtils.importJsonObjectFromFile(filename);
        JSONArray carJsonArray = (JSONArray) object.get(CARS_TAG);


        for (Object o : carJsonArray) {
            cars.add(ScoreCar.importScoreCar((JSONObject) o));
        }

        return cars;
    }

    public static void exportScoreCarsToJsonFile(ArrayList<ScoreCar> cars, String filename){
        JSONObject jsonCars = new JSONObject();
        JSONArray jsonArrayCars = new JSONArray();

        for (ScoreCar c : cars){
            jsonArrayCars.add(c.exportScoreCar());
        }

        jsonCars.put("Cars", jsonArrayCars);
        JsonUtils.exportJsonObjectToFile(jsonCars, filename);
    }

    @Override
    public String toString() {
        return "ScoreCar{id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", garageName='" + garageName + '\'' +
                ", speed=" + speedometer +
                '}';
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
        car1.setGarageName(garagePaco.getName());
        car2.setGarageName(garagePaco.getName());
        car3.setGarageName(garagePaco.getName());

        Garage tallerManolo = new Garage("Taller Manolo");
        ScoreCar car4 = new ScoreCar("Volkswagen", "Polo");
        ScoreCar car5 = new ScoreCar("Volkswagen", "Golf");
        tallerManolo.getCars().add(car4);
        tallerManolo.getCars().add(car5);
        car4.setGarageName(tallerManolo.getName());
        car5.setGarageName(tallerManolo.getName());

        Garage escuderiaLoli = new Garage("Escuderia Loli");
        ScoreCar car6 = new ScoreCar("Ford", "Mustang");
        escuderiaLoli.getCars().add(car6);
        car6.setGarageName(escuderiaLoli.getName());


        ArrayList<ScoreCar> cars = new ArrayList<>();
        cars.addAll(garagePaco.getCars());
        cars.addAll(tallerManolo.getCars());
        cars.addAll(escuderiaLoli.getCars());

        exportScoreCarsToJsonFile(cars, "cars.json");
    }


    @Override
    public int compareTo(ScoreCar o) {
        if (this.getDistance() > o.getDistance()) {
            return 1;
        } else if (this.getDistance() < o.getDistance()) {
            return -1;
        } else return 0;
    }
}

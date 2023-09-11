package com.campusdual.racecontrol.model;

import com.campusdual.racecontrol.util.Input;
import com.campusdual.racecontrol.util.RandomUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;

public class ScoreCar implements Comparable<ScoreCar> {
    private static final double MAX_SPEED = 200;
    public static final String CAR_ID = "id";
    public static final String CAR_BRAND = "brand";
    public static final String CAR_MODEL = "model";
    public static final String CAR_OWNER = "garage";

    private long id = -1;
    private String brand;
    private String model;
    private String garageName = "";
    private double speedometer = 0.0d; //In km/h

    private double distance = 0.0d; //In meters

    public ScoreCar() {
        this.brand = Input.string("Type the brand of the car:\n");
        this.model = Input.string("Type the model of the car:\n");
    }

    public ScoreCar(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public ScoreCar(long id, String brand, String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
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

    public String getModel() {
        return model;
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

    public static Car importCar(JSONObject object){
        String brand = (String)object.get(Car.CAR_BRAND);
        String model = (String)object.get(Car.CAR_MODEL);
        return new Car(brand, model);
    }

    public static JSONObject importJSONFile(String filename){
        try(FileReader fr = new FileReader(filename)){
            JSONParser parser = new JSONParser();
            return (JSONObject)parser.parse(fr);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public JSONObject exportScoreCar(){
        JSONObject obj = new JSONObject();
        obj.put(CAR_ID, getId());
        obj.put(CAR_BRAND, getBrand());
        obj.put(CAR_MODEL, getModel());
        obj.put(CAR_OWNER, getGarageName());
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


    @Override
    public String toString() {
        return "ScoreCar{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", garageName='" + garageName + '\'' +
                ", speed=" + speedometer +
                '}';
    }

    public static void main(String[] args) {
        //ScoreCar sc = new ScoreCar();
        //System.out.println(sc);

        ScoreCar sc2 = new ScoreCar("Seat", "Ibiza");
        ScoreCar sc3 = new ScoreCar("Opel", "Corsa");

        for (int i = 0; i < 120; i++){
            sc2.drive();
            sc3.drive();
        }

        System.out.printf("SC2. Distance: %.2f m. Final speed: %.2f km/h.\n", sc2.distance, sc2.speedometer);
        System.out.printf("SC3. Distance: %.2f m. Final speed: %.2f km/h.\n", sc3.distance, sc3.speedometer);
        System.out.println("Winner: " + sc2.compareTo(sc3));
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

package com.campusdual.racecontrol.main.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Objects;

public class Car {
    public static final String CAR_ID = "id";
    public static final String CAR_BRAND = "brand";
    public static final String CAR_MODEL = "model";
    public static final String CAR_OWNER = "garage";
    private long id;
    private String brand;
    private String model;
    private Garage garage;

    public Car(long id, String brand, String model, Garage garage) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.garage = garage;
    }

    public Car(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public static Car importCar(JSONObject object){
        String brand = (String)object.get(Car.CAR_BRAND);
        String model = (String)object.get(Car.CAR_MODEL);
        return new Car(brand, model);
    }

    public JSONObject exportCar(){
        JSONObject obj = new JSONObject();
        obj.put(CAR_ID, getId());
        obj.put(CAR_BRAND, getBrand());
        obj.put(CAR_MODEL, getModel());
        obj.put(CAR_OWNER, getGarage().getId());
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

    public static JSONObject importJSONFile(String filename){
        try(FileReader fr = new FileReader(filename)){
            JSONParser parser = new JSONParser();
            return (JSONObject)parser.parse(fr);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
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

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Car car1 = new Car(1, "Citroen", "Xsara", new Garage(1, "Garage paco"));
        System.out.println(car1);
        JSONObject jsonObject = car1.exportCar();
        System.out.println(jsonObject);

        Car car2 = Car.importCar(jsonObject);
        Car.exportJSONToFile(jsonObject, "test.json");

        Car car3 = Car.importCar(Objects.requireNonNull(Car.importJSONFile("test2.json")));
        System.out.println(car3);
    }
}

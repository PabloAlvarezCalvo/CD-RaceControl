package com.campusdual.racecontrol;

import com.campusdual.racecontrol.util.Input;
import com.campusdual.racecontrol.util.RandomUtils;

public class ScoreCar implements Comparable<ScoreCar> {
    private static final double MAX_SPEED = 200;
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
